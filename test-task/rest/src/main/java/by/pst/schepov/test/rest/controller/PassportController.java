package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.core.entity.Passport;
import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.rest.dto.PassportDTO;
import by.pst.schepov.test.rest.dto.page.PassportPageDTO;
import by.pst.schepov.test.service.PassportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@AllArgsConstructor
@RestController
@RequestMapping("/passports")
public class PassportController {

    private final PassportService passportService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PassportPageDTO getAll(Pageable pageable) {
        PassportPageDTO passportPageDTO = new PassportPageDTO(passportService.getAllPassports(pageable));

        passportPageDTO.getContent().forEach(arg ->
                arg.add(linkTo(methodOn(PassportController.class).getOne(arg.getNumber())).withSelfRel()));

        passportPageDTO.add(linkTo(methodOn(PassportController.class).getAll(pageable)).withSelfRel());
        return passportPageDTO;
    }

    @GetMapping("/{number}")
    @ResponseStatus(HttpStatus.OK)
    public PassportDTO.Response.Full getOne(@PathVariable("number") String number) {
        return new PassportDTO.Response.Full(passportService.getPassportByPassportNumber(number));
    }

    //todo
//    @PostMapping
//    @ResponseStatus(HttpStatus.CREATED)
//    public PassportDTO.Response.Full create(PassportDTO.Request.Create dto){
//        Passport passport = new Passport(dto.getNumber(), new Person(dto.getPerson().getId()));
//        return new PassportDTO.Response.Full(passportService.createPassport(passport));
//    }
//
//    @DeleteMapping("/{number}")
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    public void delete(@PathVariable("number") String number) {
//        passportService.deletePassportByNumber(number);
//    }

}
