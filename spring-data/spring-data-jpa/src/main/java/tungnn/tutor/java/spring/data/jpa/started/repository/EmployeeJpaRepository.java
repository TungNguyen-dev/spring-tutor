package tungnn.tutor.java.spring.data.jpa.started.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public interface EmployeeJpaRepository extends JpaRepository<Employee, Long> {}
