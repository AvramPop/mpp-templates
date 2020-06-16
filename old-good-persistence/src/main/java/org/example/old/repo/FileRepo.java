package org.example.old.repo;

import org.example.old.domain.Student;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class FileRepo implements Repository {
  private final String filename;
  private final String delimiter;

  public FileRepo(String filename, String delimiter) {
    this.filename = filename;
    this.delimiter = delimiter;
  }

  @Override
  public Optional<Student> findOne(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("id must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));

    return Optional.ofNullable(entities.get(id));
  }

  @Override
  public List<Student> findAll() {
    return StreamSupport.stream(loadData().spliterator(), false).collect(Collectors.toList());
  }

  @Override
  public boolean save(Student entity) {
    if (entity == null) {
      throw new IllegalArgumentException("id must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));
    Optional<Student> optional = Optional.ofNullable(entities.putIfAbsent(entity.getId(), entity));

    if (optional.isPresent()) return false;

    saveToFile(entity);
    return true;
  }

  @Override
  public boolean delete(Integer id) {
    if (id == null) {
      throw new IllegalArgumentException("id must not be null");
    }
    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));
    Optional<Student> optional = Optional.ofNullable(entities.remove(id));

    if (optional.isEmpty()) return false;

    saveToFile(entities.values());
    return true;
  }

  @Override
  public boolean update(Student entity) {
    if (entity == null) {
      throw new IllegalArgumentException("entity must not be null");
    }

    Map<Integer, Student> entities =
        StreamSupport.stream(loadData().spliterator(), false)
            .collect(Collectors.toMap(Student::getId, elem -> elem));

    Optional<Student> optional =
        Optional.ofNullable(
            entities.computeIfPresent(entity.getId(), (k, v) -> entity) == null ? entity : null);
    if (optional.isPresent()) return false;

    saveToFile(entities.values());
    return true;
  }

  private Iterable<Student> loadData() {
    Path path = Paths.get(this.filename);
    HashSet<Student> newEntities = new HashSet<>();
    try {
      Files.lines(path).forEach(line -> newEntities.add(studentFromFileLine(line, delimiter)));
    } catch (IOException e) {
      e.printStackTrace();
    }
    return newEntities;
  }

  private void saveToFile(Collection<Student> entitiesToSave) {
    Path path = Paths.get(this.filename);
    List<String> entityLines =
        entitiesToSave.stream()
            .map(elem -> objectToFileLine(elem, delimiter))
            .collect(Collectors.toList());
    try {
      Files.write(path, entityLines);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public String objectToFileLine(Student student, String delimiter) {
    return student.getId() + delimiter + student.getName();
  }

  private Student studentFromFileLine(String line, String delimiter) {
    List<String> params = Arrays.asList(line.split(delimiter));
    Student student = new Student(Integer.parseInt(params.get(0)), params.get(1));
    return student;
  }

  private void saveToFile(Student entity) {
    Path path = Paths.get(this.filename);
    try {
      Files.write(
          path, (objectToFileLine(entity, delimiter) + "\n").getBytes(), StandardOpenOption.APPEND);
    } catch (IOException ex) {
      ex.printStackTrace();
    }
  }
}
