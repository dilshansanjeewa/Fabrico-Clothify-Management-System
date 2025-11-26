package repository;

import model.entity.ItemEntity;

import java.util.List;

public interface ItemRepository {
    boolean save(ItemEntity item);
    List<ItemEntity> getAllItems();
    boolean update(ItemEntity itemEntity);
}
