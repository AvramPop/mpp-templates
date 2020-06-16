package org.example.core.repository.criteria;

import org.example.core.model.Student;
import org.example.core.repository.StudentFilteringRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.ParameterExpression;
import javax.persistence.criteria.Root;
import java.util.List;

@Component("StudentCriteriaRepositoryImpl")
public class StudentCriteriaRepository implements StudentFilteringRepository {
  @PersistenceContext
  private EntityManager entityManager;
  @Override
  @Transactional
  public List<Student> findByNameLikeCustom(String name){
    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();

    CriteriaQuery<Student> criteriaQuery = criteriaBuilder.createQuery(Student.class);
    criteriaQuery.distinct(Boolean.TRUE);
    Root<Student> studentRoot = criteriaQuery.from(Student.class);
   // ParameterExpression<String> pe = criteriaBuilder.parameter(String.class);
    criteriaQuery.where(criteriaBuilder.like(studentRoot.get("name"), "%" + name + "%"));

    TypedQuery<Student> query = entityManager.createQuery(criteriaQuery);
 //   query.setParameter(pe, name);
    return query.getResultList();
  }
}
