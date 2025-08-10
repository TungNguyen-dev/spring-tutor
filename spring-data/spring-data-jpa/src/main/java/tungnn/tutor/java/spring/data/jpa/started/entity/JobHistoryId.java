package tungnn.tutor.java.spring.data.jpa.started.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serializable;
import java.time.LocalDate;
import java.util.Objects;
import org.hibernate.Hibernate;

@Embeddable
public class JobHistoryId implements Serializable {
  private static final long serialVersionUID = 4210536596037693643L;

  @Column(name = "EMPLOYEE_ID", nullable = false)
  private Integer employeeId;

  @Column(name = "START_DATE", nullable = false)
  private LocalDate startDate;

  public Integer getEmployeeId() {
    return employeeId;
  }

  public void setEmployeeId(Integer employeeId) {
    this.employeeId = employeeId;
  }

  public LocalDate getStartDate() {
    return startDate;
  }

  public void setStartDate(LocalDate startDate) {
    this.startDate = startDate;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    JobHistoryId entity = (JobHistoryId) o;
    return Objects.equals(this.employeeId, entity.employeeId)
        && Objects.equals(this.startDate, entity.startDate);
  }

  @Override
  public int hashCode() {
    return Objects.hash(employeeId, startDate);
  }
}
