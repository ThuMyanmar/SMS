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

}
