package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;
import model.dto.EmployeeDto;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;
import util.PasswordUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class EmployeeManagementFormController implements Initializable {

    FileChooser fileChooser;
    private File selectedImage;
    private final String employeeImageDir = "employee_image/";

    //----- comboBox Data List -----//
    ObservableList<String> Provinces = FXCollections.observableArrayList();
    ObservableList<String> districts = FXCollections.observableArrayList();

    //----- Employee Service for all logics -----//
    EmployeeService employeeService = new EmployeeServiceImpl();

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnUpload;

    @FXML
    private ComboBox<String> comboDistrict;

    @FXML
    private ComboBox<String> comboProvince;

    @FXML
    private DatePicker dateDob;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private ListView<?> lstSuggestions;

    @FXML
    private JFXRadioButton radioAdmin;

    @FXML
    private JFXRadioButton radioEmployee;

    @FXML
    private JFXRadioButton radioFemale;

    @FXML
    private JFXRadioButton radioMale;

    @FXML
    private ToggleGroup toggleGender;

    @FXML
    private ToggleGroup toggleRole;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtFirstName;

    @FXML
    private TextField txtLastName;

    @FXML
    private PasswordField txtPassword;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtPostalCode;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelonAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {

    }

    @FXML
    void btnSaveonAction(ActionEvent event) {

        if(checkInputFields()){
            EmployeeDto employeeDto = new EmployeeDto(
                    txtFirstName.getText(),
                    txtLastName.getText(),
                    getGender(),
                    dateDob.getValue(),
                    txtEmail.getText(),
                    txtPhone.getText(),
                    PasswordUtil.hashPassword(txtPassword.getText()),
                    getRole(),
                    comboProvince.getValue(),
                    comboDistrict.getValue(),
                    txtAddress.getText(),
                    txtPostalCode.getText(),
                    saveEmployeeImg()
            );
            boolean b = employeeService.saveEmployee(employeeDto);

            if(b){
                System.out.println("saved successed...");
            }else {
                System.out.println("not saved...");
            }

        }
    }

    @FXML
    void btnUpdateonAction(ActionEvent event) {
        checkInputFields();
    }

    @FXML
    void btnUploadonAction(ActionEvent event) {
        //----- Open the File Chooser Window -----//
        File file = fileChooser.showOpenDialog(null);

        if(file != null){
            selectedImage = file;
            imgEmployee.setImage(new Image(selectedImage.toURI().toString()));
        }
    }

    @FXML
    void comboProvinceonAction(ActionEvent event) {
        if(comboDistrict.isDisable()){
            comboDistrict.setDisable(false);
        }
        
        if(comboProvince.getValue().equals("Western")){
            districts.clear();
            districts.addAll("Gampaha", "Colombo", "Kalutara");

        } else if (comboProvince.getValue().equals("Central")) {
            districts.clear();
            districts.addAll("Matale", "Kandy", "Nuwara Eliya");

        } else if (comboProvince.getValue().equals("Southern")) {
            districts.clear();
            districts.addAll("Galle", "Matara", "Hambantota");

        } else if (comboProvince.getValue().equals("Eastern")) {
            districts.clear();
            districts.addAll("Trincomalee", "Batticaloa", "Ampara");

        } else if (comboProvince.getValue().equals("Northern")) {
            districts.clear();
            districts.addAll("Jaffna", "Kilinochchi", "Mannar", "Mullaitivu", "Vavuniya");

        } else if (comboProvince.getValue().equals("North Western")) {
            districts.clear();
            districts.addAll("Kurunegala", "Puttalam");

        } else if (comboProvince.getValue().equals("North Central")) {
            districts.clear();
            districts.addAll("Anuradhapura", "Polonnaruwa");

        } else if (comboProvince.getValue().equals("Sabaragamuwa")) {
            districts.clear();
            districts.addAll("Kegalle", "Ratnapura");

        } else {
            districts.clear();
            districts.addAll("Badulla", "Monaragala");
        }

        comboDistrict.setItems(districts);
    }

    @FXML
    void radioFemale(ActionEvent event) {

    }

    @FXML
    void txtSearchonAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonKeyReleased(KeyEvent event) {

    }
    private String getRole(){
        if(radioAdmin.isSelected()){
            return "Admin";
        }else {
            return "Employee";
        }
    }

    private String getGender(){
        if(radioMale.isSelected()){
            return "Male";
        }else {
            return "Female";
        }
    }

    private boolean checkInputFields(){
        if(txtFirstName.getText() == null || txtFirstName.getText().isEmpty()){
            showErrorAlerts("Entre First Name");
            return false;

        } else if (txtLastName.getText() == null || txtLastName.getText().isEmpty()) {
            showErrorAlerts("Entre Last Name");
            return false;

        } else if (!(radioMale.isSelected() || radioFemale.isSelected())) {
            showErrorAlerts("Select Gender");
            return false;

        } else if (dateDob.getValue() == null) {
            showErrorAlerts("Entre Date of Birth");
            return false;

        } else if (txtEmail.getText() == null || txtEmail.getText().isEmpty()) {
            showErrorAlerts("Entre Employee Email Address");
            return false;

        } else if (!validateEmail()) {
            showErrorAlerts("Input Valid Email Addres");
            return false;

        } else if (txtPhone.getText() == null || txtPhone.getText().isEmpty()) {
            showErrorAlerts("Entre Phone Number");
            return false;

        } else if (txtPassword.getText() == null || txtPassword.getText().isEmpty()) {
            showErrorAlerts("Entre Password");
            return false;

        } else if (!(radioAdmin.isSelected() || radioEmployee.isSelected())) {
            showErrorAlerts("Select Role");
            return false;

        } else if (comboDistrict.getValue() == null) {
            showErrorAlerts("Select District");
            return false;

        } else if (comboProvince.getValue() == null) {
            showErrorAlerts("Select Province");
            return false;

        } else if (txtAddress.getText() == null || txtAddress.getText().isEmpty()) {
            showErrorAlerts("Entre Street Address");
            return false;

        } else if (txtPostalCode.getText() == null || txtPostalCode.getText().isEmpty()) {
            showErrorAlerts("Entre Postal Code");
            return false;

        } else {
            return true;
        }

    }

    private boolean validateEmail(){
        String email = txtEmail.getText();

        if (email.equals(email.toLowerCase())){
            try {
                if(email.substring(email.length()-10).equals("@gmail.com")){
                    return true;
                }
            }catch (StringIndexOutOfBoundsException ex){
                return false;
            }
        }

        return false;
    }

    private void showErrorAlerts(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(message);
        alert.showAndWait();
    }

    private String saveEmployeeImg(){
        try {
            System.out.println(selectedImage.getName());
            String uniqueName = System.currentTimeMillis() + "_" + selectedImage.getName();
            System.out.println(uniqueName);
            File destinationFile = new File(employeeImageDir + uniqueName);
            System.out.println(destinationFile.getName()+","+destinationFile.getPath());

            Files.copy(selectedImage.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            return destinationFile.getPath();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //----- Initialize File Chooser -----//
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Employee Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        //----- Create a Directly to Save Employee Images if not Exists -----//
        File dir = new File(employeeImageDir);
        if(!dir.exists()){
            dir.mkdir();
        }
        
        Provinces.addAll("Western", "Uva", "Southern", "Sabaragamuwa", "Northern", "North Western", "North Central", "Eastern", "Central");
        comboProvince.setItems(Provinces);

    }
}
