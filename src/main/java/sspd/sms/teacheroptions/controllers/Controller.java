package sspd.sms.teacheroptions.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.print.PrinterJob;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import sspd.sms.Launch;
import sspd.sms.config.AppConfig;
import sspd.sms.errorHandler.GlobalExceptionHandler;
import sspd.sms.teacheroptions.db.Timpls;
import sspd.sms.teacheroptions.model.Teacher;
import sspd.sms.teacheroptions.model.TeacherDTO;
import sspd.sms.teacheroptions.services.TeacherServices;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;
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
    private MenuItem exportToExcelbtn;

    @FXML
    private MenuItem importToDatabtn;

    @FXML
    private TextArea teacheraddresstxt;

    @FXML
    private ImageView teacherimg;

    @FXML
    private Button uploadimgbtn;


    @FXML
    private TableColumn<Teacher, String> addressCol;

    @FXML
    private Button editbtn;




    @FXML
    private Label totaltlb;

    private ApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);


    private Timpls tdb = context.getBean(Timpls.class);

    private TeacherServices tservice = new TeacherServices();





    @Override
    public void initialize(URL location, ResourceBundle resources) {

        getLoadData();
        actionEvent();
        tableIni();
        initializeSearch();
        teacherimg.getStyleClass().add("/style/imageview.css");

        teachertable.setEditable(true);

        rowUpdate();

        exportToExcelbtn.setOnAction(event -> {



            ObservableList<Teacher> data = FXCollections.observableArrayList(tdb.getAllTask());

            totaltlb.setText(String.valueOf(data.size()));

            teachertable.setItems(data);

            exportToExcel(teachertable,new Stage());





        });

        importToDatabtn.setOnAction(event -> {

            importFromExcel(new Stage());

        });





    }

    private void tableIni() {

        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        qualificationCol.setCellValueFactory(new PropertyValueFactory<>("qualification"));
        contactCol.setCellValueFactory(new PropertyValueFactory<>("contact"));
        emailCol.setCellValueFactory(new PropertyValueFactory<>("email"));
        addressCol.setCellValueFactory(new PropertyValueFactory<>("address"));

    }

    private void actionEvent() {



        Thread.setDefaultUncaughtExceptionHandler(new GlobalExceptionHandler());


        teachersubjectbtn.setOnAction(event -> {


            Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();


            if(teacher==null){

                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("No Selection");
                alert.setHeaderText("No Teacher Selected");
                alert.setContentText("Please select a teacher from the table.");
                alert.showAndWait();
                return;
            }

            int teacherId = tservice.getTeacherId(teacher.getName());
            if (teacherId <= 0) {

                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setHeaderText("Teacher ID Retrieval Failed");
                alert.setContentText("Unable to fetch teacher ID for: " + teacher.getName());
                alert.showAndWait();
                return;
            }

            teacher.setTeacher_id(teacherId);

            TeacherDTO teacherDTO = TeacherDTO.getInstance();
            teacherDTO.setTeacher(teacher);




            FXMLLoader fxmlLoader = new FXMLLoader(Launch.class.getResource("/layout/teachersubjectsview.fxml"));

            Scene scene = null;

            Stage stage = new Stage();

            try{

                scene = new Scene(fxmlLoader.load());
                stage.initStyle(StageStyle.UTILITY);
                stage.initModality(Modality.WINDOW_MODAL);
                Stage mainStage = (Stage) teachertable.getScene().getWindow();
                stage.initOwner(mainStage);
                stage.setTitle("Teacher Subjects");
                stage.setScene(scene);
                stage.show();



            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        });






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
                    teacherimg.setImage(image);



                } catch (Exception e) {
                    e.printStackTrace();

                }
            } else {

            }
        });


        savebtn.setOnAction(event -> {

                String name = teachernametxt.getText();
                String quali = teacherqualificationtxt.getText();
                String contact = teachercontacttxt.getText();
                String email = teacheremailtxt.getText();
                String address = teacheraddresstxt.getText();

                String orgPath = teacherimg.getImage().getUrl();
                File sourceFile = new File(orgPath.replace("file:",""));

                File destinationFolder = new File("D:/backImage");

                if(!destinationFolder.exists()){
                    destinationFolder.mkdir();
                }

                File destinationFile = new File(destinationFolder,name+".jpg");

            try {
                Files.copy(sourceFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                String photoPath = destinationFile.getAbsolutePath();
                Teacher teacher = new Teacher(name, quali, contact, email,address,photoPath);
                tdb.insertTask(teacher);
                getLoadData();
                setTextnull();

            } catch (IOException  | org.hibernate.exception.ConstraintViolationException exception) {

                showInformationDialog( "Error", "Failed to save the image.","Failded to Save the image");
            }











        });

        teachertable.setOnKeyPressed(event -> {
          //
            if(event.getCode() == KeyCode.DELETE){

                Teacher teacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();
                if (teacher == null) {
                    showInformationDialog("Error", "No Selection", "Please select a teacher to delete.");
                    return;
                }

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Confirmation");
                alert.setHeaderText("Delete Confirmation");
                alert.setContentText("Are you sure you want to delete this teacher?");

                if(alert.showAndWait().orElse(ButtonType.CANCEL) == ButtonType.OK) {

                    String photPath = getPhotoPath(teacher.getName(),teacher.getEmail());

                    if(photPath!= null && !photPath.isEmpty()){

                        File photoFile = new File(photPath);

                        if(photoFile.exists() && photoFile.isFile()){


                            boolean deleted = photoFile.delete();
                            tdb.deleteTask(teacher);
                            getLoadData();

                            if(!deleted){
                                showInformationDialog("Data Delete Photo Error","Teacher","Teacher Error Photo Delete !!!");
                            }



                        }

                    }

                }



            }


        });





        teachertable.setOnMouseClicked(event -> {


           Teacher oldteacher = (Teacher) teachertable.getSelectionModel().getSelectedItem();

            teachernametxt.setText(oldteacher.getName());
            teacherqualificationtxt.setText(oldteacher.getQualification());
            teachercontacttxt.setText(oldteacher.getContact());
            teacheremailtxt.setText(oldteacher.getEmail());
            teacheraddresstxt.setText(oldteacher.getAddress());

            String photoPath = getPhotoPath(oldteacher.getName(), oldteacher.getEmail());

            Image image = new Image("file:" + photoPath);
            teacherimg.setImage(image);

            editbtn.setOnAction(event1 -> {


                String name = teachernametxt.getText();
                String qualification = teacherqualificationtxt.getText();
                String contact = teachercontacttxt.getText();
                String email = teacheremailtxt.getText();
                String address =teacheraddresstxt.getText();

                String orgPath = teacherimg.getImage().getUrl();
                File sourceFile = new File(orgPath.replace("file:",""));

                File destinationFolder = new File("D:/backImage");

                if(!destinationFolder.exists()){
                    destinationFolder.mkdir();
                }



                File destinationFile = new File(destinationFolder,name+".jpg");

                try {


                    Files.copy(sourceFile.toPath(),destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
                    String upphotoPath = destinationFile.getAbsolutePath();
                    Teacher upteacher = new Teacher(oldteacher.getTeacher_id(),name, qualification, contact, email,address,upphotoPath);
                    tdb.updateTask(upteacher);
                    getLoadData();
                    setTextnull();

                } catch (IOException e) {
                    showInformationDialog( "Error", "Failed to save the image.","Failded to Save the image");
                }





            });




        });







    }

    private int getTeachID(String name,String email){


        return tdb.getAllTask().stream()
                .filter(teacher -> teacher.getName().equals(name) && teacher.getEmail().equals(email))
                .map(Teacher::getTeacher_id)
                .findFirst()
                .orElse(-1);



    }


    private String getPhotoPath(String name, String email) {
        return tdb.getAllTask().stream()
                .filter(teacher -> teacher.getName().equals(name) && teacher.getEmail().equals(email))
                .map(Teacher::getPhoto)
                .findFirst()
                .orElse(null);
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
        teacheraddresstxt.setText("");
        teacherimg.setImage(null);

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


    private void showInformationDialog(String title, String header, String content) {
        javafx.application.Platform.runLater(() -> {
            javafx.scene.control.Alert alert = new javafx.scene.control.Alert(Alert.AlertType.INFORMATION);
            alert.setTitle(title);
            alert.setHeaderText(header);
            alert.setContentText(content);
            alert.showAndWait();
        });
    }

    public void exportToExcel(TableView<Teacher> tableView, Stage stage) {






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
                    Cell cell = headerRow.createCell(i);
                    cell.setCellValue(tableView.getColumns().get(i).getText());
                }


                ObservableList<?> items = tableView.getItems();
                for (int rowIndex = 0; rowIndex < items.size(); rowIndex++) {
                    Row dataRow = sheet.createRow(rowIndex + 1);
                    Object rowData = items.get(rowIndex);

                    for (int colIndex = 0; colIndex < tableView.getColumns().size(); colIndex++) {
                        Object cellData = tableView.getColumns().get(colIndex)
                                .getCellObservableValue((Teacher) rowData)
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

                ObservableList<Teacher> data = FXCollections.observableArrayList();

                System.out.println(sheet.getLastRowNum());

                for (int rowIndex = 1; rowIndex <= sheet.getLastRowNum(); rowIndex++) {
                    Row row = sheet.getRow(rowIndex);


                    String name = getCellValueAsString(row.getCell(0));
                    String qualification = getCellValueAsString(row.getCell(1));
                    String content = getCellValueAsString(row.getCell(2));
                    String email = getCellValueAsString(row.getCell(3));
                    String address = getCellValueAsString(row.getCell(4));
                    String photo = getCellValueAsString(row.getCell(5));




                    data.add(new Teacher(name, qualification, content,email,address,photo));
                }






              tdb.bactchTask(data);




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






}
