package sspd.sms.errorHandler;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.dao.DataAccessException;

public class GlobalExceptionHandler implements Thread.UncaughtExceptionHandler {

    @Override
    public void uncaughtException(Thread t, Throwable e) {
        if (e instanceof org.hibernate.exception.ConstraintViolationException) {
            showErrorDialog("Database Error", ((ConstraintViolationException) e).getSQL(), "The email already exists. Please use a different email.");
        } else if (e instanceof DataAccessException) {
            showErrorDialog("Global Error", "An error occurred", e.getMessage());
        } else {
            e.printStackTrace();
        }
    }

    public static class DataAccessException extends RuntimeException {
        public DataAccessException(String message, Throwable cause) {
            super(message, cause);
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


}
