package sspd.sms.studentoptions.controller.register;

import com.jfoenix.controls.JFXCheckBox;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.stage.FileChooser;
import jfxtras.scene.control.LocalTimeTextField;
import org.hibernate.exception.ConstraintViolationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.services.ClassesService;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;
import java.util.*;

import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import sspd.sms.errorHandler.Validation;
import sspd.sms.registeroptions.model.Register;
import sspd.sms.registeroptions.service.RegisterService;
import sspd.sms.studentoptions.model.register.Student;
import sspd.sms.studentoptions.model.register.StudentSchedule;
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

    @FXML
    private JFXCheckBox day1;

    @FXML
    private JFXCheckBox day2;

    @FXML
    private JFXCheckBox day3;

    @FXML
    private JFXCheckBox day4;

    @FXML
    private JFXCheckBox day5;

    @FXML
    private JFXCheckBox day6;

    @FXML
    private JFXCheckBox day7;

    @FXML
    private LocalTimeTextField endTime;

    @FXML
    private LocalTimeTextField startTime;



    private ClassesService classesService;

    private StudentService studentService;

    private StudentIDGenerate generator;

    private RegisterService registerService;

    @Autowired
    private Validation<Student> validate;

    @Autowired
    private Validation<Register> validateregister;

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
            }


        });

    }
    @FXML
    private void getLoadData() {

        stuidtxt.setText(generator.getStudentIDGenerate());

    }

    private List<String> getSelectedDays() {
        List<String> selectedDays = new ArrayList<>();

        if (day1.isSelected()) selectedDays.add("Sunday");
        if (day2.isSelected()) selectedDays.add("Monday");
        if (day3.isSelected()) selectedDays.add("Tuesday");
        if (day4.isSelected()) selectedDays.add("Wednesday");
        if (day5.isSelected()) selectedDays.add("Thursday");
        if (day6.isSelected()) selectedDays.add("Friday");
        if (day7.isSelected()) selectedDays.add("Saturday");

        return selectedDays;
    }


    private void addStudent() {

        stusavebtn.setOnAction(event -> {
            try {
                String name = stunametxt.getText();

                Date dob = null;
                if (studof.getValue() != null) {
                    dob = Date.valueOf(studof.getValue());
                } else {
                    showInformationDialog("Error", "Date of Birth required", "Please select a valid Date of Birth.");
                    return;
                }

                String gender = stugender.getValue();
                String email = stuemailtxt.getText();
                String phone = stuphonetxt.getText();
                String address = stuaddresstxt.getText();


                String orgimgPath = null;

                if (stuimage.getImage() != null) {
                    orgimgPath = stuimage.getImage().getUrl().replace("file:", "");
                } else {
                    showInformationDialog("Error", "Image Not Found", "Please select an image before proceeding.");
                    return;
                }

                String decodedPath = URLDecoder.decode(orgimgPath, StandardCharsets.UTF_8);
                File sourceFile = new File(decodedPath);


                File destinationFolder = new File("D:/backImage/StudentImage");
                if (!destinationFolder.exists()) {
                    destinationFolder.mkdirs();
                }



                Student student = new Student(generator.getStudentIDGenerate(), name, dob, gender, phone, email, address);

                boolean bo = validate.testValidator(student);

                Classes classes = new Classes();
                classes.setClass_id(classesService.getClassesDTO().getClass_id());



                if(bo){

                  Register register = new Register(classes, student);

                  boolean bo1 = validateregister.testValidator(register);

                  if(bo1){

                      File destinationFile = new File(destinationFolder, name + ".jpg");
                      Files.copy(sourceFile.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

                      String photoPath = destinationFile.getAbsolutePath();

                      student.setPhoto_path(photoPath);

                     Set<StudentSchedule> schedule = new HashSet<>();

                      for(String s :getSelectedDays()){

                          LocalTime starttime = startTime.getLocalTime();
                          LocalTime endtime = endTime.getLocalTime();

                          StudentSchedule studentSchedule = new StudentSchedule();
                          studentSchedule.setStudent(student);
                          studentSchedule.setDay_of_week(s);
                          studentSchedule.setClasses(classes);
                          studentSchedule.setStart_time(Time.valueOf(starttime));
                          studentSchedule.setEnd_time(Time.valueOf(endtime));

                          schedule.add(studentSchedule);

                      }

                      student.setStudentScheduleSet(schedule);



                      registerService.insertTaskStudentAndRegister(student, register);

                      showInformationDialog("Success", "Register Completed", "Register record inserted successfully.");
                      resetForm();
                      getLoadData();

                  }


                }


//

            } catch ( IOException | NullPointerException | ConstraintViolationException e) {
                e.printStackTrace();

                showErrorDialog("Error", "Failed to save the image.", "Failed to Save the image or Insert Image");
            }
            catch (IllegalStateException e){

                showErrorDialog("Error", "Failed ", "Failed to Save Register");
            }







        });


    }

    private void resetForm() {
        stunametxt.clear();
        studof.setValue(null);
        stugender.setValue(null);
        stuphonetxt.clear();
        stuemailtxt.clear();
        stuaddresstxt.clear();
        stuimage.setImage(null);
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