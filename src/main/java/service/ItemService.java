package service;

import javafx.collections.ObservableList;
import model.dto.Brand;
import model.dto.Item;
import model.entity.ItemEntity;

public interface ItemService {
    ObservableList<Brand> getAllBrands();
    boolean save(Item item);
    ObservableList<Item> getAllItems();
    boolean update(Item item);
    boolean delete(Item item);
    ItemEntity getItem(Long id);
    int findLowStockedItems();
}
