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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "COUNTRIES", schema = "HR")
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

  @OneToMany
  @JoinColumn(name = "COUNTRY_ID")
  private Set<Location> locations = new LinkedHashSet<>();

  public String getCountryId() {
    return countryId;
  }

  public void setCountryId(String countryId) {
    this.countryId = countryId;
  }

  public String getCountryName() {
    return countryName;
  }

  public void setCountryName(String countryName) {
    this.countryName = countryName;
  }

  public Region getRegion() {
    return region;
  }

  public void setRegion(Region region) {
    this.region = region;
  }

  public Set<Location> getLocations() {
    return locations;
  }

  public void setLocations(Set<Location> locations) {
    this.locations = locations;
  }
}
