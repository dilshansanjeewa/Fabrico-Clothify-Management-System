package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class UpdateEmployeeFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<?> comboDistrict;

    @FXML
    private ComboBox<?> comboProvince;

    @FXML
    private DatePicker dateofBirth;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private ListView<?> lstEmployeeSuggestions;

    @FXML
    private JFXRadioButton radioFemale;

    @FXML
    private JFXRadioButton radioMale;

    @FXML
    private ToggleGroup toggleGender;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private TextField txtNumber;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {

    }

    @FXML
    void imgEmployeeOnMouseClicked(MouseEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
