package sspd.sms.classoptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.registeroptions.Models.Register;

import java.sql.Date;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name="classes")
@Entity
public class Classes {

    @Id
    @GeneratedValue(strategy= GenerationType.AUTO)
    private int class_id;

    @NotBlank
    @Column(name="class_name",length = 100)
    private String class_name;


    @Column(name = "date",nullable = false)
    private Date date;


    @OneToOne
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;


    @Column(name="scedule")
    private int scedule;


    @Column(name="limit_stu")
    private int limit_stu;

    @Column(name = "status")
    private int status;

    @OneToMany(mappedBy = "classes",fetch = FetchType.LAZY)
    private Set<ClasshasTeacher> classhasTeacherSet;

    @OneToMany(mappedBy = "classes", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<Register> registerSet;


    public Classes(int class_id, String class_name, Date date, Course course, int scedule, int limit_stu, int status) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.date = date;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }


    public Classes(String class_name, Date date, Course course, int scedule, int limit_stu, int status) {
        this.class_name = class_name;
        this.date = date;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }


}
