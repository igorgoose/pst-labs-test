package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.rest.dto.PassportDTO;
import by.pst.schepov.test.rest.dto.page.PassportPageDTO;
import by.pst.schepov.test.rest.link.PassportLinkCreator;
import by.pst.schepov.test.service.PassportService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/passports")
public class PassportController {

    private final PassportService passportService;
    private final PassportLinkCreator passportLinkCreator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public PassportPageDTO getAll(Pageable pageable) {
        PassportPageDTO passportPageDTO = new PassportPageDTO(passportService.getAllPassports(pageable));

        passportPageDTO.getContent().forEach(arg -> passportLinkCreator.addLinks(arg, arg.getNumber()));

        passportPageDTO.add(linkTo(methodOn(PassportController.class).getAll(pageable)).withSelfRel());
        return passportPageDTO;
    }

    @GetMapping("/{number}")
    @ResponseStatus(HttpStatus.OK)
    public PassportDTO.Response.Full getOne(@PathVariable("number") String number) {
        PassportDTO.Response.Full dto = new PassportDTO.Response.Full(passportService.
                getPassportByPassportNumber(number));
        passportLinkCreator.addLinks(dto);
        return new PassportDTO.Response.Full(passportService.getPassportByPassportNumber(number));
    }


}
