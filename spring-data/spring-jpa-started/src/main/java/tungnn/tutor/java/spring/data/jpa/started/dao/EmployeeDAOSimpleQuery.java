package tungnn.tutor.java.spring.data.jpa.started.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import java.util.List;

/*
 * Simple query example
 * Language: JPQL
 */
public class EmployeeDAOSimpleQuery {

  @PersistenceContext private EntityManager entityManager;

  /*
   * Simple query
   */
  public List<?> findAll() {
    Query query = entityManager.createQuery("select e from Employee e");
    return query.getResultList();
  }

  /*
   * Positional parameter: `?`
   */
  public List<?> findByFirstName(String firstName) {
    Query query = entityManager.createQuery("select e from Employee e where e.firstName = ?");
    query.setParameter(0, firstName);
    return query.getResultList();
  }

  /*
   * Named parameter: `:name`
   */
  public List<?> findByLastName(String name) {
    Query query = entityManager.createQuery("select e from Employee e where e.firstName = :name");
    query.setParameter("name", name);
    return query.getResultList();
  }

  /*
   * Update using executeUpdate
   */
  public int updateSalary(Long id, Double newSalary) {
    Query query =
        entityManager.createQuery("update Employee e set e.salary = :salary where e.id = :id");
    query.setParameter("salary", newSalary);
    query.setParameter("id", id);
    return query.executeUpdate();
  }

  /*
   * Delete using executeUpdate
   */
  public int deleteById(Long id) {
    Query query = entityManager.createQuery("delete from Employee e where e.id = :id");
    query.setParameter("id", id);
    return query.executeUpdate();
  }
}
