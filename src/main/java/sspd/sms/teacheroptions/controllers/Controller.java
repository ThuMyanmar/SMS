package sspd.sms.teacheroptions.controllers;

import jakarta.validation.ConstraintViolation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.KeyCode;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.dao.DataAccessException;
import sspd.sms.errorHandler.GlobalExceptionHandler;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.model.Teacher;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@org.springframework.stereotype.Controller
public class Controller implements Initializable {

    @FXML
    private TableColumn<Teacher, String> contactCol;

    @FXML
    private TableColumn<Teacher, String> emailCol;

    @FXML
    private TableColumn<Teacher, String> nameCol;

    @FXML
    private Button printbtn;

    @FXML
    private TableColumn<Teacher, String> qualificationCol;

    @FXML
    private Button savebtn;

    @FXML
    private Label teachercontactlb;

    @FXML
    private TextField teachercontacttxt;

    @FXML
    private Label teacheremaillb;

    @FXML
    private TextField teacheremailtxt;

    @FXML
    private Label teachernamelb;

    @FXML
    private TextField teachernametxt;

    @FXML
    private Label teacherqualificationlb;

    @FXML
    private TextArea teacherqualificationtxt;

    @FXML
    private Button teachersubjectbtn;

    @FXML
    private TableView teachertable;
    @FXML
    private Label suggestnamelb;

    @FXML
    private Label suggestqualb;




    @FXML
    private Label totaltlb;

    private ApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");

    private Timpls tdb = context.getBean(Timpls.class    );





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getLoadData();
        actionEvent();
        tableIni();
        initializeSearch();

        teachertable.setEditable(true);

        rowUpdate();





    }

    private void tableIni() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        qualificationCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));

    }

    private void actionEvent() {



        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());

        savebtn.setOnAction(event -> {

                String name = teachernametxt.getText();
                String quali = teacherqualificationtxt.getText();
                String contact = teachercontacttxt.getText();
                String email = teacheremailtxt.getText();

                Teacher teacher = new Teacher(name, quali, contact, email);



                tdb.insertTask(teacher);
                getLoadData();
                setTextnull();




        });





    }

    private  void rowUpdate(){


        nameCol.setCellFactory(TextFieldTableCell.forTableColumn());
        nameCol.setOnEditCommit(event -> {


            String value = String.valueOf(event.getNewValue());

            if(null!=value && !value.isEmpty()){

                Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();

                event.getRowValue().setName(value);

                teacher.setName(value);

                tdb.updateTask(teacher);
                getLoadData();


            }



        });

        qualificationCol.setCellFactory(TextFieldTableCell.forTableColumn());
        qualificationCol.setOnEditCommit(event -> {


            String value = String.valueOf(event.getNewValue());

            if(null!=value && !value.isEmpty()){

                Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();

                event.getRowValue().setQualification(value);

                teacher.setQualification(value);

                tdb.updateTask(teacher);
                getLoadData();


            }



        });

        contactCol.setCellFactory(TextFieldTableCell.forTableColumn());
        contactCol.setOnEditCommit(event -> {


            String value = String.valueOf(event.getNewValue());

            if(null!=value && !value.isEmpty()){

                Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();

                event.getRowValue().setContact(value);

                teacher.setContact(value);

                tdb.updateTask(teacher);

                getLoadData();




            }



        });

        emailCol.setCellFactory(TextFieldTableCell.forTableColumn());
        emailCol.setOnEditCommit(event -> {


            String value = String.valueOf(event.getNewValue());

            if(null!=value && !value.isEmpty()){

                Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();

                event.getRowValue().setEmail(value);

                teacher.setEmail(value);

                tdb.updateTask(teacher);


                getLoadData();




            }



        });




    }

    private void setTextnull(){

        teachernametxt.setText("");
        teacherqualificationtxt.setText("");
        teachercontacttxt.setText("");
        teacheremailtxt.setText("");
        suggestnamelb.setText("");
        suggestqualb.setText("");

    }

    private void showErrorDialog(String title, String header, String content) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public void initializeSearch() {




        teachernametxt.textProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue.isEmpty()) {


                suggestnamelb.setText("");
            } else {

                ObservableList<String> suggestions = getNameList()
                        .filtered(name -> name.toLowerCase().contains(newValue.toLowerCase()));


                if (!suggestions.isEmpty()) {

                    String suggestionText = String.join(", ", suggestions.subList(0, Math.min(suggestions.size(), 3)));
                    suggestnamelb.setText("Suggested: " + suggestionText);


                    teachernametxt.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.TAB && !suggestions.isEmpty()) {

                            teachernametxt.setText(suggestions.get(0));
                            suggestnamelb.setText(""); // Clear the label after selecting
                        }
                    });
                } else {

                    suggestnamelb.setText("No suggestions found");
                }
            }
        });
        teacherqualificationtxt.textProperty().addListener((observable, oldValue, newValue) -> {


            if (newValue.isEmpty()) {


                suggestqualb.setText("");
            } else {
                // Filter the list of names based on the input
                ObservableList<String> suggestions = getQuaList()
                        .filtered(name -> name.toLowerCase().contains(newValue.toLowerCase()));


                if (!suggestions.isEmpty()) {

                    String suggestionText = String.join(", ", suggestions.subList(0, Math.min(suggestions.size(), 3)));
                    suggestqualb.setText("Suggested: " + suggestionText);


                    teacherqualificationtxt.setOnKeyPressed(event -> {
                        if (event.getCode() == KeyCode.TAB && !suggestions.isEmpty()) {

                            teacherqualificationtxt.setText(suggestions.get(0));
                            suggestqualb.setText(""); // Clear the label after selecting
                        }
                    });
                } else {

                    suggestqualb.setText("No suggestions found");
                }
            }
        });


    }

    public void getLoadData() {

        ObservableList<Teacher> data = FXCollections.observableArrayList(tdb.getAllTask());

        totaltlb.setText(String.valueOf(data.size()));

        teachertable.setItems(data);

    }

    public ObservableList<String> getNameList() {
        return FXCollections.observableArrayList(
                tdb.getAllTask().stream()
                        .map(Teacher::getName)
                        .collect(Collectors.toList())
        );
    }

    public ObservableList<String> getQuaList() {
        return FXCollections.observableArrayList(
                tdb.getAllTask().stream()
                        .map(Teacher::getQualification)
                        .collect(Collectors.toList())
        );
    }






}
