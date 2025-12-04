package service.impl;

import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Item;
import model.entity.ItemEntity;
import model.entity.OrderDetailEntity;
import model.entity.OrderEntity;
import repository.OrderRepository;
import repository.impl.OrderRepositoryImpl;
import service.ItemService;
import service.OrderService;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class OrderServiceImpl implements OrderService {

    private OrderRepository orderRepository = new OrderRepositoryImpl();
    private ItemService itemService = new ItemServiceImpl();

    private double netTotal;

    @Override
    public String getNewOrderCode() {
        Long lastId = orderRepository.getLastId();
//        int nextId = (lastId == null) ? 1 : lastId+1;
        return String.format("ORD-%05d",((lastId == null) ? 1 : lastId+1));
    }

    @Override
    public boolean saveOrder(String orderCode, List<CartItem> cartItems) {
        OrderEntity orderEntity = getOrderEntity(orderCode, cartItems);
        return orderRepository.savOrder(orderEntity);
    }

    @Override
    public String validateQuantity(ObservableList<CartItem> cartList) {

        for (CartItem cartItem : cartList){
            ItemEntity item = itemService.getItem(cartItem.getItem().getId());

            if (item == null){
                return cartItem.getItem().getName()+" Not Found";
            }

            if(item.getQty() < cartItem.getItemQty()){
                return "Not enough stock for: " + item.getName() +
                        "\nAvailable: " + item.getQty() +
                        "\nRequested: " + cartItem.getItemQty();
            }
        }
        return "OK";
    }

    private OrderEntity getOrderEntity(String orderCode, List<CartItem> cartItems) {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setOrderCode(orderCode);
        orderEntity.setOrderDate(LocalDate.now());
        orderEntity.setOrderDetailEntities(getOrderDetailEntities(orderEntity,cartItems));
        orderEntity.setTotalAmount(netTotal);

        return orderEntity;
    }

    private List<OrderDetailEntity> getOrderDetailEntities(OrderEntity orderEntity, List<CartItem> cartItems) {
        List<OrderDetailEntity> orderDetailEntities = new ArrayList<>();
        netTotal = 0.0;

        for (CartItem cartItem : cartItems){
            orderDetailEntities.add(new OrderDetailEntity(
                    null,
                    orderEntity,
                    cartItem.getItem().getId(),
                    cartItem.getItem().getName(),
                    cartItem.getItemQty(),
                    cartItem.getItem().getSellingPrice(),
                    cartItem.getItemQty()*cartItem.getItem().getSellingPrice()
            ));

            netTotal+=cartItem.getItemQty()*cartItem.getItem().getSellingPrice();
        }

        return orderDetailEntities;
    }


}
