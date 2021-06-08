package wolox.training.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("student")
@Data
public class Student extends User {

    @Column
    private String year;

}
