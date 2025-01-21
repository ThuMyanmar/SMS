package sspd.sms.courseoptions.module;

public class Course {
    int course_id;
    String course_name;
    String description;
    double duration;
    int fee;

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
