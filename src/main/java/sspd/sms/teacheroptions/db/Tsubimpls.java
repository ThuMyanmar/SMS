package sspd.sms.teacheroptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sspd.sms.DAO.Taskdao;
import sspd.sms.errorHandler.DataAccessException;
import sspd.sms.teacheroptions.model.TeacherSubject;

import java.util.List;
import java.util.Set;

@Repository
public class Tsubimpls implements Taskdao<TeacherSubject> {

    private final Validator validator;
    private final SessionFactory sessionFactory;
    private static final Logger logger = LoggerFactory.getLogger(Tsubimpls.class);

    @Autowired
    public Tsubimpls(Validator validator, SessionFactory sessionFactory) {
        this.validator = validator;
        this.sessionFactory = sessionFactory;
    }


    @Override
    public List<TeacherSubject> getAllTask() {
        try (Session session = sessionFactory.openSession()) {
            return session.createQuery("FROM TeacherSubject", TeacherSubject.class).list();
        } catch (Exception e) {
            logger.error("Error fetching TeacherSubjects", e);
            throw new DataAccessException("Failed to retrieve data", e);
        }
    }

    @Override
    public void insertTask(TeacherSubject task) {


            try (Session session = sessionFactory.openSession()) {
                Transaction tx = session.beginTransaction();

                try {
                    session.persist(task);
                    showInformationDialog("Data Insert Successful", "TeacherSubject", "TeacherSubject Data Insert Successfully!!!");
                    tx.commit();
                }catch (Exception e) {
                    tx.rollback();
                    showErrorDialog("Database Error", "Data Insertion Error", "Failed to insert data: " + e.getMessage());
                }
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
        session.getTransaction().commit();
        session.close();

    }

    @Override
    public void bactchTask(List<TeacherSubject> list) {

        try (Session session = sessionFactory.openSession()) {
            Transaction tx = session.beginTransaction();
            try {
                for (int i = 0; i < list.size(); i++) {
                    session.persist(list.get(i));
                    if (i % 50 == 0) { // Hibernate batch size နှင့်ကိုက်ညီပါ
                        session.flush();
                        session.clear();
                    }
                }
                tx.commit();
            } catch (Exception e) {
                tx.rollback();
                throw new DataAccessException("Batch insert failed", e);
            }
        }

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
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }



}
