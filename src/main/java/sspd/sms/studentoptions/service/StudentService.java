package sspd.sms.studentoptions.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import sspd.sms.DAO.Taskdao;
import sspd.sms.studentoptions.db.Studentimpl;
import sspd.sms.studentoptions.model.Student;

import java.util.List;
import java.util.Set;

@Service
public class StudentService implements Taskdao<Student> {

    private final SessionFactory sessionFactory;

    private final Validator validator;

    private final Studentimpl studentimpl;


    public StudentService(SessionFactory sessionFactory, Validator validator, Studentimpl studentimpl) {
        this.sessionFactory = sessionFactory;
        this.validator = validator;
        this.studentimpl = studentimpl;
    }

    @Override
    public List<Student> getAllTask() {
        return studentimpl.getAllTask();
    }

    @Override
    public void insertTask(Student task) {

    }

    public void insertTask(Student task, Session session) {

        Set<ConstraintViolation<Student>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Student> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

           studentimpl.insertTask(task, session);

        }



    }


    @Override
    public void updateTask(Student task) {

        Set<ConstraintViolation<Student>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Student> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            studentimpl.updateTask(task);
            showInformationDialog("Success", "Update Completed", "Student record updated successfully.");


        }


    }

    @Override
    public void deleteTask(Student task) {

        Set<ConstraintViolation<Student>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Student> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            studentimpl.deleteTask(task);
            showInformationDialog("Success", "Deletion Completed", "Student record deleted successfully.");



        }


    }

    @Override
    public void bactchTask(List<Student> list) {

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
