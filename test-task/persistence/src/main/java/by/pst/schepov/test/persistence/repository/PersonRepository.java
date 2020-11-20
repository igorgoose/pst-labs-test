package by.pst.schepov.test.persistence.repository;

import by.pst.schepov.test.core.entity.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Integer> {
}
