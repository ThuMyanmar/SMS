package sspd.sms.studentoptions.model.dropout;


import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sspd.sms.studentoptions.model.register.Student;

import java.sql.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "dropout_students")
@Component
public class DropoutStudent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int dropout_id;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "stu_id", unique = true)
    @NotNull(message = "Required Student")
    private Student student;

    @Column(name = "dropout_date")
    private Date dropout_date;

    @NotBlank(message = "Reason Required")
    @Column(name = "reason")
    private String reason;

    public DropoutStudent(Student student, String reason) {
        this.student = student;
        this.reason = reason;
    }
}
