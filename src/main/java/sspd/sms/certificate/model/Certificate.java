package sspd.sms.certificate.model;


import java.util.Date;

public class Certificate {

    String cer_id;
    String stu_id;
    Date issue_date;
    int ctid;

    public Certificate(String stu_id, Date issue_date, int ctid) {
        this.stu_id = stu_id;
        this.issue_date = issue_date;
        this.ctid = ctid;
    }

    public Certificate(String cer_id, String stu_id, Date issue_date, int ctid) {
        this.cer_id = cer_id;
        this.stu_id = stu_id;
        this.issue_date = issue_date;
        this.ctid = ctid;

    }

    public String getCer_id() {
        return cer_id;
    }

    public void setCer_id(String cer_id) {
        this.cer_id = cer_id;
    }

    public String getStu_id() {
        return stu_id;
    }

    public void setStu_id(String stu_id) {
        this.stu_id = stu_id;
    }

    public Date getIssue_date() {
        return issue_date;
    }

    public java.sql.Date setIssue_date() {
        this.issue_date = issue_date;
        return new java.sql.Date(this.issue_date.getTime());
    }

    public int getCtid() {
        return ctid;
    }

    public void setCtid(int ctid) {
        this.ctid = ctid;
    }
}
