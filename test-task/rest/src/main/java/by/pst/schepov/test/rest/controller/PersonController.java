package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.rest.dto.PersonDTO;
import by.pst.schepov.test.rest.dto.page.PersonPageDTO;
import by.pst.schepov.test.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PersonPageDTO getAll(Pageable pageable) {
        PersonPageDTO personPageDTO = new PersonPageDTO(personService.getAllPeople(pageable));

        personPageDTO.getContent().forEach(arg -> addLinks(arg, arg.getId()));

        personPageDTO.add(linkTo(methodOn(PersonController.class).getAll(pageable)).withSelfRel());
        return personPageDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO.Response.Full getOne(@PathVariable("id") int id) {
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(personService.getPersonById(id));
        addLinks(dto, dto.getId());
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO.Response.Full create(@RequestBody PersonDTO.Request.Create requestDto) {
        Person person = personService.createPerson(requestDto.convert());
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(person);
        addLinks(dto, dto.getId());
        return dto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO.Response.Full update(@PathVariable("id") int id, @RequestBody PersonDTO.Request.Create requestDto) {
        Person person = personService.updatePerson(id, requestDto.convert());
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(person);
        addLinks(dto, dto.getId());
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        personService.deletePersonById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


    @SuppressWarnings("all")
    public void addLinks(RepresentationModel dto, int id) {
        dto.add(linkTo(methodOn(PersonController.class).getOne(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).update(id, new PersonDTO.Request.Create()))
                .withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(PersonController.class).delete(id)).withRel("delete").withType("DELETE"));
    }


}
