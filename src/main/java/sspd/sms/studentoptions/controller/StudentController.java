package sspd.sms.studentoptions.controller;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.DTO.ClassesDTO;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.services.ClassesService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.service.RegisterService;
import sspd.sms.studentoptions.model.Student;
import sspd.sms.studentoptions.service.StudentIDGenerate;
import sspd.sms.studentoptions.service.StudentService;

@Controller
public class StudentController implements Initializable {

    @FXML
    private TextArea stuaddresstxt;

    @FXML
    private DatePicker studof;

    @FXML
    private TextField stuemailtxt;

    @FXML
    private ComboBox<String> stugender;

    @FXML
    private Label stuidtxt;

    @FXML
    private ImageView stuimage;

    @FXML
    private TextField stunametxt;

    @FXML
    private TextField stuphonetxt;

    @FXML
    private Button stusavebtn;

    @FXML
    private Button uploadimgbtn;

    private ClassesService classesService;

    private StudentService studentService;

    private StudentIDGenerate generator;

    private RegisterService registerService;

    public StudentController(ClassesService classesService, StudentService studentService, StudentIDGenerate generator, RegisterService registerService) {
        this.classesService = classesService;
        this.studentService = studentService;
        this.generator = generator;
        this.registerService = registerService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {



       getLoadData();
       actionEvent();

    }
    @FXML
    private void actionEvent() {

        addStudent();

        setGender(stugender);

        uploadimgbtn.setOnAction(event -> {

            FileChooser fileChooser = new FileChooser();


            FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                    "Image Files", "*.png", "*.jpg", "*.jpeg"
            );
            fileChooser.getExtensionFilters().add(imageFilter);


            fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));


            File selectedFile = fileChooser.showOpenDialog(uploadimgbtn.getScene().getWindow());

            if (selectedFile != null) {
                try {

                    Image image = new Image(selectedFile.toURI().toString());
                    stuimage.setImage(image);



                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else {

            }


        });

    }
    @FXML
    private void getLoadData() {

        stuidtxt.setText(generator.getStudentIDGenerate());

    }

    private void addStudent() {

        stusavebtn.setOnAction(event -> {

            try {

                String name = stunametxt.getText();

                Date dob = Date.valueOf(studof.getValue());

                String gender = stugender.getValue();

                String email = stuaddresstxt.getText();

                String phone = stunametxt.getText();

                String address  = stuaddresstxt.getText();

                String orgimgPath = stuimage.getImage().getUrl();

                File sourceFile = new File(orgimgPath.replace("file", ""));

                File destinationFolder = new File("D:/backImage/StudentImage");

                if (!destinationFolder.exists()) {
                    destinationFolder.mkdir();
                }

                File destinationFile = new File(destinationFolder, name + ".jpg");

                Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String photoPath = destinationFile.getAbsolutePath();

                Student student = new Student(generator.getStudentIDGenerate(),name, dob,gender,phone, email,address, photoPath);

                Classes classes = new Classes();
                classes.setClass_id(classesService.getClassesDTO().getClass_id());

                Register register = new Register(classes,student);

                registerService.insertTaskStudentAndRegister(student,register);

                showInformationDialog("Success", "Register Completed", "Register record inserted successfully.");
                Stage stage = (Stage) stusavebtn.getScene().getWindow();
                getResetDataClassDTO();
                stage.close();

            }catch (IOException | NullPointerException | ConstraintViolationException e){

                showInformationDialog( "Error", "Failed to save the image.","Failded to Save the image or Insert Image");
            }




        });

    }

    private void getResetDataClassDTO(){

        classesService.setClassesDTO(null);

    }

    private void setGender(ComboBox<String> gender) {

        ObservableList<String> options = FXCollections.observableArrayList("Male", "Female");
        gender.setItems(options);

    }
    private void showErrorDialog(String title, String header, String content) {
        Platform.runLater(() -> {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    private void showInformationDialog(String title, String header, String content) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }





}