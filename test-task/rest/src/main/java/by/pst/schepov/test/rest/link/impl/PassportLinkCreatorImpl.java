package by.pst.schepov.test.rest.link.impl;

import by.pst.schepov.test.rest.controller.PassportController;
import by.pst.schepov.test.rest.dto.PassportDTO;
import by.pst.schepov.test.rest.link.PassportLinkCreator;
import by.pst.schepov.test.rest.link.PersonLinkCreator;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
@SuppressWarnings("all")
public class PassportLinkCreatorImpl implements PassportLinkCreator {

    @Setter
    @Autowired
    private PersonLinkCreator personLinkCreator;

    @Override
    public void addLinks(RepresentationModel dto, String id) {
        dto.add(linkTo(methodOn(PassportController.class).getOne(id)).withSelfRel());
    }


    @Override
    public void addLinks(PassportDTO.Response.Full dto) {
        addLinks(dto, dto.getNumber());
        personLinkCreator.addLinks(dto.getPerson(), dto.getPerson().getId());
    }

}
