package service;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Order;
import model.dto.OrderDetail;

import java.util.List;

public interface OrderService {
    String getNewOrderCode();
    boolean saveOrder(String orderCode, List<CartItem> cartItems);
    String validateQuantity(ObservableList<CartItem> cartList);
    ObservableList<Order> getAllOrders();
    ObservableList<OrderDetail> getOrderDetails(String visibleId);
}
