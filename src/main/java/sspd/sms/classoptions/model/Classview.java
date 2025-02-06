package sspd.sms.classoptions.model;

import jakarta.persistence.Column;

import java.sql.Date;

public class Classview {


    private int class_id;


    private Date date;


    private String class_name;

    private String course_name;

    private int scedule;

    private int limit_stu;

    private String status;

    private int totalteacher;


    public Classview(int class_id, Date date, String class_name, String course_name, int scedule, int limit_stu, String status) {
        this.class_id = class_id;
        this.date = date;
        this.class_name = class_name;
        this.course_name = course_name;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
    }

    public Classview(int class_id, Date date, String class_name, String course_name, int scedule, int limit_stu, String status, int totalteacher) {
        this.class_id = class_id;
        this.date = date;
        this.class_name = class_name;
        this.course_name = course_name;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
        this.status = status;
        this.totalteacher = totalteacher;
    }




    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotalteacher() {
        return totalteacher;
    }

    public void setTotalteacher(int totalteacher) {
        this.totalteacher = totalteacher;
    }

    public Date getDate() {
        return date;
    }


    public void setDate(Date date) {
        this.date = date;
    }

    public int getClass_id() {
        return class_id;
    }

    public void setClass_id(int class_id) {
        this.class_id = class_id;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getCourse_name() {
        return course_name;
    }

    public void setCourse_name(String course_name) {
        this.course_name = course_name;
    }

    public int getScedule() {
        return scedule;
    }

    public void setScedule(int scedule) {
        this.scedule = scedule;
    }

    public int getLimit_stu() {
        return limit_stu;
    }

    public void setLimit_stu(int limit_stu) {
        this.limit_stu = limit_stu;
    }
}
