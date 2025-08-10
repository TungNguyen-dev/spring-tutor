package tungnn.tutor.java.spring.data.jpa.started.repository.custom;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public interface EmployeeRepositoryQueryAnnotation extends JpaRepository<Employee, Long> {

  @Query("SELECT e FROM Employee e WHERE e.firstName = ?1 AND e.salary > ?2")
  List<Employee> customQueryWithNamedParams(String firstName, BigDecimal salary);
}
