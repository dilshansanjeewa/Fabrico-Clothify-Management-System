package service;

import javafx.collections.ObservableList;
import model.dto.CartItem;

import java.util.List;

public interface OrderService {
    String getNewOrderCode();
    boolean saveOrder(String orderCode, List<CartItem> cartItems);

    String validateQuantity(ObservableList<CartItem> cartList);
}
