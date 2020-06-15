package org.example.web.converter;

import org.example.core.model.Student;
import org.example.web.dto.StudentDto;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class StudentConverter {
  public Student convertDtoToModel(StudentDto dto) {
    return Student.builder().name(dto.getName()).id(dto.getId()).build();
  }

  public StudentDto convertModelToDto(Student student) {
    return StudentDto.builder()
        .id(student.getId())
        .name(student.getName())
        .build();
  }

  public List<StudentDto> convertModelsToDtos(Collection<Student> students) {
    return students.stream().map(this::convertModelToDto).collect(Collectors.toList());
  }
}
