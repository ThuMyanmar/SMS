package sspd.sms.teacheroptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Controller;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.services.Services;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherDTO;
import sspd.sms.teacheroptions.model.TeacherSubject;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class TeacherSubjectController implements Initializable {

    @FXML
    private TableColumn<Course, String> DesCol;

    @FXML
    private Button addbtn;

    @FXML
    private TableView coursetable;

    @FXML
    private TableColumn<Course, String> nameCol;

    @FXML
    private Button removebtn;

    @FXML
    private TableColumn<Course, String> subCol;

    @FXML
    private TableView subjecttable;

    @FXML
    private Label teachernamelb;

    @FXML
    private Label teachersublb;

    private final TeacherDTO dto = TeacherDTO.getInstance();
    private final Teacher teacher = dto.getTeacher();



    private TeacherServices teacherServices = new TeacherServices();

    private  Services courseServices = new Services();

    @Override
    public void initialize(URL location, ResourceBundle resources) {


        getLoad();
        coursetableIni();
        teacherSubjectableIni();
        actionEvent();

    }

    private void actionEvent() {


        addbtn.setOnAction(event -> {

            try {

            Course course = (Course) coursetable.getSelectionModel().getSelectedItem();
            course.setCourse_id(courseServices.findCourseByName(course.getCourse_name()));

            TeacherSubject teacherSubject = new TeacherSubject(teacher, course);

            teacherServices.saveTeacherSubjects(teacherSubject);



            getLoad();


            }catch (NullPointerException e){


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Table Noticed");
                alert.setContentText("Your want to subject could not be found.");
                alert.showAndWait();

            }


        });

        removebtn.setOnAction(event -> {


            try {

                Course course = (Course) subjecttable.getSelectionModel().getSelectedItem();
                course.setCourse_id(courseServices.findCourseByName(course.getCourse_name()));

                TeacherSubject teacherSubject = new TeacherSubject(teacher, course);


                teacherServices.removeTeacherSubjects(teacherSubject);

                getLoad();

            }catch (NullPointerException e){


                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Table Noticed");
                alert.setContentText("Your subject could not be found.");
                alert.showAndWait();

            }



        });




    }

    private void coursetableIni() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
    }

    private void teacherSubjectableIni() {

        subCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
    }

    private void getLoad() {


        teachernamelb.setText(teacher.getName());

        ObservableList<Course> tlist = FXCollections.observableArrayList( teacherServices.getSubjectNames(teacher.getTeacher_id()));

        System.out.println(tlist.size());

        ObservableList<Course> clist = FXCollections.observableArrayList(teacherServices.getCourseList(teacher.getTeacher_id()));

        subjecttable.setItems(tlist);
        coursetable.setItems(clist);
        teachersublb.setText(String.valueOf(tlist.size()));




    }
}
