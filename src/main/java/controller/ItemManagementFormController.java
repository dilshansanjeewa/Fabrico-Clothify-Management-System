package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ItemManagementFormController implements Initializable {

    @FXML
    private JFXButton btnAll;

    @FXML
    private JFXButton btnBarcode;

    @FXML
    private JFXButton btnDeleteItems;

    @FXML
    private JFXButton btnSaveItems;

    @FXML
    private JFXButton btnUpdateItems;

    @FXML
    private ImageView imgAlertIcon;

    @FXML
    private BorderPane itemManagementBorderPane;

    @FXML
    private Label lblLowStock;

    @FXML
    void btnAllOnAction(ActionEvent event) {
        loadContent("/view/all_item_form.fxml");
    }

    @FXML
    void btnBarcodeOnAction(ActionEvent event) {
        loadContent("/view/item_barcode_form.fxml");
    }

    @FXML
    void btnDeleteItemsOnAction(ActionEvent event) {
        loadContent("/view/delete_item_form.fxml");
    }

    @FXML
    void btnSaveItemsOnAction(ActionEvent event) {
        loadContent("/view/save_item_form.fxml");
    }

    @FXML
    void btnUpdateItemsOnAction(ActionEvent event) {
        loadContent("/view/update_item_form.fxml");
    }

    private void loadContent(String path){
        try {
            itemManagementBorderPane.setCenter(FXMLLoader.load(getClass().getResource(path)));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        loadContent("/view/all_item_form.fxml");
    }
}
