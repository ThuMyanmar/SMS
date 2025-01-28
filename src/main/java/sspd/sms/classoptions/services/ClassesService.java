package sspd.sms.classoptions.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sspd.sms.classoptions.db.Classimpls;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.classoptions.model.Classview;
import sspd.sms.config.AppConfig;
import sspd.sms.config.SpringContextHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ClassesService {

    private  ApplicationContext context = SpringContextHelper.getContext();
    Classimpls classimpls =  context.getBean(Classimpls.class);


    List<Classview> classviews = new ArrayList<>();

    List<Classes> classes = new ArrayList<>();





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
                            c.getStatus()==1?"Open":"Closed"
                    );
                })
                .collect(Collectors.toList());

        classes.addAll(classimpls.getAllTask());

        classviews.addAll(classesList);

        return classesList;
    }

    public int countClasses() {

        return classviews.size();
    }


    public void SaveClass(Classes classes) {


        if(getStatus(classes.getClass_id())==0) {


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
        else {

            showErrorDialog("Data Insert not Successful","Close Class","Your class is ful!!!");


        }







    }

    public int getStatus(int class_id) {
        return classes.stream()
                .filter(classes1 -> classes1.getClass_id() == class_id)
                .map(Classes::getStatus)
                .findFirst()
                .orElse(-1);
    }

    public int  getLimitStu(int class_id) {

        return classes.stream()
                .filter(classes1 -> classes1.getClass_id() == class_id)
                .map(Classes::getLimit_stu)
                .findFirst()
                .orElse(-1);


    }


    public void classUpdate(Classes classes) {

        classimpls.updateTask(classes);


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
