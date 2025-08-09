package tungnn.tutor.java.spring.data.jpa.started.repository;

import java.math.BigDecimal;
import org.springframework.data.repository.Repository;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

/**
 * Simple repository interface demonstrating Spring Data JPA's basic Repository functionality.
 * Extends Repository interface which is the base interface providing minimal Spring Data repository
 * features.
 */
public interface EmployeeSimpleRepository extends Repository<Employee, Long> {

  /**
   * Demonstrates Spring Data JPA's method name query feature. The method name follows a specific
   * pattern that Spring automatically converts to a query: - 'find' : indicates a select operation
   * - 'By' : indicates the start of condition - 'Salary' : specifies the entity field name to query
   *
   * <p>Spring will automatically create: "SELECT e FROM Employee e WHERE e.salary = :salary"
   *
   * @param salary the salary value to search for
   * @return the Employee with matching salary
   */
  Employee findBySalary(BigDecimal salary);
}
