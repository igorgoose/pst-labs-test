package by.pst.schepov.test.persistence.repository;

import by.pst.schepov.test.core.entity.Passport;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PassportRepository extends JpaRepository<Passport, String> {
    Optional<Passport> findByPersonId(int id);
}
