package by.pst.schepov.test.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.LinkedList;
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
    private List<Person> employees = new LinkedList<>();

    public Job(int id, String title, String companyName) {
        this.id = id;
        this.title = title;
        this.companyName = companyName;
    }

    public void addEmployee(Person employee) {
        employees.add(employee);
        employee.getJobs().add(this);
    }

    public void removeEmployee(Person employee) {
        employees.remove(employee);
        employee.getJobs().remove(this);
    }

}
