package by.pst.schepov.test.core.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
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
    private String LastName;

    @OneToOne(optional = false, cascade = {CascadeType.REMOVE, CascadeType.PERSIST, CascadeType.MERGE})
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Passport passport;

    @OneToMany(mappedBy = "owner")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Car> cars;

    @ManyToMany
    @JoinTable(
            name = "employees",
            joinColumns = @JoinColumn(name = "job_id"),
            inverseJoinColumns = @JoinColumn(name = "person_id"))
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Job> jobs;

    public Person(int id) {
        this.id = id;
    }

}
