package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;

//    @GetMapping
//    public CarPage getAll(Pageable pageable){
//
//    }

}
