package tungnn.tutor.java.spring.data.jpa.started.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "EMPLOYEES", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "employeeId")
@NamedQuery(
    name = "Employee.findByEmail",
    query = "SELECT e FROM Employee e WHERE e.email = :email")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "employee_id_gen")
  @SequenceGenerator(name = "employee_id_gen", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
  @Column(name = "EMPLOYEE_ID", nullable = false)
  private Integer employeeId;

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

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "JOB_ID", nullable = false)
  private Job job;

  @Column(name = "SALARY", precision = 8, scale = 2)
  private BigDecimal salary;

  @Column(name = "COMMISSION_PCT", precision = 2, scale = 2)
  private BigDecimal commissionPct;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "MANAGER_ID")
  private Employee manager;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "DEPARTMENT_ID")
  private Department department;

  @OneToMany(mappedBy = "manager")
  private Set<Department> managedDepartments = new LinkedHashSet<>();

  @OneToMany(mappedBy = "manager")
  private Set<Employee> managedEmployees = new LinkedHashSet<>();

  @OneToMany(mappedBy = "employee")
  private Set<JobHistory> jobHistories = new LinkedHashSet<>();
}
