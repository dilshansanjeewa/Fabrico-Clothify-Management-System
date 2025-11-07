package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class ItemManagementFormController {

    @FXML
    private JFXButton btnAdd;

    @FXML
    private JFXButton btnAddNewBrand;

    @FXML
    private JFXButton btnAddSupplier;

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private JFXButton btnUpdate;

    @FXML
    private JFXButton btnUpload;

    @FXML
    private ComboBox<?> comboBrand;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private ComboBox<?> comboSize;

    @FXML
    private ComboBox<?> comboSubCategory;

    @FXML
    private ComboBox<?> comboSuplier;

    @FXML
    private DatePicker dateAdding;

    @FXML
    private ImageView imgItem;

    @FXML
    private ColorPicker itmColor;

    @FXML
    private Label lblLowStock;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtCostPrice;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtItemName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSearch;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    void btnAddNewBrandonAction(ActionEvent event) {

    }

    @FXML
    void btnAddSupplieronAction(ActionEvent event) {
        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/add_new_supplier_form.fxml"));
//            Parent root = new FXMLLoader(getClass().getResource("/view/add_new_supplier_form.fxml")).load();
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(new Scene(new FXMLLoader(getClass().getResource("/view/add_new_supplier_form.fxml")).load()));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnAddonAction(ActionEvent event) {

    }

    @FXML
    void btnCancelonAction(ActionEvent event) {

    }

    @FXML
    void btnDeleteonAction(ActionEvent event) {

    }

    @FXML
    void btnUpdateonAction(ActionEvent event) {

    }

    @FXML
    void btnUploadonAction(ActionEvent event) {

    }

    @FXML
    void comboSuplieronAction(ActionEvent event) {

    }

    @FXML
    void itmColoronAction(ActionEvent event) {

    }

    @FXML
    void txtColoronAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonAction(ActionEvent event) {

    }

}
