package by.pst.schepov.test.core.entity;


import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "cars")
public class Car implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            columnDefinition = "INTEGER"
    )
    private int id;

    private String model;

    @ManyToOne(optional = false)
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person owner;


    public Car(int id, String model) {
        this.id = id;
        this.model = model;
    }
}
