package sspd.sms.courseoptions.model;

public class CourseDTO {

    private static volatile CourseDTO instance;
    private Course course;


    private CourseDTO() {}


    public static CourseDTO getInstance() {


        if (instance == null) {
            synchronized (CourseDTO.class) {
                if (instance == null) {
                    instance = new CourseDTO();
                }
            }
        }
        return instance;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }
}
