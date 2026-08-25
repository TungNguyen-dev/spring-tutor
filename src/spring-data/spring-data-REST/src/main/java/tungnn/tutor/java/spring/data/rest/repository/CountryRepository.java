package tungnn.tutor.java.spring.data.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tungnn.tutor.java.spring.data.rest.entity.Country;

@RepositoryRestResource
public interface CountryRepository extends JpaRepository<Country, String> {}
