package org.example.sensor.infrastructure;


import org.example.server.infrastructure.Message;
import org.hibernate.service.spi.ServiceException;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;

/** TCP-connection communication provider via socket streams. */
public class TCPClient {
  /**
   * Receives Message as result of sent request.
   *
   * @param request the client request as message.
   * @return the server's response
   */
  public Message sendAndReceive(Message request) {
    try (Socket socket = new Socket(Message.HOST, Message.PORT);
         InputStream is = socket.getInputStream();
         OutputStream os = socket.getOutputStream()) {
      // System.out.println("sendAndReceive - sending request: " + request);
      request.writeTo(os);

      // System.out.println("sendAndReceive - received response: ");
      Message response = new Message();
      response.readFrom(is);
      //  System.out.println(response);

      return response;
    } catch (IOException e) {
      throw new ServiceException("error connection to server " + e.getMessage(), e);
    }
  }
}
