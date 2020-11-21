package by.pst.schepov.test.core.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
import java.util.List;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "people")
public class Person implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "INTEGER"
    )
    private int id;

    private String firstName;
    private String lastName;

    @OneToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Passport passport;

    @OneToMany(mappedBy = "owner", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REMOVE})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Car> cars = new LinkedList<>();

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "employees",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "job_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Job> jobs = new LinkedList<>();

    public Person(int id) {
        this.id = id;
    }

    public Person(String firstName, String lastName, Passport passport, List<Job> jobs) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.passport = passport;
        this.jobs = jobs;
    }

    public void addJob(Job job){
        jobs.add(job);
        job.getEmployees().add(this);
    }

    public void removeJob(Job job){
        jobs.remove(job);
        job.getEmployees().remove(this);
    }
}
