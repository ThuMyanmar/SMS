package sspd.sms.classoptions.module;

public class Classes {

    int class_id;
    String class_name;
    int course_id;
    int teacher_id;
    String scedule;
    int limit_stu;

    public Classes(String class_name, int course_id, int teacher_id, String scedule, int limit_stu) {
        this.class_name = class_name;
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        this.scedule = scedule;
        this.limit_stu = limit_stu;
    }

    public Classes(int class_id, String class_name, int course_id, int teacher_id, String scedule, int limit_stu) {
        this.class_id = class_id;
        this.class_name = class_name;
        this.course_id = course_id;
        this.teacher_id = teacher_id;
        this.scedule = scedule;
        this.limit_stu = limit_stu;



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

    public int getCourse_id() {
        return course_id;
    }

    public void setCourse_id(int course_id) {
        this.course_id = course_id;
    }

    public int getTeacher_id() {
        return teacher_id;
    }

    public void setTeacher_id(int teacher_id) {
        this.teacher_id = teacher_id;
    }

    public String getScedule() {
        return scedule;
    }

    public void setScedule(String scedule) {
        this.scedule = scedule;
    }

    public int getLimit_stu() {
        return limit_stu;
    }

    public void setLimit_stu(int limit_stu) {
        this.limit_stu = limit_stu;
    }
}
