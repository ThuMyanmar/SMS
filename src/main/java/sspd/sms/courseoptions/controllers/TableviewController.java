package sspd.sms.courseoptions.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import org.hibernate.validator.internal.engine.messageinterpolation.InterpolationTerm;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.services.Services;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class TableviewController implements Initializable {

    @FXML
    private TableView Coursetb;

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

    Services services = new Services();

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Coursetbini();
        getLoadData();

    }

    public void getLoadData(){

        List<Course> clist = services.getAllCourses();
        Coursetb.getItems().setAll(clist);

    }

    public void Coursetbini(){

        courseidCol.setCellValueFactory(new PropertyValueFactory<>("course_id"));
        coursenameCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        descriptionCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));


    }
}


