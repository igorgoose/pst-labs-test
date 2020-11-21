package by.pst.schepov.test.service;

import by.pst.schepov.test.core.entity.Job;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface JobService {

    Page<Job> getAllJobs(Pageable pageable);

    Job getJobById(int id);

    Job createJob(Job job);

    Job updateJob(int id, Job job);

    void deleteJobById(int id);
}
