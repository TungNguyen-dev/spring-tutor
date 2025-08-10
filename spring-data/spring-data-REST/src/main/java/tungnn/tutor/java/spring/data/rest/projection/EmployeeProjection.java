package tungnn.tutor.java.spring.data.rest.projection;

import java.math.BigDecimal;
import java.time.LocalDate;
import org.springframework.data.rest.core.config.Projection;
import tungnn.tutor.java.spring.data.rest.entity.Employee;

@Projection(
    name = "employee",
    types = {Employee.class})
public interface EmployeeProjection {

  Long getEmployeeId();

  String getFirstName();

  String getLastName();

  String getEmail();

  String getPhoneNumber();

  LocalDate getHireDate();

  BigDecimal getSalary();

  BigDecimal getCommissionPct();
}
