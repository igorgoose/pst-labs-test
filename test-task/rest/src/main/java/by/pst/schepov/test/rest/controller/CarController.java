package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.rest.dto.CarDTO;
import by.pst.schepov.test.rest.dto.page.CarPageDTO;
import by.pst.schepov.test.rest.link.CarLinkCreator;
import by.pst.schepov.test.service.CarService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/cars")
public class CarController {

    private final CarService carService;
    private final CarLinkCreator carLinkCreator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public CarPageDTO getAll(Pageable pageable) {
        CarPageDTO carPageDTO = new CarPageDTO(carService.getAllCars(pageable));

        carPageDTO.getContent().forEach(arg -> carLinkCreator.addLinks(arg, arg.getId()));

        carPageDTO.add(linkTo(methodOn(CarController.class).getAll(pageable)).withSelfRel());
        return carPageDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO.Response.Full getOne(@PathVariable("id") int id) {
        CarDTO.Response.Full dto = new CarDTO.Response.Full(carService.getCarById(id));
        carLinkCreator.addLinks(dto);
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CarDTO.Response.Full create(@RequestBody CarDTO.Request.Create requestDto) {
        CarDTO.Response.Full dto = new CarDTO.Response.Full(carService.createCar(requestDto.convert()));
        carLinkCreator.addLinks(dto);
        return dto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public CarDTO.Response.Full update(@PathVariable("id") int id,
                                       @RequestBody CarDTO.Request.Create requestDto) {
        CarDTO.Response.Full dto = new CarDTO.Response.Full(carService.updateCar(id, requestDto.convert()));
        carLinkCreator.addLinks(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        carService.deleteCarById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }




}
