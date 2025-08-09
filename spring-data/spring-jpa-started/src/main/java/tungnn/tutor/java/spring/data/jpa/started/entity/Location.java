package tungnn.tutor.java.spring.data.jpa.started.entity;

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
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "LOCATIONS", schema = "HR")
public class Location {
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "LOCATIONS_id_gen")
  @SequenceGenerator(
      name = "LOCATIONS_id_gen",
      sequenceName = "LOCATIONS_SEQ",
      allocationSize = 100)
  @Column(name = "LOCATION_ID", nullable = false)
  private Short id;

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

  @OneToMany
  @JoinColumn(name = "LOCATION_ID")
  private Set<Department> departments = new LinkedHashSet<>();

  public Short getId() {
    return id;
  }

  public void setId(Short id) {
    this.id = id;
  }

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getCity() {
    return city;
  }

  public void setCity(String city) {
    this.city = city;
  }

  public String getStateProvince() {
    return stateProvince;
  }

  public void setStateProvince(String stateProvince) {
    this.stateProvince = stateProvince;
  }

  public Country getCountry() {
    return country;
  }

  public void setCountry(Country country) {
    this.country = country;
  }

  public Set<Department> getDepartments() {
    return departments;
  }

  public void setDepartments(Set<Department> departments) {
    this.departments = departments;
  }
}
