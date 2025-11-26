package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.util.StringConverter;
import model.dto.Brand;
import model.dto.Supplier;
import service.BrandService;
import service.impl.BrandServiceImpl;

import java.net.URL;
import java.util.ResourceBundle;

public class AddNewBrandPopupController implements Initializable {

    BrandService brandService = new BrandServiceImpl();

    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnSave;

    @FXML
    private ComboBox<Supplier> comboSupplier;

    @FXML
    private Label lblNameError;

    @FXML
    private Label lblSupplierError;

    @FXML
    private TextField txtBrandName;

    @FXML
    void btnCancelonAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnSaveonAction(ActionEvent event) {
        if(isValidInputs()){
            if(brandService.save(getBrand())){
                showSuccessAlert("Saved Successfully", "The Brand has been added to the system.");
                clear();
            } else {
                showErrorAlert("The Brand has not been added to the system.");
            }

        }

    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    private void showSuccessAlert(String headerText, String contentText){
        successAlert.setTitle("Success");
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        successAlert.showAndWait();
    }

    private Brand getBrand(){
        Brand brand = new Brand();
        brand.setName(txtBrandName.getText());
        brand.setSupplier(comboSupplier.getValue());

        return brand;
    }

    private boolean isValidInputs(){
        return checkInputFields();
    }

    private void  clear(){
        txtBrandName.setText("");
        comboSupplier.setValue(null);
    }
    
    private boolean checkInputFields(){
        if(txtBrandName.getText().trim().isEmpty()){
            if(lblSupplierError.isVisible()){
                lblSupplierError.setVisible(false);
            }

            lblNameError.setVisible(true);
            return false;

        } else if (comboSupplier.getSelectionModel().getSelectedItem() == null) {
            if(lblNameError.isVisible()){
                lblNameError.setVisible(false);
            }

            lblSupplierError.setVisible(true);
            return false;

        } else {
            if(lblSupplierError.isVisible() || lblNameError.isVisible()){
                lblSupplierError.setVisible(false);
                lblNameError.setVisible(false);
            }
            return true;
        }
    }
    
    private void loadSupplier(){
        comboSupplier.setItems(brandService.getSuppliers());

        comboSupplier.setConverter(new StringConverter<Supplier>() {
            @Override
            public String toString(Supplier supplier) {
                return supplier != null ? supplier.getName()+" | "+supplier.getEmail() : "";
            }

            @Override
            public Supplier fromString(String s) {
                return null;
            }
        });
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadSupplier();
    }
}
