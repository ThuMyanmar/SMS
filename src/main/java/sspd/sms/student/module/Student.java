package sspd.sms.student.module;

import org.w3c.dom.Text;

import java.util.Date;

public class Student {

    private String stu_id;
    private String stu_name;
    private Date stu_dob;
    private String gender;
    private String contact;
    private String email;
    private String address;
    private String photo_path;

    public Student(String stu_id, String stu_name, java.sql.Date stu_dob,String gender, String contact, String email, String address, String photo_path) {
        this.stu_id = stu_id;
        this.stu_name = stu_name;
        this.stu_dob = stu_dob;
        this.gender = gender;
        this.contact = contact;
        this.email = email;
        this.address = address;
        this.photo_path = photo_path;
    }


    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public String getStu_name() {
        return stu_name;
    }

    public void setStu_name(String stu_name) {
        this.stu_name = stu_name;
    }

    public Date getStu_dob() {
        return stu_dob;
    }

    public void setStu_dob(Date stu_dob) {
        this.stu_dob = stu_dob;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhoto_path() {
        return photo_path;
    }

    public void setPhoto_path(String photo_path) {
        this.photo_path = photo_path;
    }
}
