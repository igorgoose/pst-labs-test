package by.pst.schepov.test.rest.dto.page;

import by.pst.schepov.test.core.entity.Job;
import by.pst.schepov.test.rest.dto.JobDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class JobPageDTO extends PagedModel<JobDTO.Response.Short> {

    private List<JobDTO.Response.Short> content;
    private int page;
    private int size;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public JobPageDTO(Page<Job> jobPage){
        content = convert(jobPage.getContent());
        page = jobPage.getPageable().getPageNumber();
        size = jobPage.getSize();
        totalPages = jobPage.getTotalPages();
        hasNext = jobPage.hasNext();
        hasPrevious = jobPage.hasPrevious();
    }

    private List<JobDTO.Response.Short> convert(List<Job> jobs){
        return jobs.stream().map(JobDTO.Response.Short::new).collect(Collectors.toList());
    }
}
