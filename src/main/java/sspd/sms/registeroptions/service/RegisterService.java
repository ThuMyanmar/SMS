package sspd.sms.registeroptions.service;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Service;
import sspd.sms.DAO.Taskdao;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.db.Registerimpl;

import java.util.List;
import java.util.Set;

@Service
public class RegisterService implements Taskdao<Register> {

    private final Validator validator;
    private final Registerimpl registerimpl;

    public RegisterService(Validator validator, Registerimpl registerimpl) {
        this.validator = validator;
        this.registerimpl = registerimpl;
    }


    @Override
    public List<Register> getAllTask() {
        return registerimpl.getAllTask();
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
