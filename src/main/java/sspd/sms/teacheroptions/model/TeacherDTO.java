package sspd.sms.teacheroptions.model;

public class TeacherDTO {
    private static volatile TeacherDTO instance;
    private Teacher teacher;




    private TeacherDTO() {}




    public static TeacherDTO getInstance() {
        if (instance == null) {
            synchronized (TeacherDTO.class) { // Thread-safe Singleton
                if (instance == null) {
                    instance = new TeacherDTO();
                }
            }
        }
        return instance;
    }

    // Getter for teacher
    public Teacher getTeacher() {
        return teacher;
    }

    // Setter for teacher
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}


