package by.pst.schepov.test.rest.controller;

import by.pst.schepov.test.service.JobService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@AllArgsConstructor
@RestController
@RequestMapping("/jobs")
public class JobController {

    private final JobService jobService;
}
