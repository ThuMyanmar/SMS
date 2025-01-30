package sspd.sms.courseoptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import sspd.sms.DAO.Taskdao;
import sspd.sms.courseoptions.model.Course;

import java.util.List;
import java.util.Set;

@Repository
public class Courseimpl implements Taskdao<Course> {


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
    public List<Course> getAllTask() {

        Session session = sessionFactory.openSession() ;
        session.beginTransaction();
        return session.createQuery("from Course c order by c.id desc ").list();

    }

    @Override
    public void insertTask(Course task) {

        Set<ConstraintViolation<Course>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Course> violation : violations) {
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
    public void updateTask(Course task) {

        Set<ConstraintViolation<Course>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Course> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }

            showErrorDialog("Database Error","Data Update Error",errorMessages.toString()+"\n"+name);

        }
        else {

            Session session = sessionFactory.openSession();
            session.beginTransaction();
            session.merge(task);
            showInformationDialog("Data Update Successful","Course","Course Data Update Successfully!!!");
            session.getTransaction().commit();
            session.close();


        }

    }

    @Override
    public void deleteTask(Course task) {

        Session session = sessionFactory.openSession();
        session.beginTransaction();
        session.remove(task);

        session.getTransaction().commit();
        session.close();

    }
    @Override
    public void bactchTask(List<Course> list) {

        Session session = sessionFactory.openSession();

        Transaction tx = session.beginTransaction();



        for(Course t :list){

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
