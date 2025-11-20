package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class DeleteItemFormController {

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnDelete;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblColour;

    @FXML
    private Label lblCostPrice;

    @FXML
    private Label lblDescription;

    @FXML
    private Label lblName;

    @FXML
    private Label lblQuantity;

    @FXML
    private Label lblSellingPrice;

    @FXML
    private Label lblSize;

    @FXML
    private Label lblSubCategory;

    @FXML
    private Label lblSupplier;

    @FXML
    private ListView<?> lstSuggestions;

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
