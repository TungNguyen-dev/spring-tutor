package tungnn.tutor.java.spring.data.jpa.started.entity;

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
@Table(name = "REGIONS", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "regionId")
public class Region {
  @Id
  @Column(name = "REGION_ID", nullable = false)
  private Long regionId;

  @Column(name = "REGION_NAME", length = 25)
  private String regionName;

  @OneToMany(mappedBy = "region")
  private Set<Country> countries = new LinkedHashSet<>();
}
