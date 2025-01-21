package sspd.sms.teacheroptions.db;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import sspd.sms.DAO.Taskdao;
import sspd.sms.teacheroptions.model.Teacher;

import java.util.List;
import java.util.Set;

@Component
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

        List<Teacher>tlist = session.createQuery("FROM Teacher t ORDER BY t.id DESC").list();

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
            session.getTransaction().commit();
            session.close();

        }



    }

    @Override
    public void updateTask(Teacher task) {

    }

    @Override
    public void deleteTask(Teacher task) {

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
}
