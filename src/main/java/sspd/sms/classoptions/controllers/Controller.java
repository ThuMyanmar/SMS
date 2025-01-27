package sspd.sms.classoptions.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.services.ClassesService;

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
    private TableColumn<Classes, Integer> seduleCol;

    @FXML
    private TextField seduletxt;

    @FXML
    private Button teacherbtn;

    @FXML
    private Button updatebtn;


    ClassesService classesService = new ClassesService();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        classtableini();
        getLoadData();

        actionEvent();

    }

    private void actionEvent() {

        savebtn.setOnAction(event -> {

           String name =  nametxt.getText();
           int courseId = Integer.parseInt(coursetxt.getText());
            int sedu = Integer.parseInt(seduletxt.getText());
           int  limitstu = Integer.parseInt(limitstudenttxt.getText());

          // Classes classes = new Classes(name,course,sedu,limitstu);

        });


    }

    private void getLoadData() {

       List<Classes> clist =  classesService.getAllClasses();
        classtable.getItems().setAll(clist);

    }

    private void classtableini() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course"));
        seduleCol.setCellValueFactory(new PropertyValueFactory<>("scedule"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));



    }



}
