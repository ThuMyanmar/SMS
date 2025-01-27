package sspd.sms.classoptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import sspd.sms.courseoptions.model.Course;



@Table(name="classes")
@Entity
public class Classes {

   @Id
   @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int class_id;

   @NotBlank
   @Column(name="class_name",length = 100)
    private String class_name;



    @OneToOne
    @JoinColumn(name = "course_course_id")
    private Course course;


    @Column(name="scedule")
    private int scedule;


    @Column(name="limit_stu")
    private int limit_stu;
    public Classes(int class_id, String class_name, Course course, int scedule, int limit_stu) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;

    }
    public Classes() {}

    public Classes(String class_name, Course course, int scedule, int limit_stu) {
        this.class_name = class_name;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public int getScedule() {
        return scedule;
    }

    public void setScedule(int scedule) {
        this.scedule = scedule;
    }

    public int getLimit_stu() {
        return limit_stu;
    }

    public void setLimit_stu(int limit_stu) {
        this.limit_stu = limit_stu;
    }
}
