package sspd.sms.registeroptions.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import java.sql.Date;

@Getter@Setter
@AllArgsConstructor
@NoArgsConstructor
@Component
public class RegisterView {

    private Date re_date;
    private String stu_id;
    private String stu_name;
    private String class_name;
    private String course_name;


    public RegisterView(String stu_id, String stu_name, String class_name, String course_name) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.class_name = class_name;
        this.course_name = course_name;
    }
}
