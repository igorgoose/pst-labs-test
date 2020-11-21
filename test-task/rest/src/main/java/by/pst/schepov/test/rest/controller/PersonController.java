package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.rest.dto.PersonDTO;
import by.pst.schepov.test.rest.dto.page.PersonPageDTO;
import by.pst.schepov.test.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
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

        personPageDTO.getContent().forEach(this::addLinks);

        personPageDTO.add(linkTo(methodOn(PersonController.class).getAll(pageable)).withSelfRel());
        return personPageDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public PersonDTO.Response.Full getOne(@PathVariable("id") int id) {
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(personService.getPersonById(id));
        addLinks(dto);
        //todo add more links
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PersonDTO.Response.Full create(@RequestBody PersonDTO.Request.Create requestDto) {
        Person person = personService.createPerson(requestDto.convert());
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(person);
        addLinks(dto);
        return dto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    PersonDTO.Response.Full update(@PathVariable("id") int id, @RequestBody PersonDTO.Request.Create requestDto) {
        Person person = personService.updatePerson(id, requestDto.convert());
        PersonDTO.Response.Full dto = new PersonDTO.Response.Full(person);
        addLinks(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    void delete(@PathVariable("id") int id) {
        personService.deletePersonById(id);
    }

    private void addLinks(PersonDTO.Response.Full dto) {
        dto.add(linkTo(methodOn(PersonController.class).getOne(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto.getId(), new PersonDTO.Request.Create())).withRel("update").withType("PUT"));
    }

    private void addLinks(PersonDTO.Response.Short dto) {
        dto.add(linkTo(methodOn(PersonController.class).getOne(dto.getId())).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(PersonController.class).update(dto.getId(), new PersonDTO.Request.Create())).withRel("update").withType("PUT"));
    }


}
