package sspd.sms.classoptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.model.ClassHasTeacherDTO;
import sspd.sms.classoptions.services.ClassHasTeacherService;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
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


    private TeacherServices teacherServices;

    private ClassHasTeacherService classHasTeacherService;

    @Autowired
    public void setTeacherServices(TeacherServices teacherServices) {
        this.teacherServices = teacherServices;
    }

    @Autowired
    public void setClassHasTeacherService(ClassHasTeacherService classHasTeacherService) {
        this.classHasTeacherService = classHasTeacherService;
    }

    private final ClassHasTeacherDTO classHasTeacherDTO = ClassHasTeacherDTO.getInstance();

    @Override
    public void initialize(URL location, ResourceBundle resources) {




        teachernamelb.setText(String.valueOf(classHasTeacherDTO.getClasses().getClass_name()));

        tableIni();
        actionEvent();
        getLoadData();
        
    }
    @FXML
    private void getLoadData() {

        ObservableList<Teacher> teachers = FXCollections.observableArrayList(classHasTeacherService.getFilteredTeachers(classHasTeacherDTO.getClasses().getCourse().getCourse_id()));

        teachernamelb.setText(String.valueOf(classHasTeacherDTO.getClasses().getClass_name()));

        teachertable.setItems(teachers);
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
