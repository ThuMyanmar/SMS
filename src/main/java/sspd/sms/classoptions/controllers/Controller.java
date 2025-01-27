package sspd.sms.classoptions.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.services.ClassesService;
import sspd.sms.courseoptions.model.CourseDTO;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {



    @FXML
    private TableView classtable;


    @FXML
    private TableColumn<Classes, Integer> courseCol;

    @FXML
    private TextField coursetxt;

    @FXML
    private TableColumn<Classes, Integer> limitstudentCol;

    @FXML
    private TextField limitstudenttxt;

    @FXML
    private TableColumn<Classes, String> nameCol;

    @FXML
    private TextField nametxt;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<Classes, Integer> sceduleCol;

    @FXML
    private TextField seduletxt;

    @FXML
    private Button teacherbtn;

    @FXML
    private Button updatebtn;


    @FXML
    private AnchorPane mainPane;


    ClassesService classesService = new ClassesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        classtableini();
        getLoadData();

        actionEvent();

    }

    private void actionEvent() {

        mainPane.setOnKeyPressed(event1 -> {

            if(event1.getCode() == KeyCode.F1) {

                try {

                    Stage stage = new Stage();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/coursetableview.fxml"));
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    Stage mainstage = (Stage) savebtn.getScene().getWindow();
                    stage.initOwner(mainstage);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Course Table");
                    stage.show();

                    stage.setOnCloseRequest(event -> {

                        CourseDTO couserDTO = CourseDTO.getInstance();


                        coursetxt.setText(couserDTO.getCourse().getCourse_name());


                    });







                } catch (IOException e) {
                    e.printStackTrace(); // Log the error for debugging
                }

            }


        });

        savebtn.setOnAction(event -> {



           if(!seduletxt.getText().isEmpty() && !limitstudenttxt.getText().isEmpty()){

               try {

               CourseDTO couserDTO = CourseDTO.getInstance();

                   System.out.println(couserDTO.getCourse().getCourse_id());


//               String name =  nametxt.getText();
//               int sedu = Integer.parseInt(seduletxt.getText());
//               int  limitstu = Integer.parseInt(limitstudenttxt.getText());
//
//               Classes classes =new Classes(name,couserDTO.getCourse(),sedu,limitstu);
//
//               classesService.SaveClass(classes);

               } catch (NumberFormatException e) {
                   e.printStackTrace();

                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Invalid Input");
                   alert.setHeaderText("Please enter valid numeric values.");
                   alert.setContentText("Sedule and Limitstu must be numbers.");
                   alert.showAndWait();
               }




           }else {

               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Empty Fields");
               alert.setHeaderText("All fields are required.");
               alert.setContentText("Please fill out both the Duration and Fee fields.");
               alert.showAndWait();
           }






        });




    }

    private void getLoadData() {

       List<Classes> clist =  classesService.getAllClasses();
        classtable.getItems().setAll(clist);

    }

    private void classtableini() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        sceduleCol.setCellValueFactory(new PropertyValueFactory<>("scedule"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));



    }



}
