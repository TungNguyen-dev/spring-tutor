package tungnn.tutor.java.spring.data.rest.entity;

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
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "DEPARTMENTS", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "departmentId")
public class Department {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "department_id_gen")
  @SequenceGenerator(
      name = "department_id_gen",
      sequenceName = "DEPARTMENTS_SEQ",
      allocationSize = 10)
  @Column(name = "DEPARTMENT_ID", nullable = false)
  private Short departmentId;

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

  @OneToMany(mappedBy = "department")
  private Set<Employee> employees = new LinkedHashSet<>();

  @OneToMany(mappedBy = "department")
  private Set<JobHistory> jobHistories = new LinkedHashSet<>();
}
