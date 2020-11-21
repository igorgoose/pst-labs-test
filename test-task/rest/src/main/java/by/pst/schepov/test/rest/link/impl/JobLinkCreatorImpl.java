package by.pst.schepov.test.rest.link.impl;

import by.pst.schepov.test.rest.controller.JobController;
import by.pst.schepov.test.rest.dto.JobDTO;
import by.pst.schepov.test.rest.dto.PersonDTO;
import by.pst.schepov.test.rest.link.JobLinkCreator;
import by.pst.schepov.test.rest.link.PersonLinkCreator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("all")
public class JobLinkCreatorImpl implements JobLinkCreator {

    @Setter
    @Autowired
    private PersonLinkCreator personLinkCreator;

    @Override
    public void addLinks(JobDTO.Response.Full dto) {
        addLinks(dto, dto.getId());
        for (PersonDTO.Response.Short employee : dto.getEmployees()) {
            personLinkCreator.addLinks(employee, employee.getId());
        }
    }

    @Override
    public void addLinks(RepresentationModel dto, Integer id) {
        dto.add(linkTo(methodOn(JobController.class).getOne(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(JobController.class).update(id, new JobDTO.Request.Create()))
                .withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(JobController.class).delete(id)).withRel("delete").withType("DELETE"));
    }
}
