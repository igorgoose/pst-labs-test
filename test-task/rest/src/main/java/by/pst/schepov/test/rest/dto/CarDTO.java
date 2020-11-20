package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Car;
import by.pst.schepov.test.core.entity.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

public enum CarDTO {
    ;

    private interface Id {
        int getId();
    }

    private interface Model {
        String getModel();
    }

    private interface Owner {
        PersonDTO.Response.Short getOwner();
    }

    public enum Request {
        ;
    }

    public enum Response {
        ;

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Short extends RepresentationModel<CarDTO.Response.Short>
                implements Id, Model {
            int id;
            String model;

            public Short(Car car) {
                id = car.getId();
                model = car.getModel();
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Full extends RepresentationModel<CarDTO.Response.Full>
                implements Id, Model, Owner {
            int id;
            String model;
            PersonDTO.Response.Short owner;

            public Full(Car car) {
                id = car.getId();
                model = car.getModel();
                owner = new PersonDTO.Response.Short(car.getOwner());
            }
        }


    }
}