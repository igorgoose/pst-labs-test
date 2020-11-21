package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Passport;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.sql.Date;

public enum PassportDTO {
    ;

    private interface Number {
        String getNumber();
    }

    private interface DateOfIssue {
        Date getDateOfIssue();
    }

    private interface PersonResponse {
        PersonDTO.Response.Short getPerson();
    }

    private interface PersonRequest {
        PersonDTO.Request.IdOnly getPerson();
    }

    public enum Request {
        ;

        @Data
        public static class Create implements Number {
            private String number;

            public Passport convert() {
               return new Passport(number);
            }
        }

    }

    public enum Response {
        ;
        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Short extends RepresentationModel<PassportDTO.Response.Short>
                implements Number, DateOfIssue {
            String number;
            Date dateOfIssue;

            public Short(Passport passport){
                number = passport.getNumber();
                dateOfIssue = passport.getDateOfIssue();
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Full extends RepresentationModel<PassportDTO.Response.Full>
                implements Number, DateOfIssue, PersonResponse {
            String number;
            Date dateOfIssue;
            PersonDTO.Response.Short person;

            public Full(Passport passport){
                number = passport.getNumber();
                dateOfIssue = passport.getDateOfIssue();
                person = new PersonDTO.Response.Short(passport.getPerson());
            }
        }

    }



}
