package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.FlowPane;
import javafx.scene.shape.Circle;
import javafx.util.Duration;
import model.dto.CartItem;
import model.dto.Item;
import service.ItemService;
import service.OrderService;
import service.impl.ItemServiceImpl;
import service.impl.OrderServiceImpl;
import util.BillUtil;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.ResourceBundle;

public class PosViewController implements Initializable {

    private final ItemService itemService = new ItemServiceImpl();
    private final OrderService orderService = new OrderServiceImpl();

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("hh:mm:ss a");

    private final DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("EEEE, MMMM dd, yyyy");

    private final ObservableList<CartItem> cartList = FXCollections.observableArrayList();

    private final double discountRate = 0.0;

    private final Alert errorAlert = new Alert(Alert.AlertType.ERROR);
    private final Alert conformationAlert = new Alert(Alert.AlertType.CONFIRMATION);

    private String orderCode;

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
    private Label lblDiscount;

    @FXML
    private Label lblNetTotal;

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
    void btnCancelOrderonAction(ActionEvent event) {
        CartItem selectedItem = tblSummery.getSelectionModel().getSelectedItem();

        if (selectedItem != null){
            if(isConformed("Remove Item", "Remove Item "+selectedItem.getItem().getName())){
                cartList.remove(selectedItem);
                calculateTotals();
            }

        }else {
            if(isConformed("Order Cancellation", "Press OK to Cancel Order")){
                clearAll();
            }
        }
    }

    @FXML
    void btnCheckoutonAction(ActionEvent event) {

        if(!cartList.isEmpty()){
            String validation = orderService.validateQuantity(cartList);

            if (validation.equals("OK")){

                if(isConformed("Place Order Now", "Press OK to Checkout and Print Bill")){
                    boolean isSaved = orderService.saveOrder(orderCode, cartList);
                    if (isSaved){
                        BillUtil.generateBill(System.currentTimeMillis()+"_"+orderCode, cartList);
                        clearAll();
                        loadNewOrderCode();
                    }else {
                        showErrorAlert("Order have not been added to the system");
                    }
                }

            }else {
                showErrorAlert(validation);
            }

        } else {
            showErrorAlert("Please add items to place order");
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

    private void clearAll(){
        cartList.clear();
        lblSubTotal.setText("Rs.00.00");
        lblDiscount.setText("Rs.00.00");
        lblNetTotal.setText("Rs.00.00");
    }

    private boolean isConformed(String headerMessage,String contentMessage){
        Optional<ButtonType> result = showConformationAlert(headerMessage, contentMessage);

        return result.isPresent() && result.get() == ButtonType.OK;
    }

    private Optional<ButtonType> showConformationAlert(String headerText, String contentText){
        conformationAlert.setTitle("Confirmation");
        conformationAlert.setHeaderText(headerText);
        conformationAlert.setContentText(contentText);

        return conformationAlert.showAndWait();
    }

    private void showErrorAlert(String errorMessage){
        errorAlert.setHeaderText("Error Alert");
        errorAlert.setHeaderText("ERROR");
        errorAlert.setContentText(errorMessage);
        errorAlert.showAndWait();
    }

    private void calculateTotals() {

        double subTotal = cartList.stream()
                .mapToDouble(item -> item.getItem().getSellingPrice() * item.getItemQty())
                .sum();

        double discountAmount = (subTotal * discountRate) / 100;

        double netTotal = subTotal - discountAmount;

        // Display values
        lblSubTotal.setText(String.format("Rs.%.2f", subTotal));
        lblDiscount.setText(String.format("Rs.%.2f", discountAmount));
        lblNetTotal.setText(String.format("Rs.%.2f", netTotal));
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

    private void setDateTime(){
        Timeline clock = new Timeline(new KeyFrame(Duration.seconds(1), e -> {
            LocalDateTime now = LocalDateTime.now();
            lblTime.setText(now.format(timeFormatter));
            lblDate.setText(now.format(dateTimeFormatter));
        }));
        clock.setCycleCount(Timeline.INDEFINITE);
        clock.play();
    }

    private void loadNewOrderCode(){
        orderCode = orderService.getNewOrderCode();
    }

    public void setDetails(String name, Image image){
        imgUser.setImage(image);
        lblUserName.setText(name);
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Circle clip = new Circle(30, 30,25);
        imgUser.setClip(clip);

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

        loadNewOrderCode();
        setDateTime();
    }
}
