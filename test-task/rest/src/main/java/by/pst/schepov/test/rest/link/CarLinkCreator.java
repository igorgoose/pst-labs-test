package by.pst.schepov.test.rest.link;

import by.pst.schepov.test.rest.dto.CarDTO;

public interface CarLinkCreator extends LinkCreator<Integer> {

    void addLinks(CarDTO.Response.Full dto);

}
