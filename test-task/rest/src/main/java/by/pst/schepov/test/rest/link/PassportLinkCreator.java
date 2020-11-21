package by.pst.schepov.test.rest.link;

import by.pst.schepov.test.rest.dto.PassportDTO;

public interface PassportLinkCreator extends LinkCreator<String> {

    void addLinks(PassportDTO.Response.Full dto);
}
