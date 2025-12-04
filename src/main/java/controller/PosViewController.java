package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import model.dto.CartItem;
import model.dto.Item;
import service.ItemService;
import service.impl.ItemServiceImpl;
import util.BillUtil;
import util.EmailUtil;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class PosViewController implements Initializable {

    ItemService itemService = new ItemServiceImpl();

    private ObservableList<CartItem> cartList = FXCollections.observableArrayList();

    private double discountRate = 0.0;

    @FXML
    private JFXButton btnAllItems;

    @FXML
    private JFXButton btnCancelOrder;

    @FXML
    private JFXButton btnCheckout;

    @FXML
    private JFXButton btnClearSort;

    @FXML
    private JFXButton btnCustomers;

    @FXML
    private TableColumn<CartItem, Number> colDescount;

    @FXML
    private TableColumn<CartItem, String> colItem;

    @FXML
    private TableColumn<CartItem, Number> colPrice;

    @FXML
    private TableColumn<CartItem, Number> colQty;

    @FXML
    private TableColumn<CartItem, Number> colTotal;

    @FXML
    private ComboBox<?> comboBrand;

    @FXML
    private ComboBox<?> comboCategory;

    @FXML
    private ComboBox<?> comboType;

    @FXML
    private FlowPane flowProducts;

    @FXML
    private ImageView imgUser;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblDescount;

    @FXML
    private Label lblNetToal;

    @FXML
    private Label lblRole;

    @FXML
    private Label lblSortOrder;

    @FXML
    private Label lblSubTotal;

    @FXML
    private Label lblTime;

    @FXML
    private Label lblUserName;

    @FXML
    private JFXListView<?> lstSuggestions;

    @FXML
    private ScrollPane scrollProducts;

    @FXML
    private TableView<CartItem> tblSummery;

    @FXML
    private TextField txtSearch;

    @FXML
    void btnAllItemsonAction(ActionEvent event) {
        loadItemCards();
    }

    @FXML
    void btnCancelOrderonAction(ActionEvent event) {

    }

    @FXML
    void btnCheckoutonAction(ActionEvent event) {
        try {
//            File billFile = BillUtil.generateBill(System.currentTimeMillis() + "", cartList);
//            EmailUtil.sendBill("dilshansanjeewads7@gmail.com", "Fabrico e-Bill", "Dear customer, please find your bill attached.", billFile);
//            EmailUtil.sendTestEmail();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    void btnClearSortonAction(ActionEvent event) {

    }

    @FXML
    void btnCustomersonAction(ActionEvent event) {

    }

    @FXML
    void comboBrandonAction(ActionEvent event) {

    }

    @FXML
    void comboCategoryonAction(ActionEvent event) {

    }

    @FXML
    void comboTypeonAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonAction(ActionEvent event) {

    }

    @FXML
    void txtSearchonKeyReleased(KeyEvent event) {

    }

    private void calculateTotals() {

        double subTotal = cartList.stream()
                .mapToDouble(item -> item.getItem().getSellingPrice() * item.getItemQty())
                .sum();

        double discountAmount = (subTotal * discountRate) / 100;

        double netTotal = subTotal - discountAmount;

        // Display values
        lblSubTotal.setText(String.format("Rs.%.2f", subTotal));
        lblDescount.setText(String.format("Rs.%.2f", discountAmount));
        lblNetToal.setText(String.format("Rs.%.2f", netTotal));
    }

    public void addToCart(CartItem cartItem){

        // If same item exists update quantity
        for (CartItem cart : cartList) {
            if (cart.getItem().getId().equals(cartItem. getItem().getId())) {
                cart.setItemQty(cart.getItemQty() + cartItem.getItemQty());
                tblSummery.refresh();
                calculateTotals();
                return;
            }
        }

        cartList.add(cartItem);
        calculateTotals();
    }

    private void loadItemCards(){

        flowProducts.getChildren().clear();
        ObservableList<Item> allItems = itemService.getAllItems();

        for (Item item : allItems){
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/view/pos_item_card.fxml"));
                Parent itemCard = loader.load();

                PosItemCardController cardController = loader.getController();
                cardController.setCardData(item, this::addToCart);

                flowProducts.getChildren().add(itemCard);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colItem.setCellValueFactory(cellData ->
                new SimpleStringProperty(cellData.getValue().getItem().getName())
        );

        colPrice.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getItem().getSellingPrice())
        );

         colQty.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getItemQty())
        );

        colPrice.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(cellData.getValue().getItem().getSellingPrice())
        );

        colDescount.setCellValueFactory(cellData ->
                new SimpleDoubleProperty(0.00)
        );

        colTotal.setCellValueFactory(cellData -> {
            CartItem cart = cellData.getValue();
            double total = cart.getItem(). getSellingPrice() * cart.getItemQty();
            return new SimpleDoubleProperty(total);
        });

        tblSummery.setItems(cartList);
        loadItemCards();
    }
}
