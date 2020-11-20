package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Person;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

public enum PersonDTO {
    ;

    private interface Id {
        int getId();
    }

    private interface FirstName {
        String getFirstName();
    }

    private interface LastName {
        String getLastName();
    }

    private interface Passport {
        PassportDTO.Response.Short getPassport();
    }

    private interface Cars {

    }

    public enum Request {
        ;

        @AllArgsConstructor
        @Data
        public static class CreatePassport implements Id {
            int id;
        }

    }
    public enum Response {
        ;
        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Short extends RepresentationModel<PersonDTO.Response.Short>
                implements Id, FirstName, LastName {
            int id;
            String firstName;
            String lastName;

            public Short(Person person) {
                id = person.getId();
                firstName = person.getFirstName();
                lastName = person.getLastName();
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Full extends RepresentationModel<PersonDTO.Response.Short>
                implements Id, FirstName, LastName, Passport {
            int id;
            String firstName;
            String lastName;
            PassportDTO.Response.Short passport;

            public Full(Person person) {
                id = person.getId();
                firstName = person.getFirstName();
                lastName = person.getLastName();
                passport = new PassportDTO.Response.Short(person.getPassport());
            }
        }

    }

}
