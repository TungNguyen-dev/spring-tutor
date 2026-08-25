package tungnn.tutor.java.spring.data.jpa.started.repository;

import org.springframework.data.repository.PagingAndSortingRepository;
import tungnn.tutor.java.spring.data.jpa.started.entity.Employee;

public interface EmployeePagingAndSortingRepository
    extends PagingAndSortingRepository<Employee, Long> {}
