package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.service.PersonService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/people")
public class PersonController {

    private final PersonService personService;
}
