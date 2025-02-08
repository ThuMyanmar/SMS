package sspd.sms.registeroptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.model.Classview;
import sspd.sms.classoptions.services.ClassesService;

import java.net.URL;
import java.util.ResourceBundle;

@Controller

public class RegisterChooseController implements Initializable {

    @FXML
    private Button addStudent;

    @FXML
    private TableView<Classview> classtable;

    @FXML
    private TableColumn<Classview, String> courseCol;

    @FXML
    private TableColumn<Classview, String> nameCol;

    @FXML
    private TableColumn<Classview, Integer> limitstudentCol;


    @FXML
    private TextField searchboxtxt;


    private ClassesService classesService;


    public RegisterChooseController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        tableIni();
        getLoadData();

    }

    @FXML
    private void getLoadData() {

        ObservableList<Classview> data = FXCollections.observableArrayList(classesService.getClassviews());

        classtable.setItems(data);


    }

    @FXML
    private void tableIni() {
        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));




    }
}
