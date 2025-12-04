package controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import model.dto.Item;

import java.io.File;

public class ItemCardViewFormController{

    private Item item;

    @FXML
    private ImageView imgItem;

    @FXML
    private Label lblBrand;

    @FXML
    private Label lblCategory;

    @FXML
    private Label lblName;

    @FXML
    private Label lblPrice;

    @FXML
    private Label lblStock;

    @FXML
    private Label lblSubCategory;

    @FXML
    void ItemCardMouseClickedOnAction(MouseEvent event) {

    }

    public void setCardDate(Item item){
        this.item = item;

        imgItem.setImage(new Image(new File(item.getImgPath()).toURI().toString()));
        lblName.setText(item.getName());
        lblCategory.setText(item.getCategory());
        lblSubCategory.setText(item.getSubCategory());
        lblBrand.setText(item.getBrand().getName());

        if(item.getQty()>10){
            lblStock.setTextFill(Color.GREEN);
            lblStock.setText("In Stock");
        } else if (item.getQty()>0) {
            lblStock.setTextFill(Color.ORANGE);
            lblStock.setText("Low Stock");
        } else {
            lblStock.setTextFill(Color.RED);
            lblStock.setText("Out of Stock");
        }

        lblPrice.setText("Rs."+item.getSellingPrice()+"0");
    }

}
