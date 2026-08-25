package tungnn.tutor.java.spring.data.rest.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import tungnn.tutor.java.spring.data.rest.entity.JobHistory;
import tungnn.tutor.java.spring.data.rest.entity.JobHistoryId;

@RepositoryRestResource
public interface JobHistoryRepository extends JpaRepository<JobHistory, JobHistoryId> {}
