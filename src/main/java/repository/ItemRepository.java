package repository;

import model.entity.ItemEntity;
import model.entity.OrderDetailEntity;
import org.hibernate.Session;

import java.util.List;

public interface ItemRepository {
    boolean save(ItemEntity item);
    List<ItemEntity> getAllItems();
    boolean update(ItemEntity itemEntity);
    boolean delete(ItemEntity itemEntity);
    void updateQty(Session session, OrderDetailEntity orderDetailEntity)throws Exception;
    ItemEntity getItem(Long id);
}
