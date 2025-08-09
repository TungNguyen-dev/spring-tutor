package tungnn.tutor.java.spring.data.jpa.started.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

/*
 * Named query example
 * Language: JPQL
 */
public class EmployeeDAONamedQuery {

  @PersistenceContext private EntityManager entityManager;

  public List<?> findAllByNamedQuery(String email) {
    Query query = entityManager.createNamedQuery("Employee.findByEmail");
    query.setParameter("email", email);
    return query.getResultList();
  }
}
