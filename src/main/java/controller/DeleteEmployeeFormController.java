package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.dto.Employee;
import service.EmployeeService;
import service.impl.EmployeeServiceImpl;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteEmployeeFormController implements Initializable {

    EmployeeService employeeService = new EmployeeServiceImpl();

    private ObservableList<Employee> allEmployeeList;

    private Employee selectedEmployee;

    private final Alert conformationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    private final Alert successAlert = new Alert(Alert.AlertType.INFORMATION);
    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView imgEmployee;

    @FXML
    private Label lblAddress;

    @FXML
    private Label lblDateOfBirth;

    @FXML
    private Label lblDistrict;

    @FXML
    private Label lblEmail;

    @FXML
    private Label lblFName;

    @FXML
    private Label lblGender;

    @FXML
    private Label lblLName;

    @FXML
    private Label lblMobile;

    @FXML
    private Label lblPCode;

    @FXML
    private Label lblProvince;

    @FXML
    private Label lblVisibleId;

    @FXML
    private ListView<Employee> lstEmployeeSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(isConformed()){
            if(employeeService.deleteEmployee(selectedEmployee)){
                showSuccessAlert("Deleted Successfully", "The employee has been deleted.");
                clear();
                loadList();
            } else {
                showErrorAlert("The employee has not been deleted.");
            }
        }
    }

    private void clear() {
        selectedEmployee = null;

        lblVisibleId.setText("N/A");
        lblFName.setText("N/A");
        lblLName.setText("N/A");
        lblGender.setText("N/A");
        lblDateOfBirth.setText("N/A");
        lblEmail.setText("N/A");
        lblMobile.setText("N/A");
        lblProvince.setText("N/A");
        lblDistrict.setText("N/A");
        lblAddress.setText("N/A");
        lblPCode.setText("N/A");
        loadImage();
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private boolean isConformed(){
        Optional<ButtonType> result = showConformationAlert();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.show();
    }

    private void showSuccessAlert(String headerText, String contentText){
        successAlert.setTitle("Success");
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        successAlert.show();
    }

    private Optional<ButtonType> showConformationAlert(){
        conformationAlert.setTitle("Delete Confirmation");
        conformationAlert.setHeaderText("Delete employee");
        conformationAlert.setContentText("Are you sure you want to delete employee\n"+lblVisibleId.getText()+" | "+lblFName.getText()+" "+lblLName.getText()+" ?");

        return conformationAlert.showAndWait();
    }


    private void loadImage(Image image){
        imgEmployee.setImage(image);
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
        lblFName.setText(emp.getFirstName());
        lblLName.setText(emp.getLastName());
        lblGender.setText(emp.getGender());
        lblDateOfBirth.setText(emp.getDob().toString());
        lblEmail.setText(emp.getEmail());
        lblMobile.setText(emp.getPhone());
        lblProvince.setText(emp.getProvince());
        lblDistrict.setText(emp.getDistrict());
        lblAddress.setText(emp.getStreetAddress());
        lblPCode.setText(emp.getPostalCode());
        loadImage(new Image(new File(emp.getImgPath()).toURI().toString()));
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadList();
        loadImage();

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
