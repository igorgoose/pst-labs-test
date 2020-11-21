package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.rest.dto.JobDTO;
import by.pst.schepov.test.rest.dto.page.JobPageDTO;
import by.pst.schepov.test.rest.link.JobLinkCreator;
import by.pst.schepov.test.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@AllArgsConstructor
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
    private final JobLinkCreator jobLinkCreator;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public JobPageDTO getAll(Pageable pageable) {
        JobPageDTO jobPageDTO = new JobPageDTO(jobService.getAllJobs(pageable));

        jobPageDTO.getContent().forEach(arg -> jobLinkCreator.addLinks(arg, arg.getId()));

        jobPageDTO.add(linkTo(methodOn(PersonController.class).getAll(pageable)).withSelfRel());
        return jobPageDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobDTO.Response.Full getOne(@PathVariable("id") int id) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.getJobById(id));
        jobLinkCreator.addLinks(dto);
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobDTO.Response.Full create(@RequestBody JobDTO.Request.Create requestDto) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.createJob(requestDto.convert()));
        jobLinkCreator.addLinks(dto);
        return dto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobDTO.Response.Full update(@PathVariable("id") int id, @RequestBody JobDTO.Request.Create requestDto) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.updateJob(id, requestDto.convert()));
        jobLinkCreator.addLinks(dto);
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        jobService.deleteJobById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    
}
