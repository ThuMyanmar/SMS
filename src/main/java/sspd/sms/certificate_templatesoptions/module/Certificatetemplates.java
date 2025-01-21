package sspd.sms.certificate_templatesoptions.module;

public class Certificatetemplates {

        int ctid;
        int course_id;
        String template;

    public Certificatetemplates(int ctid, int course_id, String template) {
        this.ctid = ctid;
        this.course_id = course_id;
        this.template = template;
    }

    public Certificatetemplates(int course_id, String template) {

        this.course_id = course_id;
        this.template = template;

    }

    public int getCtid() {
        return ctid;
    }

    public void setCtid(int ctid) {
        this.ctid = ctid;
    }

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public String getTemplate() {
        return template;
    }

    public void setTemplate(String template) {
        this.template = template;
    }
}
