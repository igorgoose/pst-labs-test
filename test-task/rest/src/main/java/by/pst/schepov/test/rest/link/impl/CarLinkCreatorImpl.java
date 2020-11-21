package by.pst.schepov.test.rest.link.impl;

import by.pst.schepov.test.rest.controller.CarController;
import by.pst.schepov.test.rest.dto.CarDTO;
import by.pst.schepov.test.rest.link.CarLinkCreator;
import by.pst.schepov.test.rest.link.PersonLinkCreator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("all")
public class CarLinkCreatorImpl implements CarLinkCreator {

    @Setter
    @Autowired
    private PersonLinkCreator personLinkCreator;


    @Override
    public void addLinks(RepresentationModel dto, Integer id) {
        dto.add(linkTo(methodOn(CarController.class).getOne(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(CarController.class).update(id, new CarDTO.Request.Create()))
                .withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(CarController.class).delete(id)).withRel("delete").withType("DELETE"));
    }

    @Override
    public void addLinks(CarDTO.Response.Full dto) {
        addLinks(dto, dto.getId());
        personLinkCreator.addLinks(dto.getOwner(), dto.getOwner().getId());
    }
}
