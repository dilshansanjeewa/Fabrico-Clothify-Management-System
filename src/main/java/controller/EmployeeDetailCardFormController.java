package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.dto.Employee;

import java.io.File;

public class EmployeeDetailCardFormController {

    @FXML
    private ImageView imgEmployee;

    @FXML
    private Label lblDistrict;

    @FXML
    private Label lblDob;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFirstName;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblLastName;

    @FXML
    private Label lblMobile;

    @FXML
    private Label lblPostalCode;

    @FXML
    private Label lblProvince;

    @FXML
    private Label lblStreetAddress;

    @FXML
    private Label lblVisibleId;


    public void setCardData(Employee employee){
        lblVisibleId.setText(employee.getVisibleId());
        lblFirstName.setText(employee.getFirstName());
        lblGender.setText(employee.getGender());
        lblDob.setText(employee.getDob().toString());
        lblEmail.setText(employee.getEmail());
        lblMobile.setText(employee.getPhone());
        lblProvince.setText(employee.getProvince());
        lblDistrict.setText(employee.getDistrict());
        lblStreetAddress.setText(employee.getStreetAddress());
        lblPostalCode.setText(employee.getPostalCode());
        imgEmployee.setImage(new Image(new File(employee.getImgPath()).toURI().toString()));
    }

}
