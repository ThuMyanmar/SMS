package sspd.sms.registeroptions.Models;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.stereotype.Component;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.studentoptions.model.Student;

import java.sql.Date;
import java.time.LocalDate;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Component
@Entity(name = "register")
public class Register {

    @Setter
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int re_id;

    @Column(name = "re_date")
    private  Date re_date = Date.valueOf(LocalDate.now());

    @Setter
    @ManyToOne
    @JoinColumn(name = "class_id", nullable = false)
    private Classes classes;

    @Setter
    @ManyToOne
    @JoinColumn(name = "stu_id", nullable = false)
    private Student student;

    public Register( Classes classes, Student student) {
        this.classes = classes;
        this.student = student;
    }







}
