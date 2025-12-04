package repository;

import model.entity.OrderEntity;

public interface OrderRepository {
    Long getLastId();
    boolean savOrder(OrderEntity orderEntity);
}
