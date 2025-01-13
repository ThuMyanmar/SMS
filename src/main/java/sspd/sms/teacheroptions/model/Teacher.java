package sspd.sms.teacheroptions.model;

public class Teacher {

        private int teacher_id;
        private String name;
        private String qualification;
        private String contact;
        private String email;

    public Teacher(int teacher_id, String name, String qualification, String contact, String email) {
        this.teacher_id = teacher_id;
        this.name = name;
        this.qualification = qualification;
        this.contact = contact;
        this.email = email;
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
