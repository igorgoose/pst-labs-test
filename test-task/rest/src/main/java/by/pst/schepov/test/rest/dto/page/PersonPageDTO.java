package by.pst.schepov.test.rest.dto.page;

import by.pst.schepov.test.core.entity.Person;
import by.pst.schepov.test.rest.dto.PersonDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.stream.Collectors;

@EqualsAndHashCode(callSuper = true)
@Data
public class PersonPageDTO extends PagedModel<PersonDTO.Response.Short> {

    private List<PersonDTO.Response.Short> content;
    private int page;
    private int size;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public PersonPageDTO(Page<Person> personPage){
        content = convert(personPage.getContent());
        page = personPage.getPageable().getPageNumber();
        size = personPage.getSize();
        totalPages = personPage.getTotalPages();
        hasNext = personPage.hasNext();
        hasPrevious = personPage.hasPrevious();
    }

    private List<PersonDTO.Response.Short> convert(List<Person> people){
        return people.stream().map(PersonDTO.Response.Short::new).collect(Collectors.toList());
    }
}
