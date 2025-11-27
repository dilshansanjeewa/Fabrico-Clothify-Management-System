package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

import java.io.File;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DeleteItemFormController implements Initializable {

    private ItemService itemService = new ItemServiceImpl();
    private Item item;

    private ObservableList<Item> allItemList;
    private FilteredList<Item> filteredItemList;

    private Alert conformationAlert = new Alert(Alert.AlertType.CONFIRMATION);
    Alert alert = new Alert(Alert.AlertType.INFORMATION);
    Alert errorAlert = new Alert(Alert.AlertType.ERROR);

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
    private ListView<Item> lstSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnDeleteOnAction(ActionEvent event) {
        if(isConformed()){
            if(itemService.delete(item)){
                showSuccessAlert("Deleted Successfully", "The item has been deleted.");
                clear();
                loadList();
            } else {
                showErrorAlert("The item has not been deleted.");
            }
        }
    }

    @FXML
    void txtSearchOnAction(ActionEvent event) {

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

    private boolean isConformed(){
        Optional<ButtonType> result = showConformationAlert();

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private Optional<ButtonType> showConformationAlert(){
        conformationAlert.setTitle("Delete Confirmation");
        conformationAlert.setHeaderText("Delete Item");
        conformationAlert.setContentText("Are you sure you want to delete item\n"+lblName.getText()+" ?");

        return conformationAlert.showAndWait();
    }

    private void clear(){
        item = null;
        lblName.setText("N/A");
        lblCategory.setText("N/A");
        lblSubCategory.setText("N/A");
        lblBrand.setText("N/A");
        lblSupplier.setText("N/A");
        lblSize.setText("N/A");
        lblColour.setText("N/A");
        lblQuantity.setText("N/A");
        lblCostPrice.setText("N/A");
        lblSellingPrice.setText("N/A");
        lblDescription.setText("N/A");
        loadImage();
    }

    private void enableSearchFilter(){
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String keyWord = newValue.toLowerCase();

            filteredItemList.setPredicate(item -> item.getName().toLowerCase().contains(keyWord));
            lstSuggestions.setVisible(!keyWord.isEmpty() && !filteredItemList.isEmpty());
        });
    }

    private void enableItemSelectionEvents(){
        txtSearch.setOnAction(actionEvent -> {
            Item selected = lstSuggestions.getSelectionModel().getSelectedItem();

            if(selected == null && filteredItemList.isEmpty()){
                selected = filteredItemList.get(0);
            }
            if (selected != null) {
                fillFields(selected);
                lstSuggestions.setVisible(false);
            }
        });

        lstSuggestions.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() ==1){
                Item selected = lstSuggestions.getSelectionModel().getSelectedItem();
                if(selected != null){
                    fillFields(selected);
                    lstSuggestions.setVisible(false);
                }
            }
        });
    }

    private void fillFields(Item selected) {
        item = selected;
        lblName.setText(selected.getName());
        lblCategory.setText(selected.getCategory());
        lblSubCategory.setText(selected.getSubCategory());
        lblBrand.setText(selected.getBrand().getName());
        lblSupplier.setText(selected.getBrand().getSupplier().getName());
        lblSize.setText(selected.getSize());
        lblColour.setText(selected.getColor());
        lblQuantity.setText(String.valueOf(selected.getQty()));
        lblCostPrice.setText(String.valueOf(selected.getCostPrice()));
        lblSellingPrice.setText(String.valueOf(selected.getSellingPrice()));
        lblDescription.setText(selected.getDescription());
        loadImage(selected.getImgPath());

    }

    private void loadImage(String imgPath){
//        imagePath = imgPath;
        imgItem.setImage(new Image(new File(imgPath).toURI().toString()));
    }

    private void loadImage(){
        imgItem.setImage(new Image("/images/other/demo cloth image 1.jpg"));
    }

    private void loadList(){
        allItemList = itemService.getAllItems();
        filteredItemList = new FilteredList<>(allItemList, item -> true);

        lstSuggestions.setItems(filteredItemList);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadList();

        lstSuggestions.setCellFactory(itemListView -> new ListCell<Item>(){
            @Override
            protected void updateItem(Item item, boolean empty){
                super.updateItem(item, empty);
                setText((empty || item == null) ? null : item.getName());
            }
        });

        enableSearchFilter();

        enableItemSelectionEvents();
    }
}
