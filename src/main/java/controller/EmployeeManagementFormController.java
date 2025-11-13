package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXRadioButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.stage.FileChooser;

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
    private ComboBox<?> comboDistrict;

    @FXML
    private ComboBox<?> comboProvince;

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

        System.out.println(saveEmployeeImg());
    }

    @FXML
    void btnUpdateonAction(ActionEvent event) {

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

    }
}
