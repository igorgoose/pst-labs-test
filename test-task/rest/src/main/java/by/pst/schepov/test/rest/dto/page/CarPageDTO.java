package by.pst.schepov.test.rest.dto.page;

import by.pst.schepov.test.core.entity.Car;
import by.pst.schepov.test.rest.dto.CarDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.domain.Page;
import org.springframework.hateoas.PagedModel;

import java.util.List;
import java.util.stream.Collectors;


@EqualsAndHashCode(callSuper = true)
@Data
public class CarPageDTO extends PagedModel<CarDTO.Response.Short> {

    private List<CarDTO.Response.Short> content;
    private int page;
    private int size;
    private int totalPages;
    private boolean hasNext;
    private boolean hasPrevious;

    public CarPageDTO(Page<Car> carPage) {
        content = convert(carPage.getContent());
        page = carPage.getPageable().getPageNumber();
        size = carPage.getSize();
        totalPages = carPage.getTotalPages();
        hasNext = carPage.hasNext();
        hasPrevious = carPage.hasPrevious();
    }

    private List<CarDTO.Response.Short> convert(List<Car> cars) {
        return cars.stream().map(CarDTO.Response.Short::new).collect(Collectors.toList());
    }
}
