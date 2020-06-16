package org.example.core.repository.nativesql;

import org.example.core.model.Student;
import org.example.core.repository.StudentFilteringRepository;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Component("StudentNativeRepositoryImpl")
public class StudentNativeRepository implements StudentFilteringRepository {
  @PersistenceContext
  private EntityManager entityManager;

  @Override
  @Transactional
  public List<Student> findByNameLikeCustom(String nameParam){
    Session hibernateEntityManager = entityManager.unwrap(Session.class);
    Session session = hibernateEntityManager.getSession();
    Query query =
        session
            .createNativeQuery(
                "SELECT DISTINCT {s.*} from student s WHERE s.name like ?")
            .addEntity("s", Student.class)
            .setParameter(1, "%" + nameParam + "%")
            .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY);
    return query.getResultList();
  }
}
