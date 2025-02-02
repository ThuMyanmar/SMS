package sspd.sms.teacheroptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.teacheroptions.model.TeacherSubject;

import java.util.List;
import java.util.Set;

@Repository
public class Tsubimpls implements Taskdao<TeacherSubject> {

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
    public List<TeacherSubject> getAllTask() {
        List<TeacherSubject> list;

        try (Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            list = session.createQuery("FROM TeacherSubject", TeacherSubject.class).getResultList();
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            return List.of();
        }

        return list;
    }

    @Override
    public void insertTask(TeacherSubject task) {


        Set<ConstraintViolation<TeacherSubject>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<TeacherSubject> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(task);
            showInformationDialog("Data Insert Successful","TeacherSubject","TeacherSubject Data Insert Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void updateTask(TeacherSubject task) {

        Set<ConstraintViolation<TeacherSubject>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<TeacherSubject> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error","Data Update Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(task);
            showInformationDialog("Data Update Successful","TeacherSubject","TeacherSubject Data Update Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void deleteTask(TeacherSubject task) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(task);
        //showInformationDialog("Data Delete Successful","TeacherSubject","TeacherSubject Data Delete Successfully!!!");
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void bactchTask(List<TeacherSubject> list) {

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();



        for(TeacherSubject t :list){

            session.persist(t);

        }

        session.flush();
        session.clear();


        tx.commit();
        showInformationDialog("Data Import Successful","TeacherSubject","TeacherSubject Import Successfully!!!");
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
