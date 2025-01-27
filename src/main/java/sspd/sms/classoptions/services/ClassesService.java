package sspd.sms.classoptions.services;

import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import sspd.sms.classoptions.db.Classimpls;
import sspd.sms.classoptions.model.Classes;
import sspd.sms.config.AppConfig;

import java.util.List;

public class ClassesService {

    private ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
    Classimpls classimpls = (Classimpls) context.getBean(Classimpls.class);


    public List<Classes> getAllClasses() {

        return classimpls.getAllTask();

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

    public void ClassUpdate(Classes classes) {

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
