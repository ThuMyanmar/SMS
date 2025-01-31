package sspd.sms.classoptions.model;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.stereotype.Component;
import sspd.sms.teacheroptions.model.Teacher;

@Data
@Entity
@Table(name = "teacher_class")
@Component
public class ClasshasTeacher {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int teacher_classid;

    @ManyToOne()
    @JoinColumn(name = "teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne()
    @JoinColumn@Column(name = "class_id",nullable = false)
    private Classes classes;

    public ClasshasTeacher() {}

}
