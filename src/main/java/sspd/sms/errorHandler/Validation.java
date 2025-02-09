package sspd.sms.errorHandler;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
public class Validation<T> {



    private Validator validator;

    public Validation(Validator validator) {
        this.validator = validator;
    }

    public boolean testValidator(T t) {

        Set<ConstraintViolation<T>> violations = validator.validate(t);

        String name = null;

        if (!violations.isEmpty()) {
            StringBuilder errorMessages = new StringBuilder();
            for (ConstraintViolation<T> violation : violations) {
                errorMessages.append(violation.getMessage()).append("\n");
                name = String.valueOf(violation.getPropertyPath());
            }


                showErrorDialog("Database Error","Data Insertion Error",errorMessages.toString()+"\n"+name);
            }
        else {

            return true;

        }
        return false;

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
