package sspd.sms.teacheroptions.model;

import jakarta.persistence.*;
import org.springframework.stereotype.Component;
import sspd.sms.courseoptions.model.Course;

@Component
@Entity
@Table(name = "teacher_subjects")
public class TeacherSubject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int tsid;

    @ManyToOne()
    @JoinColumn(name="teacher_id", nullable = false)
    private Teacher teacher;

    @ManyToOne()
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    public TeacherSubject(){}

    public TeacherSubject(int tsid, Teacher teacher, Course course) {
        this.tsid = tsid;
        this.teacher = teacher;
        this.course = course;
    }

    public TeacherSubject(Teacher teacher, Course course) {
        this.teacher = teacher;
        this.course = course;
    }



    public int getTsid() {
        return tsid;
    }

    public void setTsid(int tsid) {
        this.tsid = tsid;
    }

    public Teacher getTeacher() {
        return teacher;
    }

    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
