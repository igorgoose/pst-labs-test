package by.pst.schepov.test.rest.link;

import org.springframework.hateoas.RepresentationModel;

@SuppressWarnings("all")
public interface LinkCreator<ID> {
    void addLinks(RepresentationModel dto, ID id);
}
