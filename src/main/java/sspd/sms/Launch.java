package sspd.sms;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import sspd.sms.config.SpringContextHelper;
import sspd.sms.courseoptions.db.Courseimpl;
import sspd.sms.errorHandler.GlobalExceptionHandler;

import java.io.IOException;

public class Launch extends Application {

    public static ApplicationContext context = SpringContextHelper.getContext();
    @Override

    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/dashboard.fxml"));
        fxmlLoader.setControllerFactory(context::getBean);
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }

    @Override
    public void init() throws Exception {
        context = SpringContextHelper.getContext();
    }




    public static void main(String[] args) {

        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());
        launch();
    }
    public static  ApplicationContext getContext() {

        return context;
    }
}