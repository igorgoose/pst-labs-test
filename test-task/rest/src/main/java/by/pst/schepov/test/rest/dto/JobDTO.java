package by.pst.schepov.test.rest.dto;

import by.pst.schepov.test.core.entity.Car;
import by.pst.schepov.test.core.entity.Job;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;

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
        public static class Create implements Id, Title, CompanyName {
            private int id;
            private String title;
            private String companyName;

            public Job convert(){
                return new Job(id, title, companyName);
            }
        }

    }

    public enum Response {
        ;

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
                implements Id, Title, CompanyName {
            private int id;
            private String title;
            private String companyName;

            public Full(Job job) {
                id = job.getId();
                title = job.getTitle();
                companyName = job.getCompanyName();
            }
        }


    }
}
