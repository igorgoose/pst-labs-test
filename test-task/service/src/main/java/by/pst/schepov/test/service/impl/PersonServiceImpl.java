package by.pst.schepov.test.service.impl;

import by.pst.schepov.test.core.entity.Job;
import by.pst.schepov.test.core.entity.Passport;
import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.persistence.repository.JobRepository;
import by.pst.schepov.test.persistence.repository.PassportRepository;
import by.pst.schepov.test.persistence.repository.PersonRepository;
import by.pst.schepov.test.service.PersonService;
import by.pst.schepov.test.service.exception.InvalidRequestDataException;
import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;
import java.util.LinkedList;
import java.util.List;

@AllArgsConstructor
@Service
@Transactional
public class PersonServiceImpl implements PersonService {

    private final PersonRepository personRepository;
    private final JobRepository jobRepository;
    private final PassportRepository passportRepository;

    @Override
    public Page<Person> getAllPeople(Pageable pageable) {
        return personRepository.findAll(pageable);
    }

    @Override
    public Person getPersonById(int id) {
        return personRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Person with id '" + id + "' doesn't exist."));
    }

    @Override
    public Person createPerson(Person person) {
        bindPassport(person, person.getPassport());
        List<Job> persistedJobs = new LinkedList<>();
        for (Job job : person.getJobs()) {
            Job persistedJob = jobRepository.findById(job.getId()).orElseThrow(() ->
                    new InvalidRequestDataException("Invalid job id(" + job.getId() + ")."));
            persistedJob.getEmployees().add(person);
            persistedJobs.add(persistedJob);
        }
        person.setJobs(persistedJobs);
        return personRepository.save(person);
    }

    @Override
    public Person updatePerson(int id, Person person) {
        Person persistedPerson = personRepository.findById(id).orElseThrow(
                () -> new InvalidRequestDataException("Person with id '" + id + "' doesn't exist."));
        Passport passport = person.getPassport();
        if (passport != null && !passport.getNumber().equals(persistedPerson.getPassport().getNumber())) {
            bindPassport(persistedPerson, passport);
        }
        if(person.getFirstName() != null) {
            persistedPerson.setFirstName(person.getFirstName());
        }
        if(person.getLastName() != null) {
            persistedPerson.setLastName(person.getLastName());
        }
        bindJobs(persistedPerson, person.getJobs());
        return personRepository.save(persistedPerson);
    }

    @Override
    public void deletePersonById(int id) {
        if(!personRepository.existsById(id)){
            throw new InvalidRequestDataException("Person with id '" + id + "' doesn't exist.");
        }
        personRepository.deleteById(id);
    }


    private void bindPassport(Person persistedPerson, Passport passport) {
        if (passportRepository.existsById(passport.getNumber())) {
            throw new InvalidRequestDataException("Passport with number '"
                    + passport.getNumber() + "' already exists.");
        }
        passportRepository.deleteById(persistedPerson.getPassport().getNumber());
        passport.setDateOfIssue(new Date(System.currentTimeMillis()));
        persistedPerson.setPassport(passport);
        passport.setPerson(persistedPerson);
    }

    private void bindJobs(Person persistedPerson, List<Job> jobs) {
        persistedPerson.getJobs().clear();
        for (Job job : jobs) {
            Job persistedJob = jobRepository.findById(job.getId()).orElseThrow(() ->
                    new InvalidRequestDataException("Invalid job id(" + job.getId() + ")."));
            persistedJob.getEmployees().add(persistedPerson);
            persistedPerson.getJobs().add(persistedJob);
        }
    }

}
