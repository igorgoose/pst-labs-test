package by.pst.schepov.test.persistence.repository;

import by.pst.schepov.test.core.entity.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface JobRepository extends JpaRepository<Job, Integer> {
    boolean existsByCompanyNameAndTitle(String companyName, String title);
    Optional<Job> findByCompanyNameAndTitle(String companyName, String title);

}
