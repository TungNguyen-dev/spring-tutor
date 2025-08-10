package tungnn.tutor.java.spring.data.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "COUNTRIES", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "countryId")
public class Country {
  @Id
  @Column(name = "COUNTRY_ID", nullable = false, length = 2)
  private String countryId;

  @Column(name = "COUNTRY_NAME", length = 40)
  private String countryName;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "REGION_ID")
  private Region region;

  @OneToMany(mappedBy = "country")
  private Set<Location> locations = new LinkedHashSet<>();
}
