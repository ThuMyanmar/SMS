package sspd.sms.classoptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.beans.factory.annotation.Autowired;
import sspd.sms.Launch;
import sspd.sms.classoptions.model.ClassHasTeacherDTO;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.Classview;
import sspd.sms.classoptions.services.ClassesService;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.model.CourseDTO;
import sspd.sms.courseoptions.services.CourseService;

import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ResourceBundle;
import java.util.concurrent.atomic.AtomicInteger;

import static sspd.sms.Launch.context;

@org.springframework.stereotype.Controller
public class ClassController implements Initializable {



    @FXML
    private TableView classtable;


    @FXML
    private TableColumn<Classview, String> courseCol;

    @FXML
    private TextField coursetxt;

    @FXML
    private TableColumn<Classview, Integer> limitstudentCol;

    @FXML
    private TextField limitstudenttxt;

    @FXML
    private TableColumn<Classview, String> nameCol;

    @FXML
    private TextField nametxt;

    @FXML
    private Button savebtn;

    @FXML
    private TableColumn<Classview, Integer> sceduleCol;

    @FXML
    private TableColumn<Classview, Date> dateCol;


    @FXML
    private TableColumn<Classview, String> statusCol;

    @FXML
    private TextField seduletxt;

    @FXML
    private Button teacherbtn;

    @FXML
    private Button updatebtn;


    @FXML
    private AnchorPane mainPane;


    @FXML
    private Label countlb;

    @FXML
    private CheckBox notopencheckbox;

    @FXML
    private CheckBox opencheckbox;
    @FXML
    private TableColumn<Classview, Integer> totalTeacher;

    @Autowired
    ClassesService classesService;

    @Autowired
    CourseService cservice ;



    @Override
    public void initialize(URL location, ResourceBundle resources) {

        classtableini();
        getLoadData();

        actionEvent();

        classtable.setEditable(true);

    }

