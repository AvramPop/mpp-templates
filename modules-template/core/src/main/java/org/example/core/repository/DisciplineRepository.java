package org.example.core.repository;

import org.example.core.model.Discipline;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface DisciplineRepository extends JpaRepository<Discipline, Integer> {
}
