package by.pst.schepov.test.core.entity;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "passports")
public class Passport implements Serializable {

    @Id
    @Column(
            columnDefinition = "CHAR(9)"
    )
    private String number;

    private Date dateOfIssue;

    @OneToOne(mappedBy = "passport")
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private Person person;


    public Passport(String number, Person person) {
        this.number = number;
        this.person = person;
    }

    public Passport(String number) {
        this.number = number;
    }
}
