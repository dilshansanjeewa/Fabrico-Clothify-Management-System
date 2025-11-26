package controller;

import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
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

public class UpdateItemFormController implements Initializable {

    private final ItemService itemService = new ItemServiceImpl();

    private Long id;
    private String imagePath;

    private FileChooser fileChooser;
    private File itemImagefile ;
    private File selectedImage ;
    private ObservableList<Item> allItemList;
    private FilteredList<Item> filteredItemList;

    private final String itemImageDir = "item_images/";

    private Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    Alert successAlert = new Alert(Alert.AlertType.INFORMATION);

    Stage popupStage = new Stage();
    FXMLLoader loader;
    Parent root;

    @FXML
    private JFXButton btnNewBrand;

    @FXML
    private JFXButton btnClear;

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
    private ListView<Item> lstSuggesions;

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
    private TextField txtSearch;

    @FXML
    private TextField txtSellingPrice;

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
    void btnClearOnAction(ActionEvent event) {
        clear();
    }

    @FXML
    void btnSaveOnAction(ActionEvent event) {
        if (checkInputField()){
            Item item = getItem();

            if(item != null){
                if(itemService.update(item)){
                    showSuccessAlert("Updated Successfully", "The item has been updated.");
                    clear();
                } else {
                    showErrorAlert("The item has not been updated.");
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

    @FXML
    void txtSearchOnAction(ActionEvent event) {

    }

    private Item getItem(){
        Item item = new Item();

        try {
            item.setId(id);
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
            if(selectedImage == null){
                item.setImgPath(imagePath);
            }else {
                item.setImgPath(saveItemImage());
            }

            return item;
        }catch (NumberFormatException e){
            showErrorAlert("Please enter valid inputs");
        }
        return null;
    }

    private String saveItemImage(){
        try {
            String uniqueName = System.currentTimeMillis() + "_updated_" + selectedImage.getName();
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

    private void clear(){
        id = null;
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
        loadImage();
        imagePath = null;
        selectedImage = null;
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

        } else {
            return true;
        }

    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setTitle("INPUT ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.show();
    }

    private void showSuccessAlert(String headerText, String contentText){
        successAlert.setTitle("Success");
        successAlert.setHeaderText(headerText);
        successAlert.setContentText(contentText);
        successAlert.showAndWait();
    }

    private void enableSearchFilter(){
        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            String keyWord = newValue.toLowerCase();

            filteredItemList.setPredicate(item -> item.getName().toLowerCase().contains(keyWord));
            lstSuggesions.setVisible(!keyWord.isEmpty() && !filteredItemList.isEmpty());
        });
    }

    private void enableItemSelectionEvents(){
        txtSearch.setOnAction(actionEvent -> {
            Item selected = lstSuggesions.getSelectionModel().getSelectedItem();

            if(selected == null && filteredItemList.isEmpty()){
                selected = filteredItemList.get(0);
            }
            if (selected != null) {
                fillFields(selected);
                lstSuggesions.setVisible(false);
            }
        });

        lstSuggesions.setOnMouseClicked(mouseEvent -> {
            if(mouseEvent.getClickCount() ==1){
                Item selected = lstSuggesions.getSelectionModel().getSelectedItem();
                if(selected != null){
                    fillFields(selected);
                    lstSuggesions.setVisible(false);
                }
            }
        });
    }

    private void fillFields(Item selected) {
        id = selected.getId();
        txtName.setText(selected.getName());
        comboCategory.setValue(selected.getCategory());
        comboSubCategory.setValue(selected.getSubCategory());
        comboBrand.setValue(selected.getBrand());
        comboSize.setValue(selected.getSize());
        txtColor.setText(selected.getColor());
        txtQty.setText(String.valueOf(selected.getQty()));
        txtCostPrice.setText(String.valueOf(selected.getCostPrice()));
        txtSellingPrice.setText(String.valueOf(selected.getSellingPrice()));
        txtDescription.setText(selected.getDescription());
        loadImage(selected.getImgPath());

    }

    private void loadImage(String imgPath){
        imagePath = imgPath;
        imgItem.setImage(new Image(new File(imgPath).toURI().toString()));
    }

    private void loadImage(){
        imgItem.setImage(new Image("/images/other/demo cloth image 1.jpg"));
    }

    private void loadItemImage(Image image) {
        imgItem.setImage(image);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        //----- Initialize File Chooser -----//
        fileChooser = new FileChooser();
        fileChooser.setTitle("Select Item Image");

        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("Image Files", "*.png", "*.jpg", "*.jpeg")
        );

        loadImage();

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

        //-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------

        allItemList = itemService.getAllItems();
        filteredItemList = new FilteredList<>(allItemList, item -> true);

        lstSuggesions.setItems(filteredItemList);

        lstSuggesions.setCellFactory(itemListView -> new ListCell<Item>(){
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
