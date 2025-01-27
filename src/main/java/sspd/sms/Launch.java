package sspd.sms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import sspd.sms.errorHandler.GlobalExceptionHandler;

import java.io.IOException;

public class Launch extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/dashboard.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        launch();
    }
}