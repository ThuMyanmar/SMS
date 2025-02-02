package sspd.sms.teacheroptions.model;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Getter
@Component
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


    // Setter for teacher
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }
}


