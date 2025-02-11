package sspd.sms.registeroptions.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import sspd.sms.Launch;
import sspd.sms.classoptions.DTO.ClassesDTO;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.Classview;
import sspd.sms.classoptions.services.ClassesService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static sspd.sms.Launch.context;

@Controller

public class RegisterChooseController implements Initializable {

    @FXML
    private Button addStudent;

    @FXML
    private TableView<Classview> classtable;

    @FXML
    private TableColumn<Classview, String> courseCol;

    @FXML
    private TableColumn<Classview, String> nameCol;

    @FXML
    private TableColumn<Classview, Integer> limitstudentCol;


    @FXML
    private TableColumn<Classview, Integer> reg_stuCol;


    @FXML
    private TextField searchboxtxt;


    private ClassesService classesService;


    public RegisterChooseController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableIni();
        getLoadData();

        actionEvent();

    }
    @FXML
    private void actionEvent() {

        tableClickAction();












    }

    private void getResetDataClassDTO(){

        classesService.setClassesDTO(null);

    }

    private void tableClickAction(){

        classtable.setOnMouseClicked(event -> {

            if(event.getClickCount() == 2){

                Classview classview= classtable.getSelectionModel().getSelectedItem();

                ClassesDTO classesDTO = new ClassesDTO();


                classesDTO.setClass_id(classesService.getClassIdd(classview.getClass_name()));

                classesService.setClassesDTO(classesDTO);

                if(classesService.getClassesDTO()!=null){

                    try {

                        FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/registerstudentview.fxml"));
                        fxmlLoader.setControllerFactory(context::getBean);
                        Scene scene = new Scene(fxmlLoader.load());


                        Stage stage = new Stage();
                        stage.initModality(Modality.WINDOW_MODAL);


                        Stage mainStage = (Stage) addStudent.getScene().getWindow();
                        stage.initOwner(mainStage);


                        stage.setTitle("သင်တန်းအပ်ခြင်း");
                        stage.setScene(scene);
                        stage.show();

                        stage.setOnCloseRequest(event1 -> {

                            getLoadData();
                            getResetDataClassDTO();

                        });


                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                }

                else {

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    showErrorDialog("Class","Class အမှား","Class ရွေးချယ်ပါ။");
                }



            }

        });





    }

    @FXML
    private void getLoadData() {

        ObservableList<Classview> data = FXCollections.observableArrayList(classesService.getClassviews());

        classtable.setItems(data);


    }

    @FXML
    private void tableIni() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));
        reg_stuCol.setCellValueFactory(new PropertyValueFactory<>("registered_student"));




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
