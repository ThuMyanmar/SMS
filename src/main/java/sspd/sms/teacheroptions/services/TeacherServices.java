package sspd.sms.teacheroptions.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import sspd.sms.config.AppConfig;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.services.Services;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.db.Tsubimpls;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherSubject;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TeacherServices {




    private ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    private Timpls tdb = context.getBean(Timpls.class );

    private Tsubimpls ts = context.getBean(Tsubimpls.class );

    private final Services courseServices = new Services();

    public List<TeacherSubject> getTeacherSubjects() {

        return ts.getAllTask();


    }

    public int getTeacherId(String teacherName){

       return tdb.getAllTask().stream()
                .filter(teacher -> teacher.getName().equals(teacherName))
                .map(Teacher::getTeacher_id)
                .findFirst()
                .orElse(-1);

    }

    public List<Course> getSubjectNames(int teacherId) {

        System.out.println("T:"+teacherId);
        System.out.println(getTeacherSubjects().size());

        return getTeacherSubjects().stream()
                .filter(teacherSubject -> teacherSubject.getTeacher().getTeacher_id()==(teacherId))
                .map(TeacherSubject::getCourse)
                .collect(Collectors.toList());
    }

    public List<Course> getCourseList(int teacherId) {
        List<Course> courseNames = courseServices.getAllCourses();
        List<Course> teacherCourseNames = getSubjectNames(teacherId);

        if (teacherCourseNames.isEmpty()) {
            return courseNames;
        }

        return courseNames.stream()
                .filter(cname -> teacherCourseNames.stream()
                        .noneMatch(ctname -> ctname.getCourse_name().equals(cname.getCourse_name())))
                .collect(Collectors.toList());
    }

    public void saveTeacherSubjects(TeacherSubject teacherSubject) {

        int oldSize = tdb.getAllTask().size();

        ts.insertTask(teacherSubject);

        int newSize = tdb.getAllTask().size();

        if (oldSize != newSize) {

            showInformationDialog("Data Import Successful","TeacherSubject","TeacherSubject Import Successfully!!!");

        }

    }

    public void removeTeacherSubjects(TeacherSubject teacherSubject) {

        teacherSubject.setTsid(getTeacherSubjectID(teacherSubject.getTeacher().getTeacher_id(),teacherSubject.getCourse().getCourse_id()));

        int oldSize = getTeacherSubjects().size();

        ts.deleteTask(teacherSubject);

        int newSize = getTeacherSubjects().size();

        if (oldSize != newSize) {

            showInformationDialog("Data Delete Successful","TeacherSubject","TeacherSubject delete Successfully!!!");

        }



    }

    public int getTeacherSubjectID(int teacherId,int courseId) {

      return  getTeacherSubjects().stream()
              .filter(teacherSubject -> teacherSubject.getTeacher().getTeacher_id()==teacherId && teacherSubject.getCourse().getCourse_id()==courseId)
              .map(TeacherSubject::getTsid)
              .findFirst()
              .orElse(-1);

    }

    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void showInformationDialog(String title, String header, String content) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }


}
