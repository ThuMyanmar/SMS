package sspd.sms.teacheroptions.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.springframework.stereotype.Component;

@Entity
@Table(name="teacher")
@Component
public class Teacher {

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        private int teacher_id;

        @NotBlank
        @Column(name="name" ,nullable = true,length = 100)
        private String name;
        @Column(name="qualification",length = 200)
        private String qualification;

        @Pattern(
            regexp = "^09[0-9]{7,9}$",
            message = "Phone number must start with 09 and have 9 to 11 digits"
        )
        @Size(min = 5, max = 11)
        @Column(name="contact",length = 100)
        private String contact;

    @Email(
            regexp = "^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$",
            message = "Invalid email format"
    )
    @Pattern(
            regexp = "^[A-Za-z0-9._%+-]+@(gmail\\.com|yahoo\\.com|outlook\\.com)$",
            message = "Only Gmail, Yahoo, or Outlook addresses are allowed"
    )
        @NotBlank(message = "Email required")
        @Column(name = "email",length = 100)
        private String email;

    @Column(name = "address",length = 255)
    private String address;

    @Column(name="photo",length = 255)
    private String photo;



    public Teacher(){}

    public Teacher(int teacher_id, String name, String qualification, String contact, String email, String address, String photo) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.qualification = qualification;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.photo = photo;
    }

    public Teacher(String name, String qualification, String contact, String email, String address, String photo) {
        this.name = name;
        this.qualification = qualification;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.photo = photo;
    }

    public Teacher(String name, String qualification, String contact, String email, String address) {
        this.name = name;
        this.qualification = qualification;
        this.contact = contact;
        this.email = email;
        this.address = address;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getQualification() {
        return qualification;
    }

    public void setQualification(String qualification) {
        this.qualification = qualification;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
