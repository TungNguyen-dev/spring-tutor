package tungnn.tutor.java.spring.data.jpa.started.dao;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.PersistenceUnit;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public class EmployeeDAOManualTX {

  @PersistenceUnit(unitName = "default")
  private EntityManagerFactory entityManagerFactory;

  public void create(Employee employee) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      entityManager.getTransaction().begin();
      entityManager.persist(employee);
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }

  public Employee findById(Long id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      return entityManager.find(Employee.class, id);
    } finally {
      entityManager.close();
    }
  }

  public Employee update(Employee employee) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      entityManager.getTransaction().begin();
      Employee updated = entityManager.merge(employee);
      entityManager.getTransaction().commit();
      return updated;
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }

  public void delete(Long id) {
    EntityManager entityManager = entityManagerFactory.createEntityManager();
    try {
      entityManager.getTransaction().begin();
      Employee employee = entityManager.find(Employee.class, id);
      if (employee != null) {
        entityManager.remove(employee);
      }
      entityManager.getTransaction().commit();
    } catch (Exception e) {
      entityManager.getTransaction().rollback();
      throw e;
    } finally {
      entityManager.close();
    }
  }
}
