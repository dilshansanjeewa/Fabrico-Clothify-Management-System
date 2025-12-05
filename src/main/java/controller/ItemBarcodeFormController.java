package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;
import util.BarcodeUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class ItemBarcodeFormController implements Initializable {

    private final ItemService itemService = new ItemServiceImpl();

    private ObservableList<Item> allItemList;
    private Item selectedItem;
    private String latestBarcodePath;

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

    @FXML
    private JFXButton btnCancel;

    @FXML
    private JFXButton btnGenerate;

    @FXML
    private JFXButton btnPrint;

    @FXML
    private ImageView imgBarcode;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private ListView<Item> lstSuggestions;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnCancelOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnGenerateOnAction(ActionEvent event) {
        String barcodePath = BarcodeUtil.generateBarcodeWithText("ITM" + selectedItem.getId());
        loadBarcodeImage(barcodePath);
    }

    @FXML
    void btnPrintOnAction(ActionEvent event) {
        try {
            boolean isPrinted = BarcodeUtil.printBarcode(latestBarcodePath);

            if (isPrinted){
                showSuccessAlert("Barcode has been printed...");

            } else {
                showErrorAlert("Barcode printing failed...");
            }

        } catch (FileNotFoundException e) {
            showErrorAlert("Invalid barcode file location");

        } catch (NullPointerException e){
            showErrorAlert("Please generate barcode");
        }
    }

    private void clear(){
        loadImages();

        selectedItem = null;
        lblName.setText("N/A");
        lblBrand.setText("N/A");
        lblPrice.setText("Rs.00.00");

        latestBarcodePath = null;
    }

    private void showSuccessAlert(String successMessage){
        successAlert.setTitle("Success");
        successAlert.setContentText(successMessage);
        successAlert.showAndWait();
    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    private void filterItems(String keyWord) {
        if (keyWord == null || keyWord.trim().isEmpty()) {
            lstSuggestions.setVisible(false);
            lstSuggestions.getItems().clear();
            return;
        }

        List<Item> result = allItemList.stream().filter(item -> item.getName().toLowerCase().contains(keyWord.toLowerCase())).toList();

        lstSuggestions.getItems().setAll(result);

        lstSuggestions.setVisible(!result.isEmpty());
    }

    private void fillImageCard(Item item) {
        selectedItem = item;

        loadItemImage(item.getImgPath());
        lblName.setText(item.getName());
        lblBrand.setText(item.getBrand().getName());
        lblPrice.setText("Rs." + item.getSellingPrice() + "0");
    }

    private void loadImages(){
        imgItem.setImage(new Image("images/other/demo cloth image 1.jpg"));
        imgBarcode.setImage(new Image("images/other/BARCODE.png"));
    }

    private void loadItemImage(String path){
        imgItem.setImage(new Image(new File(path).toURI().toString()));
    }

    private void loadBarcodeImage(String path){
        latestBarcodePath = path;
        imgBarcode.setImage(new Image(new File(path).toURI().toString()));
    }

    private void loadList() {
        allItemList = itemService.getAllItems();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        loadImages();
        loadList();
        //----- item appear method in list view -----

        lstSuggestions.setCellFactory(listView -> new ListCell<Item>(){
            @Override
            protected void updateItem(Item item, boolean empty) {
                super.updateItem(item, empty);
                if (empty || item == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        });

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterItems(newValue);
        });

        //----- real time filtering -----
        txtSearch.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!lstSuggestions.getItems().isEmpty()) {
                        Item item = lstSuggestions.getItems().get(0);
                        fillImageCard(item);
                        lstSuggestions.setVisible(false);
                    }
                }
            }
        });

        lstSuggestions.setOnMouseClicked(mouseEvent -> {
            Item selected = lstSuggestions.getSelectionModel().getSelectedItem();
            if(selected != null){
                fillImageCard(selected);
                lstSuggestions.setVisible(false);
            }
        });
    }

}
