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
import java.util.LinkedHashSet;
import java.util.Set;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "DEPARTMENTS", schema = "HR")
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "DEPARTMENTS_id_gen")
  @SequenceGenerator(
      name = "DEPARTMENTS_id_gen",
      sequenceName = "DEPARTMENTS_SEQ",
      allocationSize = 10)
  @Column(name = "DEPARTMENT_ID", nullable = false)
  private Short id;

  @Column(name = "DEPARTMENT_NAME", nullable = false, length = 30)
  private String departmentName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "MANAGER_ID")
  private Employee manager;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "LOCATION_ID")
  private Location location;

  @OneToMany
  @JoinColumn(name = "DEPARTMENT_ID")
  private Set<Employee> employees = new LinkedHashSet<>();

  @OneToMany
  @JoinColumn(name = "DEPARTMENT_ID")
  private Set<JobHistory> jobHistories = new LinkedHashSet<>();

  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public String getDepartmentName() {
    return departmentName;
  }

  public void setDepartmentName(String departmentName) {
    this.departmentName = departmentName;
  }

  public Employee getManager() {
    return manager;
  }

  public void setManager(Employee manager) {
    this.manager = manager;
  }

  public Location getLocation() {
    return location;
  }

  public void setLocation(Location location) {
    this.location = location;
  }

  public Set<Employee> getEmployees() {
    return employees;
  }

  public void setEmployees(Set<Employee> employees) {
    this.employees = employees;
  }

  public Set<JobHistory> getJobHistories() {
    return jobHistories;
  }

  public void setJobHistories(Set<JobHistory> jobHistories) {
    this.jobHistories = jobHistories;
  }
}
