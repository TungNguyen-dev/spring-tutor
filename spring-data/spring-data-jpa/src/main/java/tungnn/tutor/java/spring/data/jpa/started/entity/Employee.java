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
import org.hibernate.annotations.NamedQuery;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "EMPLOYEES", schema = "HR")
@NamedQuery(
    name = "Employee.findByEmail",
    query = "SELECT e FROM Employee e WHERE e.email = :email")
public class Employee {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "EMPLOYEES_id_gen")
  @SequenceGenerator(name = "EMPLOYEES_id_gen", sequenceName = "EMPLOYEES_SEQ", allocationSize = 1)
  @Column(name = "EMPLOYEE_ID", nullable = false)
  private Integer id;

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
  private tungnn.tutor.java.spring.data.jpa.started.entity.Job job;

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

  @OneToMany
  @JoinColumn(name = "MANAGER_ID")
  private Set<Department> departments = new LinkedHashSet<>();

  @OneToMany
  @JoinColumn(name = "MANAGER_ID")
  private Set<Employee> employees = new LinkedHashSet<>();

  @OneToMany
  @JoinColumn(name = "EMPLOYEE_ID")
  private Set<tungnn.tutor.java.spring.data.jpa.started.entity.JobHistory> jobHistories =
      new LinkedHashSet<>();

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public LocalDate getHireDate() {
    return hireDate;
  }

  public void setHireDate(LocalDate hireDate) {
    this.hireDate = hireDate;
  }

  public tungnn.tutor.java.spring.data.jpa.started.entity.Job getJob() {
    return job;
  }

  public void setJob(tungnn.tutor.java.spring.data.jpa.started.entity.Job job) {
    this.job = job;
  }

  public BigDecimal getSalary() {
    return salary;
  }

  public void setSalary(BigDecimal salary) {
    this.salary = salary;
  }

  public BigDecimal getCommissionPct() {
    return commissionPct;
  }

  public void setCommissionPct(BigDecimal commissionPct) {
    this.commissionPct = commissionPct;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }

  public Set<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(Set<Department> departments) {
    this.departments = departments;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  public Set<tungnn.tutor.java.spring.data.jpa.started.entity.JobHistory> getJobHistories() {
    return jobHistories;
  }

  public void setJobHistories(
      Set<tungnn.tutor.java.spring.data.jpa.started.entity.JobHistory> jobHistories) {
    this.jobHistories = jobHistories;
  }
}
