package org.example.core.repository;

import org.example.core.model.Student;
import org.springframework.stereotype.Component;

import java.util.List;
public interface StudentFilteringRepository {
  List<Student> findByNameLikeCustom(String name);
}
