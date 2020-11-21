package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Job;
import by.pst.schepov.test.core.entity.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

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

    public enum Request {
        ;

        private interface Jobs {
            List<JobDTO.Request.IdOnly> getJobs();
        }

        private interface Passport {
            PassportDTO.Request.Create getPassport();
        }

        @Data
        public static class IdOnly implements Id {
            private int id;

            public Person convert() {
                Person person = new Person();
                person.setId(id);
                return person;
            }
        }

        @Data
        public static class Create implements FirstName, LastName, Passport, Jobs {
            private String firstName;
            private String lastName;
            private PassportDTO.Request.Create passport = new PassportDTO.Request.Create();
            private List<JobDTO.Request.IdOnly> jobs = new LinkedList<>();

            public Person convert(){
                List<Job> jobList = jobs.stream().map(JobDTO.Request.IdOnly::convert).collect(Collectors.toList());
                return new Person(firstName, lastName, passport.convert(), jobList);
            }
        }

    }
    public enum Response {
        ;

        private interface Jobs {
            List<JobDTO.Response.Short> getJobs();
        }

        private interface Cars {
            List<CarDTO.Response.Short> getCars();
        }

        private interface Passport {
            PassportDTO.Response.Short getPassport();
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Short extends RepresentationModel<PersonDTO.Response.Short>
                implements Id, FirstName, LastName {
            private int id;
            private String firstName;
            private String lastName;

            public Short(Person person) {
                id = person.getId();
                firstName = person.getFirstName();
                lastName = person.getLastName();
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Full extends RepresentationModel<PersonDTO.Response.Short>
                implements Id, FirstName, LastName, Passport, Jobs, Cars {
            private int id;
            private String firstName;
            private String lastName;
            private PassportDTO.Response.Short passport;
            private List<JobDTO.Response.Short> jobs;
            private List<CarDTO.Response.Short> cars;

            public Full(Person person) {
                id = person.getId();
                firstName = person.getFirstName();
                lastName = person.getLastName();
                passport = new PassportDTO.Response.Short(person.getPassport());
                jobs = person.getJobs().stream().map(JobDTO.Response.Short::new).collect(Collectors.toList());
                cars = person.getCars().stream().map(CarDTO.Response.Short::new).collect(Collectors.toList());
            }
        }

    }

}
