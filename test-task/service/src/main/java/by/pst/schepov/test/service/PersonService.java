package by.pst.schepov.test.service;

import by.pst.schepov.test.core.entity.Person;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PersonService {
    Page<Person> getAllPeople(Pageable pageable);

    Person getPersonById(int id);

    Person createPerson(Person person);

    Person updatePerson(int id, Person person);

    void deletePersonById(int id);
}
