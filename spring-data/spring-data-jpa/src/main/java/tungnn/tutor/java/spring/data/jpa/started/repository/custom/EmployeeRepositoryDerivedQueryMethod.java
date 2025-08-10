package tungnn.tutor.java.spring.data.jpa.started.repository.custom;

import java.math.BigDecimal;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public interface EmployeeRepositoryDerivedQueryMethod extends JpaRepository<Employee, Long> {

  /*
   * Derived query method example
   * Spring Data JPA can parse your repository method name
   * and automatically generate the query at runtime,
   * without you writing @Query or SQL/JPQL.
   */
  List<Employee> findEmployeeBySalaryNull(BigDecimal salary);
}
