package sspd.sms.teacheroptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.teacheroptions.model.Teacher;

import java.util.List;
import java.util.Set;


@Repository
public class Timpls implements Taskdao<Teacher> {

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
    public List<Teacher> getAllTask() {

        Session session = sessionFactory.openSession();

        List<Teacher>tlist = session.createQuery("FROM Teacher  t ORDER BY t.id DESC").list();

        session.close();

        return tlist;
    }
    @Override
    public void insertTask(Teacher task) {

        Set<ConstraintViolation<Teacher>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Teacher> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.persist(task);
            showInformationDialog("Data Insert Successful","Teacher","Teacher Data Insert Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }



    }

    @Override
    public void updateTask(Teacher task) {

        Set<ConstraintViolation<Teacher>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Teacher> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error","Data Update Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(task);
            showInformationDialog("Data Update Successful","Teacher","Teacher Data Update Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void deleteTask(Teacher task) {



            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.remove(task);
            showInformationDialog("Data Delete Successful","Teacher","Teacher Data Delete Successfully!!!");
            session.getTransaction().commit();
            session.close();




    }

    @Override
    public void bactchTask(List<Teacher> list) {

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();



        for(Teacher t :list){

           session.persist(t);

        }

        session.flush();
        session.clear();


        tx.commit();
        showInformationDialog("Data Import Successful","Teacher","Teacher Import Successfully!!!");
        session.close();


    }

    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void showInformationDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.initModality(Modality.APPLICATION_MODAL);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });

    }
}
