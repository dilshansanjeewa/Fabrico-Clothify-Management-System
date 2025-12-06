package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.CartItem;
import model.dto.Order;
import model.dto.OrderDetail;
import model.entity.ItemEntity;
import model.entity.OrderDetailEntity;
import model.entity.OrderEntity;
import org.modelmapper.ModelMapper;
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

    private ModelMapper mapper = new ModelMapper();

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

    @Override
    public ObservableList<Order> getAllOrders() {
        List<OrderEntity> allOrderEntities = getAllOrderEntities();
        ObservableList<Order> orderList = FXCollections.observableArrayList();

        for (OrderEntity entity : allOrderEntities){
            orderList.add(mapper.map(entity, Order.class));
        }

        return orderList;
    }

    @Override
    public ObservableList<OrderDetail> getOrderDetails(String visibleId) {
        OrderEntity orderEntity = findOrderEntity(visibleId);
        List<OrderDetailEntity> orderDetailEntities = orderEntity.getOrderDetailEntities();
        ObservableList<OrderDetail> orderDetails = FXCollections.observableArrayList();

        for (OrderDetailEntity entity : orderDetailEntities){
            orderDetails.add(mapper.map(entity, OrderDetail.class));
        }

        return orderDetails;
    }

    @Override
    public long getTodayOrderCount() {
        List<OrderEntity> allOrderEntities = getAllOrderEntities();
        LocalDate today = LocalDate.now();
        return allOrderEntities.stream().filter(orderEntity -> orderEntity.getOrderDate().isEqual(today)).count();
    }

    @Override
    public double getTodayRevenue() {
        List<OrderEntity> allOrderEntities = getAllOrderEntities();
        LocalDate today = LocalDate.now();

        return allOrderEntities.stream()
                .filter(orderEntity -> orderEntity.getOrderDate().isEqual(today))
                .mapToDouble(OrderEntity::getTotalAmount).sum();

    }

    @Override
    public double getThisMonthRevenue() {
        List<OrderEntity> allOrderEntities = getAllOrderEntities();
        LocalDate today = LocalDate.now();

        return allOrderEntities.stream()
                .filter(orderEntity -> orderEntity.getOrderDate().getMonth() == today.getMonth() && orderEntity.getOrderDate().getYear() == today.getYear())
                .mapToDouble(OrderEntity::getTotalAmount).sum();

    }

    private List<OrderEntity> getAllOrderEntities(){
        return orderRepository.getAllOrders();
    }

    private OrderEntity findOrderEntity(String visibleId) {
        return orderRepository.getOrder(visibleId);
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
