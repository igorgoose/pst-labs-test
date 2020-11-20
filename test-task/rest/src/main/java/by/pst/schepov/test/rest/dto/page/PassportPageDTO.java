package by.pst.schepov.test.rest.dto.page;

import by.pst.schepov.test.core.entity.Passport;
import by.pst.schepov.test.rest.dto.PassportDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Data
public class PassportPageDTO extends PagedModel<PassportDTO.Response.Short> {

    List<PassportDTO.Response.Short> content;
    int page;
    int size;
    int totalPages;
    boolean hasNext;
    boolean hasPrevious;

    public PassportPageDTO(Page<Passport> passportPage){
        content = convert(passportPage.getContent());
        page = passportPage.getPageable().getPageNumber();
        size = passportPage.getSize();
        totalPages = passportPage.getTotalPages();
        hasNext = passportPage.hasNext();
        hasPrevious = passportPage.hasPrevious();
    }

    private List<PassportDTO.Response.Short> convert(List<Passport> passports){
        return passports.stream().map(PassportDTO.Response.Short::new).collect(Collectors.toList());
    }
}
