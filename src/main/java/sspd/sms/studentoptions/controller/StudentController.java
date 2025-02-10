package sspd.sms.studentoptions.controller;

import javafx.fxml.Initializable;
import org.springframework.stereotype.Controller;
import sspd.sms.classoptions.services.ClassesService;

import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class StudentController implements Initializable {

    private ClassesService classesService;

    public StudentController(ClassesService classesService) {
        this.classesService = classesService;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        System.out.println(classesService.getClassesDTO().getClass_id());

    }
}
