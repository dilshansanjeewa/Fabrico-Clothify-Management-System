package controller;

import com.jfoenix.controls.JFXButton;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import model.dto.Item;

import java.io.ByteArrayInputStream;
import java.io.File;

public class PosItemCardController {

    private Item item;

    @FXML
    private JFXButton btnAddToCart;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblStock;

    @FXML
    private Spinner<?> spinnerQty;

    @FXML
    void btnAddToCartOnAction(ActionEvent event) {

    }

    public void setCardData(Item item){
        this.item = item;

        imgItem.setImage(new Image(new File(item.getImgPath()).toURI().toString()));
        lblName.setText(item.getName());
        lblCategory.setText("Category: "+item.getCategory());
        if(item.getQty() > 10){
            lblStock.setTextFill(Color.GREEN);
            lblStock.setText("In Stock- "+item.getQty());
        } else {
            lblStock.setTextFill(Color.RED);
            lblStock.setText("Low Stock- "+item.getQty());
        }
        lblPrice.setText("Rs. "+item.getSellingPrice()+"0");
    }

}
