package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import model.dto.Supplier;
import service.SupplierService;
import service.impl.SupplierServiceImpl;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class SupplierManagementFormController implements Initializable {

    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    Alert conformationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    Supplier supplier = new Supplier();
    SupplierService supplierService = new SupplierServiceImpl();

    ObservableList<Supplier> masterList;
    FilteredList filteredList;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnSave;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private TableColumn<?, ?> colEmail;

    @FXML
    private TableColumn<?, ?> colAddress;

    @FXML
    private TableColumn<Supplier, String> colName;

    @FXML
    private TableColumn<?, ?> colPhone;

    @FXML
    private ListView<?> lstBrands;

    @FXML
    private ListView<Supplier> lstSuggestions;

    @FXML
    private TableView<Supplier> tblSupplier;

    @FXML
    private TextField txtAddress;

    @FXML
    private TextField txtEmail;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtPhone;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clearInputs();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(isConformed()){
            System.out.println(txtName.getText());

            if(supplierService.delete(getSupplier())){
                showSuccessAlert("Deleted Successfully", "The supplier has been deleted.");
                clearInputs();
                loaSupplierTable();

            } else {
                System.out.println("canceled");
                showErrorAlert("The supplier has not been deleted.");
            }
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(isValidInputs()){
             boolean isSaved = supplierService.save(getSupplier());
             if (isSaved) {
                 showSuccessAlert("Saved Successfully", "The supplier has been added to the system.");
                 clearInputs();
                 loaSupplierTable();
             } else {
                 showErrorAlert("The supplier has not been added to the system.");
             }
        }
    }

    @FXML
    void btnUpdateOnAction(ActionEvent event) {
        if(isValidInputs()){
            boolean isUpdated = supplierService.update(getSupplier());

            if(isUpdated) {
                showSuccessAlert("Updated Successfully", "The supplier has been updated.");
                clearInputs();
                loaSupplierTable();
            }else {
                showErrorAlert("The supplier has not been updated.");
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private boolean isConformed(){
        Optional<ButtonType> result = showConformationAlert();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private Optional<ButtonType> showConformationAlert(){
        conformationAlert.setTitle("Delete Confirmation");
        conformationAlert.setHeaderText("Delete Supplier");
        conformationAlert.setContentText("Are you sure you want to delete supplier\n"+txtName.getText()+" ?");

        return conformationAlert.showAndWait();
    }

    private Supplier getSupplier(){
        supplier.setName(txtName.getText());
        supplier.setEmail(txtEmail.getText());
        supplier.setPhone(txtPhone.getText());
        supplier.setAddress(txtAddress.getText());

        return supplier;
    }

    private void loaSupplierTable(){
        tblSupplier.setItems(supplierService.getAll());

    }

    private void clearInputs(){
        txtName.setText("");
        txtEmail.setText("");
        txtPhone.setText("");
        txtAddress.setText("");
    }
    
    private boolean isValidInputs (){
        if(txtName.getText().trim().isEmpty()) {
            showErrorAlert("Please input supplier name");
            return false;
        } else if (txtEmail.getText().trim().isEmpty()) {
            showErrorAlert("Please input email address");
            return false;
        } else if (txtPhone.getText().trim().isEmpty()) {
            showErrorAlert("Please input phone number");
            return false;
        } else if (txtAddress.getText().trim().isEmpty()) {
            showErrorAlert("Please input address");
            return false;
        } else {
            return true;
        }
    }

    private void showSuccessAlert(String headerText, String contentText){
        alert.setTitle("Success");
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.show();
    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.show();
    }

    private void setSelectedValues(Supplier newSelection) {
        supplier.setId(newSelection.getId());
        txtName.setText(newSelection.getName());
        txtEmail.setText(newSelection.getEmail());
        txtPhone.setText(newSelection.getPhone());
        txtAddress.setText(newSelection.getAddress());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
        colPhone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        colAddress.setCellValueFactory(new PropertyValueFactory<>("address"));

        tblSupplier.getSelectionModel().selectedItemProperty()
                .addListener((observableValue, oldSelection, newSelection) ->{
                    if(newSelection != null){
                        setSelectedValues(newSelection);
                    }
                });

        loaSupplierTable();

        masterList = FXCollections.observableArrayList(supplierService.getSupplierList());
        filteredList = new FilteredList<>(masterList, s -> true);

        lstSuggestions.setItems(filteredList);

        lstSuggestions.setCellFactory(param -> new ListCell<Supplier>() {
            @Override
            protected void updateItem(Supplier item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        txtSearch.textProperty().addListener(((observableValue, oldValue, newValue) ->{
            filteredList.setPredicate(supplier -> {
                if (newValue == null || newValue.isBlank()) {
                    return true;
                }
                String keyword = newValue.toLowerCase();

                return supplier.toString().contains(keyword);
            });
        } ));
    }

}
