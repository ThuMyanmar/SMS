package sspd.sms.registeroptions.service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.stereotype.Service;
import sspd.sms.DAO.Taskdao;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.db.Registerimpl;
import sspd.sms.registeroptions.model.RegisterView;
import sspd.sms.studentoptions.model.Student;
import sspd.sms.studentoptions.service.StudentService;

import java.util.List;
import java.util.Set;

@Service
public class RegisterService implements Taskdao<Register> {

    private final Validator validator;
    private final Registerimpl registerimpl;
    private final StudentService studentService;
    private final SessionFactory sessionFactory;

    public RegisterService(Validator validator, Registerimpl registerimpl, StudentService studentService, SessionFactory sessionFactory) {
        this.validator = validator;
        this.registerimpl = registerimpl;
        this.studentService = studentService;
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<Register> getAllTask() {
        return registerimpl.getAllTask();
    }

    public List<RegisterView>getAllRegisterView(){

        return registerimpl.getRegisterView();

    }

    @Override
    public void insertTask(Register task) {

        Set<ConstraintViolation<Register>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Register> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error", errorMessages +"\n"+name);

        }
        else {

            registerimpl.insertTask(task);
            showInformationDialog("Success", "Insertion Completed", "Register record inserted successfully.");


        }

    }

    public void insertTask(Register task,Session session) {

        Set<ConstraintViolation<Register>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Register> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error", errorMessages +"\n"+name);

        }
        else {

            session.persist(task);





        }


    }

    @Override
    public void updateTask(Register task) {

        Set<ConstraintViolation<Register>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Register> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            registerimpl.updateTask(task);
            showInformationDialog("Success", "Update Completed", "Register record updated successfully.");


        }

    }

    @Override
    public void deleteTask(Register task) {

        Set<ConstraintViolation<Register>> violations = validator.validate(task);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<Register> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }
            showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);

        }
        else {

            registerimpl.deleteTask(task);
            showInformationDialog("Success", "Deletion Completed", "Register record deleted successfully.");



        }

    }

    @Override
    public void bactchTask(List<Register> list) {

    }

    public void insertTaskStudentAndRegister(Student student, Register register) {
        Transaction transaction = null;
        Session session = null;

        try {
            session = sessionFactory.openSession();
            transaction = session.beginTransaction();

            // Student ကို session ထဲသို့ insert လုပ်ခြင်း
            studentService.insertTask(student, session);

            // Register ကို session ထဲသို့ insert လုပ်ခြင်း
            insertTask(register, session);

            // Information dialog အတိုင်ပင်ခံ
            showInformationDialog("Success", "Register Completed", "Register record inserted successfully.");

            // Transaction commit
            transaction.commit();
        } catch (Exception e) {
            // Error ဖြစ်ရင် rollback
            if (transaction != null) transaction.rollback();
            e.printStackTrace();
            // Error Dialog ထုတ်ပြောခြင်း
            showErrorDialog("Error", "Data Insertion Error", "Register record insertion failed.");
        } finally {
            // session ပိတ်ဖို့လို
            if (session != null && session.isOpen()) {
                session.close();
            }
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

}
