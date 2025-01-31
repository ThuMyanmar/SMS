package sspd.sms.classoptions.model;

public class ClassHasTeacherDTO {

    private static volatile ClassHasTeacherDTO instance;
    private Classes classes;

    private ClassHasTeacherDTO() {}

    public static ClassHasTeacherDTO getInstance() {
        if (instance == null) {
            synchronized (ClassHasTeacherDTO.class) {
                if (instance == null) {
                    instance = new ClassHasTeacherDTO();
                }
            }
        }
        return instance;
    }


    public Classes getClasses() {
        return classes;
    }

    public void setClasses(Classes classes) {
        this.classes = classes;
    }
}
