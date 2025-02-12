package sspd.sms.courseoptions.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.model.CourseDTO;
import sspd.sms.courseoptions.services.CourseService;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

@Controller
public class TableviewController implements Initializable {

    @FXML
    private TableView coursetable;

    @FXML
    private TableColumn<Course, Integer> courseidCol;

    @FXML
    private TableColumn<Course, String> coursenameCol;

    @FXML
    private TableColumn<Course, String> descriptionCol;

    @FXML
    private TableColumn<Course, Double> durationCol;

    @FXML
    private TableColumn<Course, Integer> feeCol;

    //Services services = new Services();

    @Autowired
    CourseService courseService;

    @Override
    public void initialize(URL location, ResourceBundle resources) {





        Coursetbini();
        getLoadData();


        coursetable.setOnMouseClicked(event -> {


            if(event.getClickCount() == 2) {


                Stage stage = (Stage) coursetable.getScene().getWindow();

               Course course = (Course) coursetable.getSelectionModel().getSelectedItem();
               CourseDTO dto = CourseDTO.getInstance();
               dto.setCourse(course);



            }


        });

    }

    public void getLoadData(){

        List<Course> clist = courseService.getAllCourses();
        coursetable.getItems().setAll(clist);

    }

    public void Coursetbini(){

        courseidCol.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        coursenameCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));


    }
}


