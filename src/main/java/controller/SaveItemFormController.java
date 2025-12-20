package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import model.dto.Brand;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ResourceBundle;

public class SaveItemFormController implements Initializable {

    ItemService itemService = new ItemServiceImpl();

    private FileChooser fileChooser;
    private final String itemImageDir = "item_images/";
    private File selectedImage;
    private File itemImagefile;

    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

    Stage popupStage = new Stage();
    FXMLLoader loader;
    Parent root;

    @FXML
    private JFXButton btnClear;

    @FXML
    private JFXButton btnNewBrand;

    @FXML
    private JFXButton btnSave;

    @FXML
    private ComboBox<Brand> comboBrand;

    @FXML
    private ComboBox<String> comboCategory;

    @FXML
    private ComboBox<String> comboSize;

    @FXML
    private ComboBox<String> comboSubCategory;

    @FXML
    private ImageView imgItem;

    @FXML
    private TextField txtColor;

    @FXML
    private TextField txtCostPrice;

    @FXML
    private TextField txtDescription;

    @FXML
    private TextField txtName;

    @FXML
    private TextField txtQty;

    @FXML
    private TextField txtSellingPrice;

    @FXML
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnNewBrandOnAction(ActionEvent event) {

        try {
            loader = new FXMLLoader(getClass().getResource("/view/add_new_brand_popup_form.fxml"));
            root = loader.load();

            popupStage.setTitle("Add Brand");
            popupStage.setScene(new Scene(root));

            popupStage.initModality(Modality.APPLICATION_MODAL);
            popupStage.initOwner(btnClear.getScene().getWindow());

            popupStage.setResizable(false);
            popupStage.showAndWait();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if(checkInputField()){
            Item item = getItem();

            if(item != null){
                if(itemService.save(item)){
                    showSuccessAlert("Saved Successfully", "The item has been added to the system.");
                    clear();

                } else {
                    showErrorAlert("The item has not been added to the system.");
                }
            }
        }
    }

    @FXML
    void imgItemOnMouseClicked(MouseEvent event) {
        //----- Open the File Chooser Window -----//
        itemImagefile = fileChooser.showOpenDialog(null);

        if(itemImagefile != null){
            selectedImage = itemImagefile;
            loadItemImage(new Image(selectedImage.toURI().toString()));
        }
    }

    private void clear(){
        txtName.setText("");
        comboCategory.getSelectionModel().clearSelection();
        comboSubCategory.getSelectionModel().clearSelection();
        comboBrand.getSelectionModel().clearSelection();
        comboSize.getSelectionModel().clearSelection();
        txtColor.setText("");
        txtQty.setText("");
        txtCostPrice.setText("");
        txtSellingPrice.setText("");
        txtDescription.setText("");
        loadItemImage();
        selectedImage = null;
    }

    private Item getItem(){
        Item item = new Item();

        try {
            item.setName(txtName.getText());
            item.setCategory(comboCategory.getValue());
            item.setSubCategory(comboSubCategory.getValue());
            item.setBrand(comboBrand.getValue());
            item.setSize(comboSize.getValue());
            item.setColor(txtColor.getText());
            item.setQty(Integer.parseInt(txtQty.getText()));
            item.setCostPrice(Double.parseDouble(txtCostPrice.getText()));
            item.setSellingPrice(Double.parseDouble(txtSellingPrice.getText()));
            item.setDescription(txtDescription.getText());
            item.setImgPath(saveItemImage());

            return item;
        }catch (NumberFormatException e){
            showErrorAlert("Please enter valid inputs");
        }
        return null;
    }

    private String saveItemImage(){
        try {
            String uniqueName = System.currentTimeMillis() + "_" + selectedImage.getName();
            File destinationFile = new File(itemImageDir + uniqueName);

            Files.copy(selectedImage.toPath(), destinationFile.toPath(), StandardCopyOption.REPLACE_EXISTING);
            return destinationFile.getPath();

        } catch (IOException e) {
            return "image_null";

        }catch (NullPointerException e){
            showErrorAlert("Please choose an item image");
            return "image_null";
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

    private boolean checkInputField(){
        if(txtName.getText().trim().isEmpty()){
            showErrorAlert("Please input item name");
            return false;

        } else if (comboCategory.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Please select item category");
            return false;

        } else if (comboSubCategory.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Please select item sub Category");
            return false;

        } else if (comboBrand.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Please select item brand");
            return false;

        } else if (comboSize.getSelectionModel().getSelectedItem() == null) {
            showErrorAlert("Please select item size");
            return false;

        } else if (txtColor.getText().trim().isEmpty()) {
            showErrorAlert("Please input item color");
            return false;

        } else if (txtQty.getText().trim().isEmpty()) {
            showErrorAlert("Please input item quantity");
            return false;

        } else if (txtCostPrice.getText().trim().isEmpty()) {
            showErrorAlert("Please input cost price");
            return false;

        } else if (txtSellingPrice.getText().trim().isEmpty()) {
            showErrorAlert("Please input item selling price");
            return false;

        } else if (selectedImage == null) {
            showErrorAlert("Please select item image");
            return false;
        } else {
            return true;
        }

    }

    private void loadItemImage(){
        imgItem.setImage(new Image("/images/other/demo cloth image 1.jpg"));
    }

    private void loadItemImage(Image itemImage){
        imgItem.setImage(itemImage);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        //----- Create a Directly to Save Employee Images if not Exists -----//
        File dir = new File(itemImageDir);
        if(!dir.exists()){
            dir.mkdir();
        }

        //----- Initialize File Chooser -----//
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Item Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        ObservableList<String> categoryList = FXCollections.observableArrayList();
        categoryList.addAll("Men's Wear", "Women's Wear", "Kids Wear", "Sportswear", "Formal Wear");
        comboCategory.setItems(categoryList);

        ObservableList<String> subCategoryList = FXCollections.observableArrayList();
        subCategoryList.addAll("Casual T-Shirts", "Polo T-Shirts", "Shirts", "Denim & Trousers", "Shorts", "Blouses", "Skirts", "Saree", "Frocks", "Babywear", "School Wear", "Gym Tops", "Track Pants", "Dry-fit T-shirts");
        comboSubCategory.setItems(subCategoryList);

        ObservableList<String> sizeList = FXCollections.observableArrayList();
        sizeList.addAll("XS", "S", "M", "L", "XL", "XXL", "XXXL", "0–6M", "6–12M", "1–2Y", "2–3Y", "3–4Y", "4–5Y");
        comboSize.setItems(sizeList);

        comboBrand.setItems(itemService.getAllBrands());

        comboBrand.setConverter(new StringConverter<Brand>() {
            @Override
            public String toString(Brand brand) {
                return brand != null ? brand.getName(): "";
            }

            @Override
            public Brand fromString(String s) {
                return null;
            }
        });

        loadItemImage();
    }
}
