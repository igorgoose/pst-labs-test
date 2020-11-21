package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Car;
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



    public enum Request {
        ;

        private interface Owner {
            PersonDTO.Request.IdOnly getOwner();
        }
        @Data
        public static class Create implements Id, Model, Owner {
            private int id;
            private String model;
            private PersonDTO.Request.IdOnly owner = new PersonDTO.Request.IdOnly();

            public Car convert() {
                return new Car(id, model, owner.convert());
            }
        }

        @Data
        public static class IdOnly implements Id {
            private int id;

            public Car convert() {
                Car car = new Car();
                car.setId(id);
                return car;
            }
        }
    }

    public enum Response {
        ;

        private interface Owner {
            PersonDTO.Response.Short getOwner();
        }

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
