package by.pst.schepov.test.rest.link;

import by.pst.schepov.test.rest.dto.JobDTO;

public interface JobLinkCreator extends LinkCreator<Integer> {

    void addLinks(JobDTO.Response.Full dto);
}
