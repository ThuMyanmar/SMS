package sspd.sms.registeroptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import org.springframework.stereotype.Controller;
import sspd.sms.Launch;
import sspd.sms.classoptions.services.ClassesService;
import sspd.sms.courseoptions.services.CourseService;
import sspd.sms.registeroptions.model.RegisterView;
import sspd.sms.registeroptions.service.RegisterService;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.util.ResourceBundle;

import static sspd.sms.Launch.context;

@Controller
public class RegisterViewController implements Initializable {

    @FXML
    private Button addclassbtn;

    @FXML
    private Button addcoursebtn;

    @FXML
    private Button addsturegisterbtn;

    @FXML
    private Button addtutorbtn;

    @FXML
    private TableView<RegisterView> registertable;

    @FXML
    private TableColumn<RegisterView, Date> dateCol;

    @FXML
    private TableColumn<RegisterView, String> idCol;

    @FXML
    private TableColumn<RegisterView, String> nameCol;

    @FXML
    private TableColumn<RegisterView, String> classCol;

    @FXML
    private TableColumn<RegisterView, String> courseCol;

    @FXML
    private Label classcountlb;

    @FXML
    private Label countlb;

    @FXML
    private Label coursecountlb;

    @FXML
    private TextField searchboxtxt;

    @FXML
    private Label stucountlb;

    @FXML
    private Label tutorcountlb;


    private RegisterService registerService;

    private TeacherServices teacherServices;

    private CourseService courseService;

    private ClassesService classesService;


    public RegisterViewController(ClassesService classesService, CourseService courseService, TeacherServices teacherServices, RegisterService registerService) {
        this.classesService = classesService;
        this.courseService = courseService;
        this.teacherServices = teacherServices;
        this.registerService = registerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        iniTable();
        actionEvent();
        getLoadTableDate();

    }

    private void getLoadTableDate() {

        ObservableList<RegisterView> data = FXCollections.observableArrayList(registerService.getAllRegisterView());
        registertable.setItems(data);
        countlb.setText(String.valueOf(data.size()));
        stucountlb.setText(String.valueOf(data.size()));
        tutorcountlb.setText(String.valueOf(teacherServices.getTeachers().size()));
        coursecountlb.setText(String.valueOf(courseService.getAllCourses().size()));
        classcountlb.setText(String.valueOf(classesService.getAllClasses().size()));
    }

    private void iniTable() {

        dateCol.setCellValueFactory(new PropertyValueFactory<>("re_date"));
        idCol.setCellValueFactory(new PropertyValueFactory<>("stu_id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("stu_name"));
        classCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));


    }



    private void actionEvent() {

        addsturegisterbtn();
    }

    @FXML
    private void addsturegisterbtn() {

        addsturegisterbtn.setOnAction(event -> {


        try {

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/registerchooseclassview.fxml"));
            fxmlLoader.setControllerFactory(context::getBean);
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
