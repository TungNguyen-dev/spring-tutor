package tungnn.tutor.java.spring.data.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tungnn.tutor.java.spring.data.rest.entity.Employee;
import tungnn.tutor.java.spring.data.rest.projection.EmployeeProjection;

@RepositoryRestResource(
    collectionResourceRel = "employees",
    path = "employees",
    excerptProjection = EmployeeProjection.class)
public interface EmployeeRepository extends JpaRepository<Employee, Long> {}
