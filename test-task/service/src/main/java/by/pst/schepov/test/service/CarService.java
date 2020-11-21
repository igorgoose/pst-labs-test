package by.pst.schepov.test.service;

import by.pst.schepov.test.core.entity.Car;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface CarService {
    Page<Car> getAllCars(Pageable pageable);

    Car getCarById(int id);

    Car createCar(Car car);

    Car updateCar(int id, Car car);

    void deleteCarById(int id);
}
