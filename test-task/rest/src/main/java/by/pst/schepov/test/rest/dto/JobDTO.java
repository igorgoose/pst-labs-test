package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Job;
import by.pst.schepov.test.core.entity.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

public enum JobDTO {
    ;

    private interface Id {
        int getId();
    }

    private interface Title {
        String getTitle();
    }

    private interface CompanyName {
        String getCompanyName();
    }

    public enum Request {
        ;

        private interface Employees {
            List<PersonDTO.Request.IdOnly> getEmployees();
        }

        @Data
        public static class IdOnly implements Id {
            private int id;

            public Job convert(){
                Job job = new Job();
                job.setId(id);
                return job;
            }
        }

        @Data
        public static class Create implements Id, Title, CompanyName, Employees {
            private int id;
            private String title;
            private String companyName;
            List<PersonDTO.Request.IdOnly> employees = new LinkedList<>();

            public Job convert(){
                List<Person> people = employees.stream().map(PersonDTO.Request.IdOnly::convert)
                        .collect(Collectors.toList());
                return new Job(id, title, companyName, people);
            }
        }

    }

    public enum Response {
        ;

        private interface Employees {
            List<PersonDTO.Response.Short> getEmployees();
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Short extends RepresentationModel<JobDTO.Response.Short>
                implements Id, Title, CompanyName {
            private int id;
            private String title;
            private String companyName;

            public Short(Job job) {
                id = job.getId();
                title = job.getTitle();
                companyName = job.getCompanyName();
            }
        }

        @EqualsAndHashCode(callSuper = true)
        @Data
        public static class Full extends RepresentationModel<CarDTO.Response.Full>
                implements Id, Title, CompanyName, Employees {
            private int id;
            private String title;
            private String companyName;
            private List<PersonDTO.Response.Short> employees;

            public Full(Job job) {
                id = job.getId();
                title = job.getTitle();
                companyName = job.getCompanyName();
                employees = job.getEmployees().stream().map(PersonDTO.Response.Short::new)
                        .collect(Collectors.toList());
            }
        }


    }
}
