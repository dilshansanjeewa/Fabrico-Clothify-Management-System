package controller;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import model.dto.Employee;
import model.dto.Order;
import model.dto.OrderDetail;
import service.OrderService;
import service.impl.OrderServiceImpl;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class OrderFormController implements Initializable {

    private final OrderService orderService = new OrderServiceImpl();

    private ObservableList<OrderDetail> detailList;

    private ObservableList<Order> orderList;

    @FXML
    private TableColumn<?, ?> colCode;

    @FXML
    private TableColumn<?, ?> colDate;

    @FXML
    private TableColumn<?, ?> colTotal;

    @FXML
    private BorderPane itemManagementBorderPane;

    @FXML
    private Label lblCode;

    @FXML
    private Label lblDate;

    @FXML
    private Label lblTotal;

    @FXML
    private ListView<Order> lstSuggestions;

    @FXML
    private ListView<OrderDetail> lstDetails;

    @FXML
    private TableView<Order> tblOrders;

    @FXML
    private TextField txtSearch;

    private void searchDetails(Order newValue){
        detailList = orderService.getOrderDetails(newValue.getOrderCode());

        lblCode.setText(newValue.getOrderCode());
        lblDate.setText(newValue.getOrderDate().toString());
        lblTotal.setText("Rs. " + newValue.getTotalAmount() + "0");

        lstDetails.setItems(detailList);
    }

    private void filterOrders(String keyWord) {
        if (keyWord == null || keyWord.trim().isEmpty()) {
            lstSuggestions.setVisible(false);
            lstSuggestions.getItems().clear();
            return;
        }

        List<Order> result = orderList.stream()
                .filter(ord ->
                        ord.getOrderCode().toLowerCase().contains(keyWord.toLowerCase())
                )
                .toList();

        lstSuggestions.getItems().setAll(result);

        lstSuggestions.setVisible(!result.isEmpty());
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colCode.setCellValueFactory(new PropertyValueFactory<>("orderCode"));
        colDate.setCellValueFactory(new PropertyValueFactory<>("orderDate"));
        colTotal.setCellValueFactory(new PropertyValueFactory<>("totalAmount"));

        orderList = orderService.getAllOrders();
        tblOrders.setItems(orderList);

        lstDetails.setCellFactory(listView ->new ListCell<>(){
            @Override
            protected void updateItem( OrderDetail detail, boolean empty) {
                super.updateItem(detail, empty);
                if (empty || detail == null) {
                    setText(null);
                } else {
                    setText(detail.getItemName() + " | QTY- " +
                            detail.getItemQty() + " | Price- " +
                            detail.getUnitPrice() + " | Total- " +
                            detail.getLineTotal());
                }
            }
        });

        lstSuggestions.setCellFactory(orderListView -> new ListCell<>(){
            protected void updateItem(Order ord, boolean empty){
                super.updateItem(ord, empty);

                if(empty || ord == null) setText(null);
                else setText(ord.getOrderCode());

            }
        });

        tblOrders.getSelectionModel().selectedItemProperty().addListener((observableValue, oldValue, newValue) -> {
            if(newValue != null){
                searchDetails(newValue);
            }
        } );

        txtSearch.textProperty().addListener((observableValue, oldValue, newValue) -> {
            filterOrders(newValue);
        });

        txtSearch.setOnKeyPressed(keyEvent -> {
            switch (keyEvent.getCode()) {
                case ENTER -> {
                    if (!lstSuggestions.getItems().isEmpty()) {
                        Order ord = lstSuggestions.getItems().get(0);
                        searchDetails(ord);
                        lstSuggestions.setVisible(false);
                    }
                }
            }
        });

        lstSuggestions.setOnMouseClicked(mouseEvent -> {
            Order selected = lstSuggestions.getSelectionModel().getSelectedItem();
            if(selected != null){
                searchDetails(selected);
                lstSuggestions.setVisible(false);
            }
        });
    }
}
