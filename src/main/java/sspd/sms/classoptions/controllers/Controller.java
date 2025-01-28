package sspd.sms.classoptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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
import sspd.sms.classoptions.model.Classview;
import sspd.sms.classoptions.services.ClassesService;
import sspd.sms.courseoptions.model.CourseDTO;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

public class Controller implements Initializable {



    @FXML
    private TableView classtable;


    @FXML
    private TableColumn<Classview, String> courseCol;

    @FXML
    private TextField coursetxt;

    @FXML
    private TableColumn<Classview, Integer> limitstudentCol;

    @FXML
    private TextField limitstudenttxt;

    @FXML
    private TableColumn<Classview, String> nameCol;

    @FXML
    private TextField nametxt;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<Classview, Integer> sceduleCol;

    @FXML
    private TableColumn<Classview, Date> dateCol;


    @FXML
    private TableColumn<Classview, String> statusCol;

    @FXML
    private TextField seduletxt;

    @FXML
    private Button teacherbtn;

    @FXML
    private Button updatebtn;


    @FXML
    private AnchorPane mainPane;


    @FXML
    private Label countlb;

    @FXML
    private CheckBox notopencheckbox;

    @FXML
    private CheckBox opencheckbox;
    @FXML
    private TableColumn<Classview, Integer> totalTeacher;



    ClassesService classesService = new ClassesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        classtableini();
        getLoadData();

        actionEvent();

    }

    private void actionEvent() {

        AtomicInteger status = new AtomicInteger();

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

        notopencheckbox.setOnAction(event -> {


           if(notopencheckbox.isSelected()) {


               opencheckbox.setSelected(false);

           }

        });

        opencheckbox.setOnAction(event -> {

            status.set(1);


            if(opencheckbox.isSelected()) {

                notopencheckbox.setSelected(false);

            }

        });

        savebtn.setOnAction(event -> {



           if(!seduletxt.getText().isEmpty() && !limitstudenttxt.getText().isEmpty()){

               try {

               CourseDTO couserDTO = CourseDTO.getInstance();

               String name =  nametxt.getText();
               int sedu = Integer.parseInt(seduletxt.getText());
               int  limitstu = Integer.parseInt(limitstudenttxt.getText());

               Classes classes =new Classes(name, Date.valueOf(LocalDate.now()),couserDTO.getCourse(),sedu,limitstu,status.get());

               classesService.SaveClass(classes);

               getLoadData();

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


        ObservableList<Classview> list = FXCollections.observableArrayList(classesService.getAllClasses());

        countlb.setText(String.valueOf(classesService.countClasses()));

        classtable.setItems(list);

    }

    private void classtableini() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        sceduleCol.setCellValueFactory(new PropertyValueFactory<>("scedule"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalTeacher.setCellValueFactory(new PropertyValueFactory<>("totalteacher"));



    }



}
