package by.pst.schepov.test.rest.link.impl;

import by.pst.schepov.test.rest.controller.PersonController;
import by.pst.schepov.test.rest.dto.CarDTO;
import by.pst.schepov.test.rest.dto.JobDTO;
import by.pst.schepov.test.rest.dto.PersonDTO;
import by.pst.schepov.test.rest.link.CarLinkCreator;
import by.pst.schepov.test.rest.link.JobLinkCreator;
import by.pst.schepov.test.rest.link.PassportLinkCreator;
import by.pst.schepov.test.rest.link.PersonLinkCreator;
import lombok.AllArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("all")
public class PersonLinkCreatorImpl implements PersonLinkCreator {

    @Setter
    @Autowired
    private CarLinkCreator carLinkCreator;
    @Setter
    @Autowired
    private JobLinkCreator jobLinkCreator;
    @Setter
    @Autowired
    private PassportLinkCreator passportLinkCreator;

    @Override
    public void addLinks(RepresentationModel dto, Integer id) {
        dto.add(linkTo(methodOn(PersonController.class).getOne(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).update(id, new PersonDTO.Request.Create()))
                .withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(id)).withRel("delete").withType("DELETE"));
    }


    @Override
    public void addLinks(PersonDTO.Response.Full dto) {
        addLinks(dto, dto.getId());
        for (CarDTO.Response.Short car : dto.getCars()) {
            carLinkCreator.addLinks(car, car.getId());
        }
        for (JobDTO.Response.Short job : dto.getJobs()) {
            jobLinkCreator.addLinks(job, job.getId());
        }
        passportLinkCreator.addLinks(dto.getPassport(), dto.getPassport().getNumber());
    }

}
