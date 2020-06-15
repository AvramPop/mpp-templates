package org.example.sensor.ui;

import org.example.sensor.service.SensorClientService;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ConnectException;
import java.util.HashMap;
import java.util.concurrent.ExecutorService;

/** Console based user interface for the client. */
public class Console {
  private SensorClientService sensorClientService;
  private HashMap<String, Runnable> dictionaryOfCommands;
  private ExecutorService executorService;

  public Console(SensorClientService sensorClientService, ExecutorService executorService) {
    this.sensorClientService = sensorClientService;
    this.executorService = executorService;
    // I use lambda methods with a hash table to not to make if statements
    // if the thing fails it gets a null pointer exception
    // which means not a valid command
  }

  /** Take specific user input and print server's answer to the call getLabProblemById call. */
//  private void shutDownServer() {
//    studentService.shutDownServer();
//    System.exit(0);
//  }

  public void run() {
//    while (true) {
//      BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
//      try {
//        String inputString = input.readLine();
//        dictionaryOfCommands.get(inputString).run();
//      } catch (ConnectException ex) {
//        System.out.println(ex.getMessage());
//      } catch (IOException ex) {
//        System.out.println("Error with input!");
//      } catch (NullPointerException ex) {
//        System.out.println("Not a vaild comand");
//      }
//    }
    addSensor();
  }

  /** Take specific user input and print server's answer to the call addStudent call. */
  private void addSensor() {
    System.out.println("Read sensor {name, id, lower bound, upper bound}");
    BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
    try {
      System.out.println("Enter name: ");
      String name = input.readLine().strip();
      System.out.println("Enter id: ");
      int id = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter lower bound: ");
      int lowerBound = Integer.parseInt(input.readLine().strip());
      System.out.println("Enter upper bound: ");
      int upperBound = Integer.parseInt(input.readLine().strip());

      sensorClientService.sendData(name, lowerBound, upperBound, id);

    } catch (IOException | InterruptedException e) {
      e.printStackTrace();
    }
  }
}
