package by.pst.schepov.test.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job implements Serializable {

    @Id
    @Column(
            columnDefinition = "INTEGER"
    )
    private int id;

    private String title;

    private String companyName;

    @ManyToMany(mappedBy = "jobs")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<Person> employees;
}
