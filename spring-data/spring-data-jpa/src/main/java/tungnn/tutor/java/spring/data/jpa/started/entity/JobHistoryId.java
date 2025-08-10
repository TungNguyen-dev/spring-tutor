package tungnn.tutor.java.spring.data.jpa.started.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import java.io.Serial;
import java.io.Serializable;
import java.time.LocalDate;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

@Embeddable
@Getter
@Setter
@EqualsAndHashCode(of = {"employeeId", "startDate"})
public class JobHistoryId implements Serializable {
  @Serial private static final long serialVersionUID = 4210536596037693643L;

  @Column(name = "EMPLOYEE_ID", nullable = false)
  private Integer employeeId;

  @Column(name = "START_DATE", nullable = false)
  private LocalDate startDate;
}
