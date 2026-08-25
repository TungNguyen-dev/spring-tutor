package tungnn.tutor.java.spring.data.jpa.started.repository;

import org.springframework.data.repository.CrudRepository;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public interface EmployeeCrudRepository extends CrudRepository<Employee, Long> {}
