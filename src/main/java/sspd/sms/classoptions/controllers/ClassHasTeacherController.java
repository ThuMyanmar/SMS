package sspd.sms.classoptions.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.model.ClassHasTeacherDTO;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.ClasshasTeacher;
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




            ObservableList<Teacher>avaliableTeachers = FXCollections.observableArrayList();
            ObservableList<Teacher>assignedTeacher = FXCollections.observableArrayList();

            assignedTeacher.clear();
            avaliableTeachers.clear();

            avaliableTeachers.addAll(classHasTeacherService.getAvailableTeachers(classHasTeacherDTO.getClasses().getClass_id()));

            assignedTeacher.addAll(classHasTeacherService.getAssignedTeachers(classHasTeacherDTO.getClasses().getClass_id()));

            teachertable.setItems(avaliableTeachers);
            setteachertable.setItems(assignedTeacher);


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


        Teacher teacher = teachertable.getSelectionModel().getSelectedItem();
        if (teacher == null) {
            showErrorAlert("No Teacher Selected", "Please select a teacher from the table");
            return;
        }



        int teacherId = teacherServices.getTeacherId(teacher.getName());
        teacher.setTeacher_id(teacherId);

        Classes classes  = new Classes();
        classes.setClass_id(classHasTeacherDTO.getClasses().getClass_id());


        ClasshasTeacher association = new ClasshasTeacher();
        association.setClasses(classes);
        association.setTeacher(teacher);

        classHasTeacherService.insertTask(association);

        getLoadData();

        showInformationAlert("Teacher Added", "Successfully added teacher");




    }

    private Teacher getSelectedTeacherForDelete() {


        return setteachertable.getSelectionModel().getSelectedItem();


    }

    private boolean validateTeacherSelection(Teacher teacher) {
        if (teacher == null) {
            showErrorAlert("No Teacher Selected", "Please select a teacher from the table");
            return false;
        }
        return true;
    }

    private void handleRemoveTeacher() {

        Teacher teacher = getSelectedTeacherForDelete();
        if (!validateTeacherSelection(teacher)) {
            return;
        }

        ClasshasTeacher association = createClasshasTeacherAssociation(teacher);
        classHasTeacherService.deleteTask(association);

        getLoadData();
        showInformationAlert("Teacher Removed", "Successfully removed teacher");
    }

    private ClasshasTeacher createClasshasTeacherAssociation(Teacher teacher) {

        int teacherId = getTeacherId(teacher);

        teacher.setTeacher_id(teacherId);

        Classes classes = new Classes();
        classes.setClass_id(classHasTeacherDTO.getClasses().getClass_id());

        ClasshasTeacher association = new ClasshasTeacher();
        association.setClasses(classes);
        association.setTeacher(teacher);

        int id = classHasTeacherService.getTeacherhasClassID(classes.getClass_id(),teacher.getTeacher_id());

        association.setTeacher_classid(id);

        return association;
    }

    private int getTeacherId(Teacher teacher) {

        return teacherServices.getTeacherId(teacher.getName());

    }

    @FXML
    private void tableIni() {


        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        subCol.setCellValueFactory(new PropertyValueFactory<>("name"));




    }

    private void showErrorAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }private void showInformationAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
