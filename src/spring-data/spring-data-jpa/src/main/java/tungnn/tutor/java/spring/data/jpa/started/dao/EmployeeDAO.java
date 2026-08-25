package tungnn.tutor.java.spring.data.jpa.started.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public class EmployeeDAO {

  @PersistenceContext private EntityManager entityManager;

  public void create(Employee employee) {
    entityManager.persist(employee);
  }

  public Employee findById(Long id) {
    return entityManager.find(Employee.class, id);
  }

  public Employee update(Employee employee) {
    return entityManager.merge(employee);
  }

  public void delete(Long id) {
    Employee employee = findById(id);
    if (employee != null) {
      entityManager.remove(employee);
    }
  }
}
