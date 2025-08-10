package tungnn.tutor.java.spring.data.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import lombok.Data;

@Entity
@Table(name = "EMPLOYEES", schema = "HR")
@Data
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEES_id_gen")
  @SequenceGenerator(name = "EMPLOYEES_id_gen", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
  @Column(name = "EMPLOYEE_ID", nullable = false)
  private Long employeeId;

  @Column(name = "FIRST_NAME", length = 20)
  private String firstName;

  @Column(name = "LAST_NAME", nullable = false, length = 25)
  private String lastName;

  @Column(name = "EMAIL", nullable = false, length = 25)
  private String email;

  @Column(name = "PHONE_NUMBER", length = 20)
  private String phoneNumber;

  @Column(name = "HIRE_DATE", nullable = false)
  private LocalDate hireDate;

  @Column(name = "SALARY", precision = 8, scale = 2)
  private BigDecimal salary;

  @Column(name = "COMMISSION_PCT", precision = 2, scale = 2)
  private BigDecimal commissionPct;
}
