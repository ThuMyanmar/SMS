package sspd.sms.courseoptions.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherSubject;

import java.util.Set;

@Entity
@Table(name = "course")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int course_id;

    @NotBlank(message = "Requierd Course Name.....")
    @Size(min = 8,max = 80)
    @Column(name = "course_name",length = 80)
    private String course_name;

    @Column(name = "description")
    private String description;

    @Positive(message = "Duration must be a positive value.")
    @Column(name = "duration")

    private double duration;

    @PositiveOrZero(message = "Fee must be zero or a positive value.")
    @Column(name = "fee")
    private int fee;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private Set<TeacherSubject> teacherSubjects;

    public Course() {}

    public Course(String course_name, String description, double duration, int fee) {
        this.course_name = course_name;
        this.description = description;
        this.duration = duration;
        this.fee = fee;
    }

    public Course(int course_id, String course_name, String description, double duration, int fee) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.description = description;
        this.duration = duration;
        this.fee = fee;

    }

    public Course(int course_id, String course_name, String description) {
        this.course_id = course_id;
        this.course_name = course_name;
        this.description = description;
    }

    public Set<TeacherSubject> getTeacherSubjects() {
        return teacherSubjects;
    }

    public void setTeacherSubjects(Set<TeacherSubject> teacherSubjects) {
        this.teacherSubjects = teacherSubjects;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getDuration() {
        return duration;
    }

    public void setDuration(double duration) {
        this.duration = duration;
    }

    public int getFee() {
        return fee;
    }

    public void setFee(int fee) {
        this.fee = fee;
    }
}
