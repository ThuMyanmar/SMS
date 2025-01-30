package sspd.sms.courseoptions.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import sspd.sms.courseoptions.model.Course;
import sspd.sms.courseoptions.services.Services;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

@Controller
public class Coursecontroller implements Initializable {

    @FXML
    private TableColumn<Course, String> DesCol;

    @FXML
    private TableView coursetable;

    @FXML
    private TextArea desctxt;

    @FXML
    private TableColumn<Course, Double> durationCol;

    @FXML
    private TextField durationtxt;

    @FXML
    private Button editbtn;

    @FXML
    private TableColumn<Course, Integer> feeCol;

    @FXML
    private TextField feetxt;

    @FXML
    private TableColumn<Course, String> nameCol;

    @FXML
    private TextField nametxt;

    @FXML
    private Button savebtn;

    @FXML
    private Label totaltlb;

    @FXML
    private MenuItem exportbtn;

    @FXML
    private MenuItem importbtn;


    @Autowired
    Services services;


    @Override
    public void initialize(URL location, ResourceBundle resources) {


        tableIni();
       getLoadData();
        activeEvent();

    }

    private void activeEvent() {

        savebtn.setOnAction(event -> {


            String name = nametxt.getText();
            String desc =  desctxt.getText();

            if (!durationtxt.getText().isEmpty() && !feetxt.getText().isEmpty()) {
                try {

                    double duration = Double.parseDouble(durationtxt.getText());
                    int fee = Integer.parseInt(feetxt.getText());


                    Course course = new Course(name, desc, duration, fee);
                    int result = services.insertCourse(course);

                    if (result == 1) {

                        getClear();


                        getLoadData();




                    }


                } catch (NumberFormatException e) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Invalid Input");
                    alert.setHeaderText("Please enter valid numeric values.");
                    alert.setContentText("Duration and Fee must be numbers.");
                    alert.showAndWait();
                }
            } else {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Empty Fields");
                alert.setHeaderText("All fields are required.");
                alert.setContentText("Please fill out both the Duration and Fee fields.");
                alert.showAndWait();
            }





        });

        coursetable.setOnMouseClicked(event -> {


            Course oldcourse = (Course) coursetable.getSelectionModel().getSelectedItem();

            int courseId = services.findCourseByName(oldcourse.getCourse_name());

            nametxt.setText(oldcourse.getCourse_name());
            desctxt.setText(oldcourse.getDescription());
            durationtxt.setText(String.valueOf(oldcourse.getDuration()));
            feetxt.setText(String.valueOf(oldcourse.getFee()));

            editbtn.setOnAction(event1 -> {


                String name = nametxt.getText();
                String desc = desctxt.getText();
                double duration = Double.parseDouble(durationtxt.getText());
                int feet = Integer.parseInt(feetxt.getText());

                Course course = new Course(courseId, name, desc, duration, feet);

                services.updateCourse(course);

                getClear();
                getLoadData();

            });





        });

        coursetable.setOnKeyPressed(event -> {

            if(event.getCode() == KeyCode.DELETE) {


                Course course = (Course) coursetable.getSelectionModel().getSelectedItem();
                course.setCourse_id(services.findCourseByName(course.getCourse_name()));

                services.deleteCourse(course);

                getLoadData();

            }




        });

        exportbtn.setOnAction(event -> {

            ObservableList<Course> courses = FXCollections.observableArrayList(services.getAllCourses());

            coursetable.setItems(courses);


            exportToExcel(coursetable,new Stage());

        });

        importbtn.setOnAction(event -> {

            importFromExcel(new Stage());


        });


    }
    public void exportToExcel(TableView<Course> tableView, Stage stage) {






        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Excel File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );
        fileChooser.setInitialFileName("exported_data.xlsx");


        File selectedFile = fileChooser.showSaveDialog(stage);


        if (selectedFile != null) {
            try (FileOutputStream fos = new FileOutputStream(selectedFile);
                 Workbook workbook = new XSSFWorkbook()) {


                Sheet sheet = workbook.createSheet("Exported Data");


                Row headerRow = sheet.createRow(0);
                for (int i = 0; i < tableView.getColumns().size(); i++) {
                    org.apache.poi.ss.usermodel.Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableView.getColumns().get(i).getText());
                }


                ObservableList<?> items = tableView.getItems();
                for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
                    Row dataRow = sheet.createRow(rowIndex + 1);
                    Object rowData = items.get(rowIndex);

                    for (int colIndex = 0; colIndex < tableView.getColumns().size(); colIndex++) {
                        Object cellData = tableView.getColumns().get(colIndex)
                                .getCellObservableValue((Course) rowData)
                                .getValue();

                        Cell cell = dataRow.createCell(colIndex);
                        cell.setCellValue(cellData == null ? "" : cellData.toString());
                    }
                }


                workbook.write(fos);

                showInformationDialog("Export Success","Excel","Exported Data Success");


                System.out.println("Data exported successfully to: " + selectedFile.getAbsolutePath());
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error occurred while saving the file.");
            }
        } else {

            showInformationDialog("Export Cancelled","Excel","Exported Data Cancelled");
            System.out.println("File save operation cancelled.");
        }


    }

    public void importFromExcel(Stage stage) {

        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Excel File");
        fileChooser.getExtensionFilters().add(
                new FileChooser.ExtensionFilter("Excel Files", "*.xlsx")
        );

        File selectedFile = fileChooser.showOpenDialog(stage);
        if (selectedFile != null) {
            try (FileInputStream fis = new FileInputStream(selectedFile);
                 Workbook workbook = new XSSFWorkbook(fis)) {

                Sheet sheet = workbook.getSheetAt(0); // Assuming the first sheet is the one to read

                ObservableList<Course> data = FXCollections.observableArrayList();

                System.out.println(sheet.getLastRowNum());

                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);


                    String name = getCellValueAsString(row.getCell(0));
                    String desc = getCellValueAsString(row.getCell(1));
                    double duration = Double.parseDouble(getCellValueAsString(row.getCell(2)));
                    int fee = Integer.parseInt(getCellValueAsString(row.getCell(3)));





                    data.add(new Course(name, desc, duration, fee));
                }






                services.importCourse(data);




                getLoadData();


            } catch (IOException e) {
                e.printStackTrace();

            }
        } else {
            System.out.println("File open operation cancelled.");
        }
    }

    private String getCellValueAsString(Cell cell) {
        if (cell == null) {
            return ""; // Return empty string for null cells
        }
        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue();
            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getDateCellValue().toString(); // Convert date to string
                } else {
                    return String.valueOf((int) cell.getNumericCellValue()); // Convert numeric value to string
                }
            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());
            case FORMULA:
                return cell.getCellFormula(); // Return the formula as a string
            default:
                return ""; // Return empty string for other types
        }
    }

    private void getClear(){


        nametxt.setText("");
        desctxt.setText("");
        feetxt.setText("");
        durationtxt.setText("");
    }

    private void getLoadData() {


        ObservableList<Course> courses = FXCollections.observableArrayList(services.getAllCourses());

        coursetable.setItems(courses);
        totaltlb.setText(String.valueOf(services.getCoursesCount()));



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

    private void tableIni() {


        nameCol.setCellValueFactory(new PropertyValueFactory<>("course_name"));
        DesCol.setCellValueFactory(new PropertyValueFactory<>("description"));
        durationCol.setCellValueFactory(new PropertyValueFactory<>("duration"));
        feeCol.setCellValueFactory(new PropertyValueFactory<>("fee"));


    }
}
