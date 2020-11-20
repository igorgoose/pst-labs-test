package by.pst.schepov.test.service.impl;

import by.pst.schepov.test.core.entity.Passport;
import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.persistence.repository.PassportRepository;
import by.pst.schepov.test.persistence.repository.PersonRepository;
import by.pst.schepov.test.service.PassportService;
import by.pst.schepov.test.service.exception.InvalidRequestDataException;
import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import by.pst.schepov.test.service.exception.UnexpectedError;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Date;

@AllArgsConstructor
@Service
@Transactional
public class PassportServiceImpl implements PassportService {

    private final PassportRepository passportRepository;
    private final PersonRepository personRepository;

    @Override
    public Page<Passport> getAllPassports(Pageable pageable) {
        return passportRepository.findAll(pageable);
    }

    @Override
    public Passport getPassportByPassportNumber(String number) {
        return passportRepository.findById(number).orElseThrow(() ->
                new ResourceNotFoundException("Passport with number '" + number + "' does not exist."));
    }

    @Override
    public void deletePassportByNumber(String number) {
        if(!passportRepository.existsById(number)) {
            throw new InvalidRequestDataException("Cannot delete a non-existent passport(number='" + number + "').");
        }
    }

    @Override
    public Passport createPassport(Passport passport) {
        if(!personRepository.existsById(passport.getPerson().getId())) {
            throw new InvalidRequestDataException("Invalid person id(" + passport.getPerson().getId() + ").");
        }
        Person person = personRepository.getOne(passport.getPerson().getId());
        passport.setDateOfIssue(new Date(System.currentTimeMillis()));
        person.setPassport(passport);
        personRepository.save(person);
        return passportRepository.findByPersonId(person.getId()).orElseThrow(() ->
                new UnexpectedError("Could not create passport"));
    }


}
