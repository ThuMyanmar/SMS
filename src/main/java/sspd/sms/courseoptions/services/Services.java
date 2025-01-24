package sspd.sms.courseoptions.services;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import sspd.sms.config.AppConfig;
import sspd.sms.courseoptions.db.Courseimpl;
import sspd.sms.courseoptions.model.Course;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Component
public class Services {

    private ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

    private Courseimpl cdb = context.getBean(Courseimpl.class);

    public List<Course> getAllCourses() {

            return cdb.getAllTask();
    }

    public void insertCourse(Course course) {

        int oldSize = getAllCourses().size();

        cdb.insertTask(course);

        int newSize = getAllCourses().size();

        if(oldSize != newSize) {

            showInformationDialog("Data Insert Successful","Course","Course Data Insert Successfully!!!");

        }



    }

    public void updateCourse(Course course) {

           cdb.updateTask(course);
    }

    public void deleteCourse(Course course) {

        int oldSize = getAllCourses().size();

        cdb.deleteTask(course);

        int newSize = getAllCourses().size();

        if(oldSize != newSize) {

            showInformationDialog("Data Delete Successful","Course","Teacher Data Delete Successfully!!!");

        }




    }

    public void importCourse(List<Course>list) {


        int oldSize = getAllCourses().size();

        cdb.bactchTask(list);

        int newSize = getAllCourses().size();

        if(oldSize != newSize) {

            showInformationDialog("Data Import Successful","Course","Teacher Import Successfully!!!");

        }



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

    public int getCoursesCount() {

        return getAllCourses().size();
    }

    public int findCourseByName(String name) {

        return getAllCourses().stream()
                .filter(course -> course.getCourse_name().equals(name))
                .map(Course::getCourse_id)
                .findFirst()
                .orElse(-1);


    }

    public List<String>getCourseNames() {

       return getAllCourses().stream()
                .map(Course::getCourse_name).sorted().collect(Collectors.toList());


    }




}
