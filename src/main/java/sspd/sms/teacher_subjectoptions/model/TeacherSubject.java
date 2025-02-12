package sspd.sms.teacher_subjectoptions.model;

public class TeacherSubject {

    private int tsid;
    private int teacher_id;
    private String subject;




    public TeacherSubject(int teacher_id, String subject) {
        this.teacher_id = teacher_id;
        this.subject = subject;
    }

    public TeacherSubject(int tsid, int teacher_id, String subject) {
        this.tsid = tsid;
        this.teacher_id = teacher_id;
        this.subject = subject;



    }

    public int getTsid() {
        return tsid;
    }

    public void setTsid(int tsid) {
        this.tsid = tsid;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }
}
