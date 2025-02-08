package sspd.sms.registeroptions.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import sspd.sms.Launch;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class RegisterViewController implements Initializable {

    @FXML
    private Button addsturegisterbtn;



    @Override
    public void initialize(URL location, ResourceBundle resources) {



        actionEvent();

    }

    private void actionEvent() {

        addsturegisterbtn();
    }

    @FXML
    private void addsturegisterbtn() {

        addsturegisterbtn.setOnAction(event -> {


        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/registerchooseclassview.fxml"));
            Scene scene = new Scene(fxmlLoader.load());


            Stage stage = new Stage();
            stage.initModality(Modality.WINDOW_MODAL);


            Stage mainStage = (Stage) addsturegisterbtn.getScene().getWindow();
            stage.initOwner(mainStage); // Set owner to main stage


            stage.setTitle("အတန်းရွေးချယ်ခြင်း");
            stage.setScene(scene);
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
        }
        });
    }





}
