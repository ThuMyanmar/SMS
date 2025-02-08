package sspd.sms.studentoptions;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.sql.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "student")
@Component
public class Student {

    @Id
    private String stu_id;

    @Column(name = "stu_name",length = 100)
    @NotBlank(message = "name required")
    private String stu_name;

    @Column(name = "stu_dob")
    @NotBlank(message = " Date of Birth required")
    private Date stu_dob;

    @Column(name = "gender")
    @NotBlank(message = "gender required")
    private String gender;

    @Column(name = "contact")
    @NotBlank
    @Pattern(
            regexp = "^09[0-9]{7,9}$",
            message = "Phone number must start with 09 and have 9 to 11 digits"
    )
    @Size(min = 5, max = 11)
    private String contact;

    @Column(name = "email")
    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format"
    )
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com)$",
            message = "Only Gmail, Yahoo, or Outlook addresses are allowed"
    )
    @NotBlank(message = "Email required")
    private String email;

    @Column(name = "address")
    @NotBlank(message = "Address required")
    private String address;

    @Column(name = "photo_path")
    private String photo_path;


    public Student(String stu_name, Date stu_dob, String gender, String contact, String email, String address, String photo_path) {
        this.stu_name = stu_name;
        this.stu_dob = stu_dob;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.photo_path = photo_path;
    }


}
