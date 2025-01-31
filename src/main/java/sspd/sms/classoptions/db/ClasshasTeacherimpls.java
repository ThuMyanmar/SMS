package sspd.sms.classoptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.ClasshasTeacher;
import java.util.List;
import java.util.Set;

@Repository
public class ClasshasTeacherimpls implements Taskdao<ClasshasTeacher> {


    private Validator validator;

    private SessionFactory sessionFactory;

    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }
    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<ClasshasTeacher> getAllTask() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<ClasshasTeacher> list =session.createQuery("from ClasshasTeacher").list();
        session.getTransaction().commit();
        session.close();

        return list;
    }

    @Override
    public void insertTask(ClasshasTeacher task) {

        Set<ConstraintViolation<ClasshasTeacher>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<ClasshasTeacher> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(task);
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void updateTask(ClasshasTeacher task) {

        Set<ConstraintViolation<ClasshasTeacher>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<ClasshasTeacher> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error", "Data Update Error", errorMessages.toString() + "\n" + name);

        } else {


            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(task);
            showInformationDialog("Data Update Successful", "ClasshasTeacher", "ClasshasTeacher Data Update Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void deleteTask(ClasshasTeacher task) {

        Set<ConstraintViolation<ClasshasTeacher>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<ClasshasTeacher> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error", "Data Update Error", errorMessages.toString() + "\n" + name);

        } else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(task);
            session.getTransaction().commit();
            session.close();
        }

    }

    @Override
    public void bactchTask(List<ClasshasTeacher> list) {

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
