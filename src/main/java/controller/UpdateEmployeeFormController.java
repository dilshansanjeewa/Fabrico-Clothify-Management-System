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
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import model.dto.Employee;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.ResourceBundle;

public class UpdateEmployeeFormController implements Initializable {

    private final EmployeeService employeeService = new EmployeeServiceImpl();

    private final String employeeImageDir = "employee_images/";
    private FileChooser fileChooser;
    private File employeeImagefile;
    private File selectedImage;
    
    private Employee selectedEmployee;

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

    private ObservableList<Employee> allEmployeeList;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private ComboBox<String> comboDistrict;

    @FXML
    private ComboBox<String> comboProvince;

    @FXML
    private DatePicker dateofBirth;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private Label lblVisibleId;

    @FXML
    private ListView<Employee> lstEmployeeSuggestions;

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
        clear();
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(isAllInputsExist()){
            if(employeeService.updateEmployee(getEmployee())){
                showSuccessAlert("Saved Successfully", "The employee details has been updated");
                clear();
                loadList();
            }else {
                showErrorAlert("The employee details has not been updated");
            }
        }
    }

    @FXML
    void imgEmployeeOnMouseClicked(MouseEvent event) {
        //----- Open the File Chooser Window -----//
        employeeImagefile = fileChooser.showOpenDialog(null);

        if(employeeImagefile != null){
            selectedImage = employeeImagefile;
            loadImage(employeeImagefile);
//            loadImage(new Image(selectedImage.toURI().toString()));
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private boolean isAllInputsExist() {
        if(txtFirstName.getText().trim().isEmpty()){
            showErrorAlert("Please input first name");
            return false;

        } else if (txtLastName.getText().trim().isEmpty()) {
            showErrorAlert("Please input last name");
            return false;

        } else if (!(radioFemale.isSelected() || radioMale.isSelected())) {
            showErrorAlert("Please select gender");
            return false;

        } else if (dateofBirth.getValue() == null) {
            showErrorAlert("Please input date of birth");
            return false;

        } else if (txtEmail.getText().trim().isEmpty()) {
            showErrorAlert("Please email address");
            return false;

        } else if (txtNumber.getText().trim().isEmpty()) {
            showErrorAlert("Please input mobile number");
            return false;

        } else if (comboProvince.getValue() == null) {
            showErrorAlert("Please select province");
            return false;

        } else if (comboDistrict.getValue() == null) {
            showErrorAlert("Please select district");
            return false;

        } else if (txtAddress.getText().trim().isEmpty()) {
            showErrorAlert("Please input street address");
            return false;

        } else if (txtPostalCode.getText().trim().isEmpty()) {
            showErrorAlert(("Please input postal code"));
            return false;

        } else if (selectedImage == null) {
            showErrorAlert("Please select employee image");
            return false;

        } else {
            return true;
        }
    }

    private void showSuccessAlert(String headerText, String contentText){
        successAlert.setTitle("Success");
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        successAlert.showAndWait();
    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("INPUT ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.show();
    }

    private Employee getEmployee() {
//        Employee employee = new Employee();
        selectedEmployee.setVisibleId(lblVisibleId.getText());
        selectedEmployee.setFirstName(txtFirstName.getText());
        selectedEmployee.setLastName(txtLastName.getText());
        selectedEmployee.setGender(getGender());
        selectedEmployee.setDob(dateofBirth.getValue());
        selectedEmployee.setEmail(txtEmail.getText());
        selectedEmployee.setPhone(txtNumber.getText());
        selectedEmployee.setProvince(comboProvince.getValue());
        selectedEmployee.setDistrict(comboDistrict.getValue());
        selectedEmployee.setStreetAddress(txtAddress.getText());
        selectedEmployee.setPostalCode(txtPostalCode.getText());
        selectedEmployee.setImgPath(saveImage());

        return selectedEmployee;
    }

    private String saveImage(){
        try {
//            String uniqueName = System.currentTimeMillis() + "_" + selectedImage.getName();
            File destinationFile = new File(employeeImageDir + System.currentTimeMillis() + "_updated_" + selectedImage.getName());

            Files.copy(selectedImage.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destinationFile.getPath();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String getGender() {

        if(radioMale.isSelected()){
            return "Male";
        }else {
            return "Female";
        }
    }

    private void clear() {
        selectedEmployee = null;
        lblVisibleId.setText("N/A");
        txtFirstName.setText("");
        txtLastName.setText("");
        if(radioMale.isSelected()) radioMale.setSelected(false);
        else radioFemale.setSelected(false);
        dateofBirth.setValue(null);
        txtEmail.setText("");
        txtNumber.setText("");
        comboProvince.getSelectionModel().clearSelection();
        comboDistrict.getSelectionModel().clearSelection();
        if(!comboDistrict.isDisable()) comboDistrict.setDisable(true);
        txtAddress.setText("");
        txtPostalCode.setText("");
        selectedImage = null;
        loadImage();
    }

    private void loadImage(File imageFile){
        selectedImage = imageFile;
        imgEmployee.setImage(new Image(selectedImage.toURI().toString()));
    }

    private void loadImage(){
        imgEmployee.setImage(new Image("/images/other/demo employee image.png"));
    }

    private void loadList(){
        allEmployeeList = employeeService.getAllEmployees();
    }

    private void filterEmployees(String keyWord) {
        if (keyWord == null || keyWord.trim().isEmpty()) {
            lstEmployeeSuggestions.setVisible(false);
            lstEmployeeSuggestions.getItems().clear();
            return;
        }

        List<Employee> result = allEmployeeList.stream()
                .filter(emp ->
                        emp.getVisibleId().toLowerCase().contains(keyWord.toLowerCase()) ||
                                emp.getFirstName().toLowerCase().contains(keyWord.toLowerCase()) ||
                                emp.getLastName().toLowerCase().contains(keyWord.toLowerCase())
                )
                .toList();

        lstEmployeeSuggestions.getItems().setAll(result);

        lstEmployeeSuggestions.setVisible(!result.isEmpty());
    }

    private void fillEmployeeForm(Employee emp) {
        selectedEmployee = emp;

        lblVisibleId.setText(emp.getVisibleId());
        txtFirstName.setText(emp.getFirstName());
        txtLastName.setText(emp.getLastName());
        if(emp.getGender().equals("Male")) radioMale.setSelected(true);
        else radioFemale.setSelected(true);
        dateofBirth.setValue(emp.getDob());
        txtEmail.setText(emp.getEmail());
        txtNumber.setText(emp.getPhone());
        comboProvince.setValue(emp.getProvince());
        comboDistrict.setValue(emp.getDistrict());
        txtAddress.setText(emp.getStreetAddress());
        txtPostalCode.setText(emp.getPostalCode());
        loadImage(new File(emp.getImgPath()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //----- Initialize File Chooser -----//
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select employee image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        loadImage();

        ObservableList<String> provinces = FXCollections.observableArrayList();
        provinces.addAll("Western", "Central", "Southern", "Eastern", "Northern", "North Western", "North Central", "Sabaragamuwa", "Uva");
        comboProvince.setItems(provinces);

        ObservableList<String> districts = FXCollections.observableArrayList();

        comboProvince.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(comboDistrict.isDisable()){
                comboDistrict.setDisable(false);
            }

            try {
                if(newValue.equals("Western")){
                    districts.clear();
                    districts.addAll("Colombo", "Gampaha", "Kalutara");

                } else if (newValue.equals("Central")) {
                    districts.clear();
                    districts.addAll("Kandy", "Matale", "Nuwara Eliya");

                } else if (newValue.equals("Southern")) {
                    districts.clear();
                    districts.addAll("Galle", "Matara", "Hambantota");

                } else if (newValue.equals("Eastern")) {
                    districts.clear();
                    districts.addAll("Batticaloa", "Ampara", "Trincomalee");

                } else if (newValue.equals("Northern")) {
                    districts.clear();
                    districts.addAll("Jaffna", "Kilinochchi", "Mannar", "Mullaitivu", "Vavuniya");

                } else if (newValue.equals("North Western")) {
                    districts.clear();
                    districts.addAll("Kurunegala", "Puttalam");

                } else if (newValue.equals("North Central")) {
                    districts.clear();
                    districts.addAll("Anuradhapura", "Polonnaruwa");

                } else if (newValue.equals("Sabaragamuwa")) {
                    districts.clear();
                    districts.addAll("Kegalle", "Ratnapura");

                } else {
                    districts.clear();
                    districts.addAll("Badulla", "Moneragala");
                }

                comboDistrict.setItems(districts);
            }catch (NullPointerException e){

            }
        });

        loadList();

        //----- item appear method in list view -----

        lstEmployeeSuggestions.setCellFactory(listView -> new ListCell<Employee>(){
            @Override
            protected void updateItem(Employee emp, boolean empty) {
                super.updateItem(emp, empty);
                if (empty || emp == null) {
                    setText(null);
                } else {
                    setText(emp.getVisibleId() + " - "
                            + emp.getFirstName() + " " + emp.getLastName());
                }
            }
        });

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterEmployees(newValue);
        });

        //----- real time filtering -----
        txtSearch.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!lstEmployeeSuggestions.getItems().isEmpty()) {
                        Employee emp = lstEmployeeSuggestions.getItems().get(0);
                        fillEmployeeForm(emp);
                        lstEmployeeSuggestions.setVisible(false);
                    }
                }
            }
        });

        lstEmployeeSuggestions.setOnMouseClicked(mouseEvent -> {
            Employee selected = lstEmployeeSuggestions.getSelectionModel().getSelectedItem();
            if(selected != null){
                fillEmployeeForm(selected);
                lstEmployeeSuggestions.setVisible(false);
            }
        });
    }
}
