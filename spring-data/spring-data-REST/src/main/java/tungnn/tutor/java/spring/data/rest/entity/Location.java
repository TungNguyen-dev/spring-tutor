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
@Table(name = "LOCATIONS", schema = "HR")
@Getter
@Setter
@EqualsAndHashCode(of = "locationId")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "locations_id_gen")
  @SequenceGenerator(
      name = "locations_id_gen",
      sequenceName = "LOCATIONS_SEQ",
      allocationSize = 100)
  @Column(name = "LOCATION_ID", nullable = false)
  private Short locationId;

  @Column(name = "STREET_ADDRESS", length = 40)
  private String streetAddress;

  @Column(name = "POSTAL_CODE", length = 12)
  private String postalCode;

  @Column(name = "CITY", nullable = false, length = 30)
  private String city;

  @Column(name = "STATE_PROVINCE", length = 25)
  private String stateProvince;

  @ManyToOne(fetch = FetchType.LAZY)
  @OnDelete(action = OnDeleteAction.RESTRICT)
  @JoinColumn(name = "COUNTRY_ID")
  private Country country;

  @OneToMany(mappedBy = "location")
  private Set<Department> departments = new LinkedHashSet<>();
}
