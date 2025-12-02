package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class DeleteEmployeeFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDistrict;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFName;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblId;

    @FXML
    private Label lblLName;

    @FXML
    private Label lblMobile;

    @FXML
    private Label lblPCode;

    @FXML
    private Label lblProvince;

    @FXML
    private ListView<?> lstEmployeeSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {

    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

}
