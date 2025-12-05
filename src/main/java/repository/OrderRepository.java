package repository;

import model.entity.OrderEntity;

import java.util.List;

public interface OrderRepository {
    Long getLastId();
    boolean savOrder(OrderEntity orderEntity);
    List<OrderEntity> getAllOrders();
    OrderEntity getOrder(String visibleId);
}
