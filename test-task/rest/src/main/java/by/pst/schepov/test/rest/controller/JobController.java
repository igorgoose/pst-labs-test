package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.rest.dto.JobDTO;
import by.pst.schepov.test.rest.dto.page.JobPageDTO;
import by.pst.schepov.test.service.JobService;
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
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public JobPageDTO getAll(Pageable pageable) {
        JobPageDTO jobPageDTO = new JobPageDTO(jobService.getAllJobs(pageable));

        jobPageDTO.getContent().forEach(arg -> addLinks(arg, arg.getId()));

        jobPageDTO.add(linkTo(methodOn(PersonController.class).getAll(pageable)).withSelfRel());
        return jobPageDTO;
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobDTO.Response.Full getOne(@PathVariable("id") int id) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.getJobById(id));
        addLinks(dto, dto.getId());
        return dto;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public JobDTO.Response.Full create(@RequestBody JobDTO.Request.Create requestDto) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.createJob(requestDto.convert()));
        addLinks(dto, dto.getId());
        return dto;
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public JobDTO.Response.Full update(@PathVariable("id") int id, @RequestBody JobDTO.Request.Create requestDto) {
        JobDTO.Response.Full dto = new JobDTO.Response.Full(jobService.updateJob(id, requestDto.convert()));
        addLinks(dto, dto.getId());
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") int id) {
        jobService.deleteJobById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }



    @SuppressWarnings("all")
    public void addLinks(RepresentationModel dto, int id) {
        dto.add(linkTo(methodOn(JobController.class).getOne(id)).withSelfRel().withType("GET"));
        dto.add(linkTo(methodOn(JobController.class).update(id, new JobDTO.Request.Create()))
                .withRel("update").withType("PUT"));
        dto.add(linkTo(methodOn(JobController.class).delete(id)).withRel("delete").withType("DELETE"));
    }
}
