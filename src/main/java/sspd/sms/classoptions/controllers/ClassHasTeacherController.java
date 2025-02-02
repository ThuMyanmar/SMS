package sspd.sms.classoptions.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.model.ClassHasTeacherDTO;
import sspd.sms.classoptions.services.ClassHasTeacherService;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.net.URL;
import java.util.ResourceBundle;


/**
 *
 * ClassHasTeacher
 *
 *
 */

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



    private final TeacherServices teacherServices;

    private final ClassHasTeacherService classHasTeacherService;


    private final ClassHasTeacherDTO classHasTeacherDTO = ClassHasTeacherDTO.getInstance();


    public ClassHasTeacherController(TeacherServices teacherServices, ClassHasTeacherService classHasTeacherService) {
        this.teacherServices = teacherServices;
        this.classHasTeacherService = classHasTeacherService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        Platform.runLater(() -> {
            teachernamelb.setText(classHasTeacherDTO.getClasses().getClass_name());
        });

        tableIni();
        actionEvent();
        getLoadData();
        
    }
    @FXML
    private void getLoadData() {
        try {
            int courseId = classHasTeacherDTO.getClasses().getCourse().getCourse_id();
            ObservableList<Teacher> teachers = FXCollections.observableArrayList(
                    classHasTeacherService.getFilteredTeachers(courseId)
            );
            teachertable.setItems(teachers);
        } catch (NullPointerException e) {
            showErrorAlert("Data not found", "Course or class information is missing");
        } catch (Exception e) {
            showErrorAlert("Loading Error", "Failed to load teachers: " + e.getMessage());
        }
    }


    @FXML
    private void actionEvent() {
        addbtn.setOnAction(event -> handleAddTeacher());
        removebtn.setOnAction(event -> handleRemoveTeacher()); // removebtn ကို အသုံးပြုပါ
    }

    private void handleAddTeacher() {

    }

    private void handleRemoveTeacher() {

    }

    @FXML
    private void tableIni() {


        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        subCol.setCellValueFactory(new PropertyValueFactory<>("name"));




    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
