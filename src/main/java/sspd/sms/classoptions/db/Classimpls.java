package sspd.sms.classoptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.model.Classes;

import java.util.List;
import java.util.Set;

@Component
public class Classimpls implements Taskdao<Classes> {


    private SessionFactory sessionFactory;
    private Validator validator;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    @Autowired
    public void setValidator(Validator validator) {
        this.validator = validator;
    }

    @Override
    public List<Classes> getAllTask() {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        List<Classes> list = session.createQuery("from Classes").list();
        session.getTransaction().commit();
        session.close();


        return list;
    }

    @Override
    public void insertTask(Classes task) {

        Set<ConstraintViolation<Classes>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Classes> violation : violations) {
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
    public void updateTask(Classes task) {

        Set<ConstraintViolation<Classes>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Classes> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error", "Data Update Error", errorMessages.toString() + "\n" + name);

        } else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(task);
            showInformationDialog("Data Update Successful", "Course", "Course Data Update Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void deleteTask(Classes task) {
        Set<ConstraintViolation<Classes>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Classes> violation : violations) {
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
    public void bactchTask(List<Classes> list) {

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();



        for(Classes t :list){

            session.persist(t);

        }

        session.flush();
        session.clear();


        tx.commit();

        session.close();


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
