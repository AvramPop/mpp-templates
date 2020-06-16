package org.example.old;

import org.example.old.domain.Student;
import org.example.old.repo.FileRepo;
import org.example.old.repo.JDBCRepo;
import org.example.old.repo.Repository;
import org.example.old.repo.XMLRepo;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class Main {
  public static void main(String[] args) {
    try {
      Files.createFile(Paths.get("/home/dani/Desktop/mpp-templates/old-good-persistence/src/main/resources/students.txt"));
    } catch (IOException ignored) {
    }

    try {
      Files.createFile(Paths.get("/home/dani/Desktop/mpp-templates/old-good-persistence/src/main/resources/students.xml"));
    } catch (IOException ignored) {
    }

    Repository studentFileRepository =
        new FileRepo(
            "/home/dani/Desktop/mpp-templates/old-good-persistence/src/main/resources/students.txt", ";");

    Repository studentXMLRepository =
        new XMLRepo(
            "/home/dani/Desktop/mpp-templates/old-good-persistence/src/main/resources/students.xml");

    Repository studentDBRepository =
        new JDBCRepo(
            "/home/dani/Desktop/mpp-templates/old-good-persistence/src/main/resources/db.properties");

    studentDBRepository.delete(5);
    studentDBRepository.findAll().forEach(student -> System.out.println(student.toString()));
  }
}
