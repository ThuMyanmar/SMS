package sspd.sms.studentoptions.model.register;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.stereotype.Component;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.studentoptions.model.dropout.DropoutStudent;

import java.sql.Date;
import java.util.Set;

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
    @NotNull(message = " Date of Birth required")
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

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Register> registerSet;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<StudentSchedule> studentScheduleSet;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private DropoutStudent dropoutStudent;




    public Student(String stu_id, String stu_name, Date stu_dob, String gender, String contact, String email, String address, String photo_path) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_dob = stu_dob;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.photo_path = photo_path;
    }


    public Student(String stu_id, String stu_name, Date stu_dob, String gender, String contact, String email, String address) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_dob = stu_dob;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

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
