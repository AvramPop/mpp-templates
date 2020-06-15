package org.example.server.repository;

import org.example.common.domain.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface StudentRepository extends JpaRepository<Student, Integer> {
}