package sspd.sms.classoptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import sspd.sms.courseoptions.model.Course;

import java.sql.Date;


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


    public Classes(int class_id, String class_name, Date date, Course course, int scedule, int limit_stu, int status) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.date = date;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }


    public Classes() {}

    public Classes(String class_name, Date date, Course course, int scedule, int limit_stu, int status) {
        this.class_name = class_name;
        this.date = date;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }

    public Classes( String class_name, Course course, int scedule, int limit_stu, int status) {
        this.class_name = class_name;
        this.course = course;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
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
