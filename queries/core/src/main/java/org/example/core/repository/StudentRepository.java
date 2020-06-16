package org.example.core.repository;

import org.example.core.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
@Component("StudentCriteriaRepository")
public interface StudentRepository extends JpaRepository<Student, Integer>, StudentFilteringRepository {
  @Query("select s.name, s.id from Student s")
  List<Object[]> findAllNamesAndIds();
}
