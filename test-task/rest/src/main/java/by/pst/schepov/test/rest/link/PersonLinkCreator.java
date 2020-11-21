package by.pst.schepov.test.rest.link;

import by.pst.schepov.test.rest.dto.PersonDTO;

public interface PersonLinkCreator extends LinkCreator<Integer> {

    void addLinks(PersonDTO.Response.Full dto);
}
