package by.pst.schepov.test.persistence.repository;

import by.pst.schepov.test.core.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
