package org.example.core.repository.jpql;

import org.example.core.model.Student;
import org.example.core.repository.StudentFilteringRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Component("StudentJpqlRepositoryImpl")
public class StudentJpqlRepository implements StudentFilteringRepository {
  @PersistenceContext
  private EntityManager entityManager;
  @Override
  @Transactional
  public List<Student> findByNameLikeCustom(@Param("name") String name){
    Query query =
        entityManager.createQuery(
            "SELECT DISTINCT a from Student a WHERE a.name like concat('%',:name,'%')");
    query.setParameter("name", name);
    return query.getResultList();
  }
}
