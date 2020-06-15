package org.example.common.infrastructure;

import java.io.*;

public class Message {
  public static final int PORT = 1234;
  public static final String HOST = "localhost";

  private String header;
  private String body;

  public Message() {}

  public Message(String header, String body) {
    this.header = header;
    this.body = body;
  }

  public String getHeader() {
    return header;
  }

  public void setHeader(String header) {
    this.header = header;
  }

  public String getBody() {
    return body;
  }

  public void setBody(String body) {
    this.body = body;
  }

  public void writeTo(OutputStream os) throws IOException {
    String messageToPrint = header + System.lineSeparator() + body + System.lineSeparator();
    os.write(messageToPrint.getBytes());
  }

  public void readFrom(InputStream is) throws IOException {
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    StringBuilder bufferMessage = new StringBuilder();
    do {
      bufferMessage.append(br.readLine());
      bufferMessage.append(System.lineSeparator());
    } while (br.ready());
    bufferMessage =
        new StringBuilder(
            bufferMessage.substring(0, bufferMessage.length() - System.lineSeparator().length()));
    String[] inputParsed = bufferMessage.toString().split(System.lineSeparator(), 2);
    header = inputParsed[0];
    if (inputParsed.length > 1) {
      body = inputParsed[1];
    }
  }

  @Override
  public String toString() {
    return "Message{" + "header='" + header + '\'' + ", body='" + body + '\'' + '}';
  }
}
