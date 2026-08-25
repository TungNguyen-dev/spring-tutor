package tungnn.tutor.java.spring.data.jpa.started.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import java.util.List;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

/*
 * Typed query example
 * Language: JPQL
 */
public class EmployeeDAOTypedQuery {

  @PersistenceContext private EntityManager entityManager;

  public List<Employee> findAll() {
    TypedQuery<Employee> query =
        entityManager.createQuery("select e from Employee e", Employee.class);
    return query.getResultList();
  }
}
