package sspd.sms.studentoptions.model.register;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import sspd.sms.classoptions.model.Classes;

import java.sql.Time;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student_schedule")
@Component
public class StudentSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int schedule_id;

    @ManyToOne
    @JoinColumn(name = "stu_id",nullable = false)
    private Student student;

    @ManyToOne
    @JoinColumn(name = "class_id",nullable = false)
    private Classes classes;

    @Column(name = "day_of_week")
    private String day_of_week;

    @Column(name = "start_time")
    private Time start_time;

    @Column(name = "end_time")
    private Time end_time;


}
