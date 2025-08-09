package tungnn.tutor.java.spring.data.jpa.started.entity;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.time.LocalDate;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "JOB_HISTORY", schema = "HR")
public class JobHistory {
  @SequenceGenerator(
      name = "JOB_HISTORY_id_gen",
      sequenceName = "LOCATIONS_SEQ",
      allocationSize = 100)
  @EmbeddedId
  private JobHistoryId id;

  @MapsId("employeeId")
  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "EMPLOYEE_ID", nullable = false)
  private Employee employee;

  @Column(name = "END_DATE", nullable = false)
  private LocalDate endDate;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "JOB_ID", nullable = false)
  private Job job;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "DEPARTMENT_ID")
  private Department department;

  public JobHistoryId getId() {
    return id;
  }

  public void setId(JobHistoryId id) {
    this.id = id;
  }

  public Employee getEmployee() {
    return employee;
  }

  public void setEmployee(Employee employee) {
    this.employee = employee;
  }

  public LocalDate getEndDate() {
    return endDate;
  }

  public void setEndDate(LocalDate endDate) {
    this.endDate = endDate;
  }

  public Job getJob() {
    return job;
  }

  public void setJob(Job job) {
    this.job = job;
  }

  public Department getDepartment() {
    return department;
  }

  public void setDepartment(Department department) {
    this.department = department;
  }
}
