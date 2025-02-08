package sspd.sms.classoptions.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Setter
@Getter
@Component
public class Classview {


    private int class_id;


    private Date date;


    private String class_name;

    private String course_name;

    private int scedule;

    private int limit_stu;

    private String status;

    private int totalteacher;

    private  int registered_student;


    public Classview(int class_id, Date date, String class_name, String course_name, int scedule, int limit_stu, String status) {
        this.class_id = class_id;
        this.date = date;
        this.class_name = class_name;
        this.course_name = course_name;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }

    public Classview() {}

    public Classview(int class_id, Date date, String class_name, String course_name, int scedule, int limit_stu, String status, int totalteacher) {
        this.class_id = class_id;
        this.date = date;
        this.class_name = class_name;
        this.course_name = course_name;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
        this.totalteacher = totalteacher;
    }


}
