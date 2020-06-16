package org.example.old.repo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public abstract class DBRepository {
  private String dbType;
  private String dbHost;
  private String dbPort;
  private String dbName;
  private String dbUser;
  private String dbPassword;

  public DBRepository(String dbCredentialsFilename) {
    loadDBConfiguration(dbCredentialsFilename);
  }

  private void loadDBConfiguration(String dbCredentialsFilename) {
    Path path = Paths.get(dbCredentialsFilename);
    List<String> inputData = new ArrayList<>();
    try {
      Files.lines(path).forEach(inputData::add);
    } catch (IOException e) {
      e.printStackTrace();
    }
    dbType = inputData.get(0);
    dbHost = inputData.get(1);
    dbPort = inputData.get(2);
    dbName = inputData.get(3);
    dbUser = inputData.get(4);
    dbPassword = inputData.get(5);
  }

  private void loadDriver() {
    try {
      Class.forName("org.postgresql.Driver");
    } catch (ClassNotFoundException e) {
      System.err.println("Can’t load driver");
    }
  }

  protected Connection dbConnection() {

    loadDriver();
    DriverManager.setLoginTimeout(60);
    try {
      String url =
          "jdbc:"
              + this.dbType
              + "://"
              + this.dbHost
              + ":"
              + this.dbPort
              + "/"
              + dbName
              + "?user="
              + this.dbUser
              + "&password="
              + this.dbPassword;
      return DriverManager.getConnection(url);
    } catch (SQLException e) {
      System.err.println("Cannot connect to the database: " + e.getMessage());
    }

    return null;
  }
}
