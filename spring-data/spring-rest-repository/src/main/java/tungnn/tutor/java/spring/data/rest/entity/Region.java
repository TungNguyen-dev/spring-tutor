package tungnn.tutor.java.spring.data.rest.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "REGIONS", schema = "HR")
public class Region {
  @Id
  @Column(name = "REGION_ID", nullable = false)
  private Long id;

  @Column(name = "REGION_NAME", length = 25)
  private String regionName;

  @OneToMany
  @JoinColumn(name = "REGION_ID")
  private Set<Country> countries = new LinkedHashSet<>();

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getRegionName() {
    return regionName;
  }

  public void setRegionName(String regionName) {
    this.regionName = regionName;
  }

  public Set<Country> getCountries() {
    return countries;
  }

  public void setCountries(Set<Country> countries) {
    this.countries = countries;
  }
}
