package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Brand;
import model.dto.Item;
import model.entity.ItemEntity;
import org.modelmapper.ModelMapper;
import repository.ItemRepository;
import repository.impl.ItemRepositoryImpl;
import service.BrandService;
import service.ItemService;

import java.util.ArrayList;
import java.util.List;

public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository = new ItemRepositoryImpl();
    private final BrandService brandService = new BrandServiceImpl();

    private List<ItemEntity> allItemsEntities = new ArrayList<>();
    private ObservableList<Item> allItemList = FXCollections.observableArrayList();

    private final ModelMapper mapper = new ModelMapper();

    @Override
    public ObservableList<Brand> getAllBrands() {
        return brandService.getAllBrands();
    }

    @Override
    public boolean save(Item item) {
        return itemRepository.save(mapper.map(item, ItemEntity.class));
    }

    @Override
    public ObservableList<Item> getAllItems() {
        loadAllItems();
        return getItemList();
    }

    @Override
    public boolean update(Item item) {
        return itemRepository.update(mapper.map(item, ItemEntity.class));
    }

    @Override
    public boolean delete(Item item) {
        return itemRepository.delete(mapper.map(item, ItemEntity.class));
    }

    @Override
    public ItemEntity getItem(Long id) {
        return itemRepository.getItem(id);
    }

    @Override
    public int findLowStockedItems() {
        loadAllItems();
        int itmCount = 0;

        for (ItemEntity entity : allItemsEntities){
            if(entity.getQty() <= 10){
                itmCount++;
            }
        }
        return itmCount;
    }

    private ObservableList<Item> getItemList(){
        if(! allItemList.isEmpty()){
            allItemList.clear();
        }

        for (ItemEntity itemEntity : allItemsEntities){
            allItemList.add(mapper.map(itemEntity, Item.class));
        }

        return allItemList;
    }

    private void loadAllItems(){
        if(! allItemsEntities.isEmpty()){
            allItemsEntities.clear();
        }
        allItemsEntities = itemRepository.getAllItems();
    }
}
