package wolox.training.models;

import javax.persistence.Column;
import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;
import lombok.Data;

@Entity
@DiscriminatorValue("teacher")
@Data
public class Teacher extends User {

    @Column
    private String subject;

}
