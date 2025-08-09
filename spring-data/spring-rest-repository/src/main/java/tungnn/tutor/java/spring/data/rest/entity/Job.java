package tungnn.tutor.java.spring.data.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.SequenceGenerator;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "JOBS", schema = "HR")
public class Job {
  @Id
  @SequenceGenerator(name = "JOBS_id_gen", sequenceName = "LOCATIONS_SEQ", allocationSize = 100)
  @Column(name = "JOB_ID", nullable = false, length = 10)
  private String jobId;

  @Column(name = "JOB_TITLE", nullable = false, length = 35)
  private String jobTitle;

  @Column(name = "MIN_SALARY")
  private Integer minSalary;

  @Column(name = "MAX_SALARY")
  private Integer maxSalary;

  @OneToMany
  @JoinColumn(name = "JOB_ID")
  private Set<Employee> employees = new LinkedHashSet<>();

  @OneToMany
  @JoinColumn(name = "JOB_ID")
  private Set<JobHistory> jobHistories = new LinkedHashSet<>();

  public String getJobId() {
    return jobId;
  }

  public void setJobId(String jobId) {
    this.jobId = jobId;
  }

  public String getJobTitle() {
    return jobTitle;
  }

  public void setJobTitle(String jobTitle) {
    this.jobTitle = jobTitle;
  }

  public Integer getMinSalary() {
    return minSalary;
  }

  public void setMinSalary(Integer minSalary) {
    this.minSalary = minSalary;
  }

  public Integer getMaxSalary() {
    return maxSalary;
  }

  public void setMaxSalary(Integer maxSalary) {
    this.maxSalary = maxSalary;
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
