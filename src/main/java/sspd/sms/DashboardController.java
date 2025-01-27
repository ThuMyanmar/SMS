package sspd.sms;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Polygon;
import javafx.stage.Stage;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sspd.sms.config.AppConfig;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {

    @FXML
    private Polygon classNotic;

    @FXML
    private Polygon courseNotic;

    @FXML
    private JFXButton coursebtn;

    @FXML
    private JFXButton teacherbn;

    @FXML
    private JFXButton classbtn;

    @FXML
    private JFXButton stubtn;


    @FXML
    private Polygon stuNotic;

    @FXML
    private Polygon teacherNotic;

    @FXML
    private AnchorPane setPand;





    @Override
    public void initialize(URL location, ResourceBundle resources) {



       // polygonColodefault();
        eventActionClick();

    }

    private void eventActionClick() {

        stubtn.setOnAction(event -> {

            stuNotic.setStyle("-fx-fill: #F5F5F5;-fx-stroke:#F5F5F5");
            classNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");
            teacherNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");
            courseNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");




        });

        teacherbn.setOnAction(event -> {


            teacherNotic.setStyle("-fx-fill:#F5F5F5;-fx-stroke:#F5F5F5");
            classNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");
            stuNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");
            courseNotic.setStyle("-fx-fill: #1E88E5;-fx-stroke:#1E88E5");


            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/teacherview.fxml"));


            try {


                setPand.getChildren().clear();
                setPand.getChildren().add(fxmlLoader.load());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }



        });

        coursebtn.setOnAction(event -> {


            courseNotic.setStyle("-fx-fill: #F5F5F5; -fx-stroke:#F5F5F5");
            classNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
            stuNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
            teacherNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/courseview.fxml"));


            try {


                setPand.getChildren().clear();
                setPand.getChildren().add(fxmlLoader.load());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }




        });

        classbtn.setOnAction(event -> {

            classNotic.setStyle("-fx-fill:  #F5F5F5;");
            stuNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
            teacherNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
            courseNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/classview.fxml"));


            try {


                setPand.getChildren().clear();
                setPand.getChildren().add(fxmlLoader.load());


            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        });

    }

    private void polygonColodefault() {


        classNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
        stuNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
        teacherNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");
        courseNotic.setStyle("-fx-fill: #1E88E5; -fx-stroke:#1E88E5");




    }
}
