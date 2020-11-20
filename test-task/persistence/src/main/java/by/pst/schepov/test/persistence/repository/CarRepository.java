package by.pst.schepov.test.persistence.repository;

import by.pst.schepov.test.core.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Integer> {
}
