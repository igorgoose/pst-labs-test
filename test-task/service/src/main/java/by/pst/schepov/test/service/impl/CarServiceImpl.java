package by.pst.schepov.test.service.impl;

import by.pst.schepov.test.core.entity.Car;
import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.persistence.repository.CarRepository;
import by.pst.schepov.test.persistence.repository.PersonRepository;
import by.pst.schepov.test.service.CarService;
import by.pst.schepov.test.service.exception.InvalidRequestDataException;
import by.pst.schepov.test.service.exception.ResourceNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final PersonRepository personRepository;

    @Override
    public Page<Car> getAllCars(Pageable pageable) {
        return carRepository.findAll(pageable);
    }

    @Override
    public Car getCarById(int id) {
        return carRepository.findById(id).orElseThrow(() ->
                new ResourceNotFoundException("Car with id '" + id + "' does not exist."));
    }

    @Override
    public Car createCar(Car car) {
        if(car.getModel() == null) {
            throw new InvalidRequestDataException("Car model must not be null.");
        }
        Person persistedOwner = personRepository.findById(car.getOwner().getId()).orElseThrow(() ->
                new InvalidRequestDataException("Person with id '" + car.getOwner().getId() + "' does not exist."));
        car.setOwner(persistedOwner);
        return carRepository.save(car);
    }

    @Override
    public Car updateCar(int id, Car car) {
        Car persistedCar = carRepository.findById(id).orElseThrow(() ->
                new InvalidRequestDataException("Car with id'" + id + "' does not exist."));
        if(car.getModel() != null) {
            persistedCar.setModel(car.getModel());
        }
        if(car.getOwner() != null){
            Person persistedOwner = personRepository.findById(car.getOwner().getId()).orElseThrow(() ->
                    new InvalidRequestDataException("Person with id '" + car.getOwner().getId() + "' does not exist."));
            persistedCar.setOwner(persistedOwner);
        }
        return carRepository.save(persistedCar);
    }

    @Override
    public void deleteCarById(int id) {
        if(!carRepository.existsById(id)) {
            throw new InvalidRequestDataException("Car with id'" + id + "' does not exist.");
        }
        carRepository.deleteById(id);
    }


}
