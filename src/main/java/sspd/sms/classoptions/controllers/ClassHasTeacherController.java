package sspd.sms.classoptions.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import sspd.sms.classoptions.model.ClassHasTeacherDTO;
import sspd.sms.teacheroptions.model.Teacher;

import java.net.URL;
import java.util.ResourceBundle;

public class ClassHasTeacherController implements Initializable {

    @FXML
    private TableColumn<Teacher, String> DesCol;

    @FXML
    private Button addbtn;

    @FXML
    private TableColumn<Teacher, String> nameCol;

    @FXML
    private Button removebtn;

    @FXML
    private TableView <Teacher> setteachertable;

    @FXML
    private TableColumn<Teacher, String> subCol;

    @FXML
    private Label teachernamelb;

    @FXML
    private Label teachersublb;

    @FXML
    private TableView<Teacher> teachertable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        ClassHasTeacherDTO classHasTeacherDTO = ClassHasTeacherDTO.getInstance();

        teachernamelb.setText(String.valueOf(classHasTeacherDTO.getClasses().getClass_id()));
        
        tableIni();
        actionEvent();
        getLoadData();
        
    }
    @FXML
    private void getLoadData() {
    }

    @FXML
    private void actionEvent() {

        addbtn.setOnAction(event -> {

        });
    }

    @FXML
    private void tableIni() {


        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subCol.setCellValueFactory(new PropertyValueFactory<>("name"));




    }
}
