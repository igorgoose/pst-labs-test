package by.pst.schepov.test.service.impl;

import by.pst.schepov.test.core.entity.Job;
import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.persistence.repository.JobRepository;
import by.pst.schepov.test.persistence.repository.PersonRepository;
import by.pst.schepov.test.service.JobService;
import by.pst.schepov.test.service.exception.InvalidRequestDataException;
import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class JobServiceImpl implements JobService {

    private final JobRepository jobRepository;
    private final PersonRepository personRepository;

    @Override
    public Page<Job> getAllJobs(Pageable pageable) {
        return jobRepository.findAll(pageable);
    }

    @Override
    public Job getJobById(int id) {
        return jobRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Job with id '" + id + "' " +
                "does not exist."));
    }

    @Override
    public Job createJob(Job job) {
        if(job.getTitle() == null || job.getCompanyName() == null) {
            throw new InvalidRequestDataException("Company name and title must not be null.");
        }
        if(jobRepository.existsByCompanyNameAndTitle(job.getCompanyName(), job.getTitle())){
            throw new InvalidRequestDataException("Job with such company name('" + job.getCompanyName() + "') " +
                    "and title('" + job.getTitle() + "') already exists.");
        }
        bindEmployees(job, job.getEmployees());
        return jobRepository.save(job);
    }

    @Override
    public Job updateJob(int id, Job job) {
        Job persistedJob = jobRepository.findById(id).orElseThrow(() ->
                new InvalidRequestDataException("Job with id '" + id + "' does not exist."));
        if(job.getTitle() == null || job.getCompanyName() == null) {
            throw new InvalidRequestDataException("Company name and title must not be null.");
        }
        if(jobRepository.findByCompanyNameAndTitle(job.getCompanyName(), job.getTitle())
                .orElse(persistedJob).getId() != persistedJob.getId()){
            throw new InvalidRequestDataException("Job with such company name('" + job.getCompanyName() + "') " +
                    "and title('" + job.getTitle() + "') already exists.");
        }
        persistedJob.setCompanyName(job.getCompanyName());
        persistedJob.setTitle(job.getTitle());
        bindEmployees(persistedJob, job.getEmployees());
        return jobRepository.save(persistedJob);
    }

    @Override
    public void deleteJobById(int id) {
        if(!jobRepository.existsById(id)) {
            throw new InvalidRequestDataException("Job with id '" + id + "' does not exist.");
        }
        jobRepository.deleteById(id);
    }

    private void bindEmployees(Job job, List<Person> employees){
        job.getEmployees().clear();
        for (Person employee : employees) {
            Person persistedEmployee = personRepository.findById(employee.getId()).orElseThrow(() ->
                    new InvalidRequestDataException("Person with id '" + employee.getId() + "' does not exist."));
            job.getEmployees().add(persistedEmployee);
            if(!persistedEmployee.getJobs().contains(job)){
                persistedEmployee.getJobs().add(job);
            }
        }
    }


}