    private void actionEvent() {

        AtomicInteger status = new AtomicInteger();

        mainPane.setOnKeyPressed(event1 -> {

            if(event1.getCode() == KeyCode.F1) {

                try {

                    Stage stage = new Stage();

                    FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/layout/coursetableview.fxml"));
                    fxmlLoader.setControllerFactory(context::getBean);
                    Scene scene = new Scene(fxmlLoader.load());
                    stage.setScene(scene);
                    Stage mainstage = (Stage) savebtn.getScene().getWindow();
                    stage.initOwner(mainstage);
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.setTitle("Course Table");
                    stage.show();

                    stage.setOnCloseRequest(event -> {

                        CourseDTO couserDTO = CourseDTO.getInstance();


                        coursetxt.setText(couserDTO.getCourse().getCourse_name());




                    });







                } catch (IOException e) {
                    e.printStackTrace(); // Log the error for debugging
                }

            }


        });

        notopencheckbox.setOnAction(event -> {


           if(notopencheckbox.isSelected()) {

               status.set(0);


               opencheckbox.setSelected(false);

           }

        });

        opencheckbox.setOnAction(event -> {

            status.set(1);


            if(opencheckbox.isSelected()) {

                notopencheckbox.setSelected(false);

            }

        });

        savebtn.setOnAction(event -> {



           if(!seduletxt.getText().isEmpty() && !limitstudenttxt.getText().isEmpty()){

               try {

               CourseDTO couserDTO = CourseDTO.getInstance();

               String name =  nametxt.getText();
               int sedu = Integer.parseInt(seduletxt.getText());
               int  limitstu = Integer.parseInt(limitstudenttxt.getText());

               Classes classes =new Classes(name, Date.valueOf(LocalDate.now()),couserDTO.getCourse(),sedu,limitstu,status.get());

               classesService.SaveClass(classes);

               getLoadData();

               } catch (NumberFormatException e) {
                   e.printStackTrace();

                   Alert alert = new Alert(Alert.AlertType.ERROR);
                   alert.setTitle("Invalid Input");
                   alert.setHeaderText("Please enter valid numeric values.");
                   alert.setContentText("Sedule and Limitstu must be numbers.");
                   alert.showAndWait();
               }

           }else {

               Alert alert = new Alert(Alert.AlertType.ERROR);
               alert.setTitle("Empty Fields");
               alert.setHeaderText("All fields are required.");
               alert.setContentText("Please fill out both the Duration and Fee fields.");
               alert.showAndWait();
           }

        });

        updatebtn.setOnAction(event -> {


            if(!seduletxt.getText().isEmpty() && !limitstudenttxt.getText().isEmpty()){

                try {



                    CourseDTO couserDTO = CourseDTO.getInstance();

                    String name =  nametxt.getText();
                    int sedu = Integer.parseInt(seduletxt.getText());
                    int  limitstu = Integer.parseInt(limitstudenttxt.getText());

                    int classID = classesService.getClassIdd(name);


                    Classes classes =new Classes(classID,name, Date.valueOf(LocalDate.now()),couserDTO.getCourse(),sedu,limitstu,status.get());

                    classesService.classUpdate(classes);

                    getLoadData();
                    getClear();




                } catch (NumberFormatException e) {
                    e.printStackTrace();

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter valid numeric values.");
                    alert.setContentText("Sedule and Limitstu must be numbers.");
                    alert.showAndWait();
                }

            }else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Fields");
                alert.setHeaderText("All fields are required.");
                alert.setContentText("Please fill out both the Duration and Fee fields.");
                alert.showAndWait();
            }



        });

        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {


            String value = String.valueOf(event.getNewValue());

            if(null!=value && !value.isEmpty()){

                CourseDTO couserDTO = CourseDTO.getInstance();

                Classview classview = (Classview) classtable.getSelectionModel().getSelectedItem();

                int classID = classesService.getClassIdd(classview.getClass_name());

                Classes classes = new Classes(classID,value,classview.getDate(),couserDTO.getCourse(),classview.getScedule(),classview.getLimit_stu(),status.get());

                classesService.classUpdate(classes);

                event.getRowValue().setClass_name(value);

                getLoadData();
                getClear();



            }



        });

        classtable.setOnMouseClicked(event1 -> {


            if(event1.getClickCount()==2){

                Classview classview = (Classview) classtable.getSelectionModel().getSelectedItem();

                nametxt.setText(classview.getClass_name());
                nametxt.setEditable(false);
                coursetxt.setText(classview.getCourse_name());
                limitstudenttxt.setText(String.valueOf(classview.getLimit_stu()));
                seduletxt.setText(String.valueOf(classview.getScedule()));

                Course course  = cservice.getCourseByName(coursetxt.getText());

                CourseDTO courseDTO = CourseDTO.getInstance();
                courseDTO.setCourse(course);


                if(classview.getStatus().equals("Open")){

                    opencheckbox.setSelected(true);
                    notopencheckbox.setSelected(false);


                }
                else{
                    notopencheckbox.setSelected(true);
                    opencheckbox.setSelected(false);
                }

            }else {

                Classview classview = (Classview) classtable.getSelectionModel().getSelectedItem();


                Course course  = cservice.getCourseByName(classview.getCourse_name());

                CourseDTO courseDTO = CourseDTO.getInstance();
                courseDTO.setCourse(course);

            }






        });


        teacherbtn.setOnAction(event -> {

            Classview classview = (Classview) classtable.getSelectionModel().getSelectedItem();


            if(classview==null){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Class Selected");
                alert.setContentText("Please select a class from the table.");
                alert.showAndWait();
                return;
            }

            int classID = classesService.getClassIdd(classview.getClass_name());
            if (classID <= 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Teacher ID Retrieval Failed");
                alert.setContentText("Unable to fetch teacher ID for: " + classview.getClass_name());
                alert.showAndWait();
                return;
            }


            Classes classes = new Classes();
            classes.setClass_id(classID);
            classes.setClass_name(classview.getClass_name());


            Course course = cservice.getCourseByName(classview.getCourse_name());
            classes.setCourse(course);

            ClassHasTeacherDTO classHasTeacherDTO = ClassHasTeacherDTO.getInstance();
            classHasTeacherDTO.setClasses(classes);

            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/classteacherview.fxml"));

            Scene scene = null;

            Stage stage = new Stage();

            try{
                fxmlLoader.setControllerFactory(context::getBean);

                scene = new Scene(fxmlLoader.load());
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.WINDOW_MODAL);
                Stage mainStage = (Stage) classtable.getScene().getWindow();
                stage.initOwner(mainStage);
                stage.setTitle("Teacher Class");
                stage.setScene(scene);
                stage.show();

                stage.setOnCloseRequest(event1 -> {
                    getLoadData();
                });



            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });




    }


    private void getClear(){

        nametxt.setText("");
        limitstudenttxt.setText("");
        coursetxt.setText("");
        seduletxt.setText("");
        opencheckbox.setSelected(false);
        notopencheckbox.setSelected(false);

        CourseDTO c = CourseDTO.getInstance();
        c.setCourse(null);


    }

    private void getLoadData() {


        ObservableList<Classview> list = FXCollections.observableArrayList(classesService.getAllClasses());

        countlb.setText(String.valueOf(classesService.getAllClasses().size()));

        classtable.setItems(list);

    }

    private void classtableini() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("class_name"));
        courseCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        sceduleCol.setCellValueFactory(new PropertyValueFactory<>("scedule"));
        limitstudentCol.setCellValueFactory(new PropertyValueFactory<>("limit_stu"));
        dateCol.setCellValueFactory(new PropertyValueFactory<>("date"));
        statusCol.setCellValueFactory(new PropertyValueFactory<>("status"));
        totalTeacher.setCellValueFactory(new PropertyValueFactory<>("totalteacher"));



    }



}
