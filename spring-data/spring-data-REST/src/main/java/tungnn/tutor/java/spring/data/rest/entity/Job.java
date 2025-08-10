package tungnn.tutor.java.spring.data.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "JOBS", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "jobId")
public class Job {
  @Id
  @Column(name = "JOB_ID", nullable = false, length = 10)
  private String jobId;

  @Column(name = "JOB_TITLE", nullable = false, length = 35)
  private String jobTitle;

  @Column(name = "MIN_SALARY")
  private Integer minSalary;

  @Column(name = "MAX_SALARY")
  private Integer maxSalary;

  @OneToMany(mappedBy = "job")
  private Set<Employee> employees = new LinkedHashSet<>();

  @OneToMany(mappedBy = "job")
  private Set<JobHistory> jobHistories = new LinkedHashSet<>();
}
