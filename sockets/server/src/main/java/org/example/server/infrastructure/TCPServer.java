package org.example.server.infrastructure;

import org.example.common.exceptions.ServiceException;
import org.example.common.infrastructure.Message;
import org.example.common.infrastructure.MessageHeader;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.function.UnaryOperator;

public class TCPServer {
  private final ExecutorService executorService;
  private final Map<String, UnaryOperator<Message>> methodHandlers;
  private final int port;

  public TCPServer(ExecutorService executorService, int port) {
    this.methodHandlers = new HashMap<>();
    this.executorService = executorService;
    this.port = port;
  }

  public TCPServer(ExecutorService executorService) {
    this(executorService, Message.PORT);
  }

  public void addHandler(String methodName, UnaryOperator<Message> handler) {
    methodHandlers.put(methodName, handler);
  }

  public void startServer() {
    try (ServerSocket serverSocket = new ServerSocket(port)) {
      while (true) {
        Socket client = serverSocket.accept();
        if (!executorService.submit(new ClientHandler(client)).get()) break;
      }
    } catch (IOException e) {
      throw new ServiceException("error connecting clients", e);
    } catch (InterruptedException | ExecutionException e) {
      throw new ServiceException("error during server run", e);
    }
  }

  private class ClientHandler implements Callable<Boolean> {
    private final Socket socket;

    public ClientHandler(Socket client) {
      this.socket = client;
    }

    @Override
    public Boolean call() {
      try (InputStream is = socket.getInputStream();
          OutputStream os = socket.getOutputStream()) {
        Message request = new Message();
        request.readFrom(is);
        System.out.println("server received request from client: " + request);

        if (request.getHeader().equals(MessageHeader.SERVER_SHUTDOWN)) {
          Message response = new Message(MessageHeader.SERVER_SHUTDOWN, "");
          response.writeTo(os);
          System.out.println("Shutting down");
          return false;
        }

        System.out.println(request.getHeader());
        System.out.println(request.getBody());

        Message response = methodHandlers.get(request.getHeader()).apply(request);

        response.writeTo(os);
        System.out.println("server sending back: " + response);
      } catch (IOException e) {
        throw new ServiceException("error processing client", e);
      }
      return true;
    }
  }
}
