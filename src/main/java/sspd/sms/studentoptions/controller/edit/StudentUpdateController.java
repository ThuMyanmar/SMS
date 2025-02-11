package sspd.sms.studentoptions.controller.edit;

import com.jfoenix.controls.JFXCheckBox;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import jfxtras.scene.control.LocalTimeTextField;

public class StudentUpdateController {

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

    @FXML
    private TextArea stuaddresstxt;

    @FXML
    private DatePicker studof;

    @FXML
    private TextField stuemailtxt;

    @FXML
    private ComboBox<?> stugender;

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

}
