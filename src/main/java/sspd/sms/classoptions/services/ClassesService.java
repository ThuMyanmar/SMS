package sspd.sms.classoptions.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sspd.sms.classoptions.DTO.ClassesDTO;
import sspd.sms.classoptions.db.Classimpls;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.Classview;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ClassesService {


    private final Classimpls classimpls;


    private  ClassHasTeacherService classHasTeacherService;

    @Getter@Setter
    private ClassesDTO classesDTO;

    @Autowired
    public void setClassHasTeacherService(ClassHasTeacherService classHasTeacherService) {
        this.classHasTeacherService = classHasTeacherService;
    }

    public ClassesService(Classimpls classimpls) {
        this.classimpls = classimpls;

    }

    List<Classview> classviews = new ArrayList<>();

    List<Classes> classes = new ArrayList<>();


    public List<Classes>getClasses() {

        return classimpls.getAllTask();
    }

    public List<Classview>getClassviews() {

        return classimpls.getAllClassView();
    }

    public void studentLimitUpdate() {
        getClassviews().stream()
                .filter(c -> c.getRegistered_student() == c.getLimit_stu())
                .map(c -> {
                    Classes classes1 = new Classes();
                    classes1.setClass_id(getClassIdd(c.getClass_name()));
                    classes1.setStatus(0);
                    return classes1;
                })
                .forEach(classimpls::updateStatus);
    }

    public void UpdateLimit(){

        studentLimitUpdate();
    }

    public List<Classview> getAllClasses() {

        List<Classview> classesList = classimpls.getAllTask().stream()
                .map(c -> {

                    String courseName = (c.getCourse() != null) ? c.getCourse().getCourse_name() : "No Course Assigned";
                    return new Classview(
                            c.getClass_id(),
                            c.getDate(),
                            c.getClass_name(),
                            courseName,
                            c.getScedule(),
                            c.getLimit_stu(),
                            c.getStatus()==1?"Open":"Closed",
                            getTotalTeacherForClass(c.getClass_id())

                    );
                })
                .collect(Collectors.toList());

        classes.addAll(classimpls.getAllTask());

        classviews.addAll(classesList);

        return classesList;
    }

    private int getTotalTeacherForClass(int class_id) {

        return  classHasTeacherService.setAssignedTeachers(class_id).size();


    }

    public void SaveClass(Classes classes) {

            int oldSize = classimpls.getAllTask().size();
            classimpls.insertTask(classes);
            int newSize = classimpls.getAllTask().size();

            if(newSize != oldSize) {

                showInformationDialog("Data Insert Successful","Create Class","Class create Successfully!!!");


            }
            else {

                showErrorDialog("Data Insert not Successful","Create not Class","Class create not Successfully!!!");
            }

        }

    public static String calculateEndDate(int days) {
        LocalDate startDate = LocalDate.now();
        LocalDate endDate = startDate.plusDays(days-1);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return endDate.format(formatter);
    }

    public void classUpdate(Classes classes) {



        classimpls.updateTask(classes);


    }

    public int getClassIdd(String classname){

       getAllClasses();

      return   classes.stream()
                .filter(classes1 -> classes1.getClass_name().equals(classname))
                .map(Classes::getClass_id)
                .findFirst().orElse(-1);
    }

    public void ClassDelete(Classes classes) {

        int oldSize = classimpls.getAllTask().size();
        classimpls.deleteTask(classes);
        int newSize = classimpls.getAllTask().size();

        if(newSize != oldSize) {

            showInformationDialog("Data Delete Successful","Delete Class","Class delete Successfully!!!");

        }
        else {

            showErrorDialog("Data Delete not Successful","Class not delete","Class not delete Successfully!!!");

        }
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
