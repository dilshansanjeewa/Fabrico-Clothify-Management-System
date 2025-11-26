package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Brand;
import model.dto.Supplier;
import model.entity.BrandEntity;
import org.modelmapper.ModelMapper;
import repository.BrandRepository;
import repository.impl.BrandRepositoryImpl;
import service.BrandService;
import service.SupplierService;

import java.util.ArrayList;
import java.util.List;


public class BrandServiceImpl implements BrandService {
    SupplierService supplierService = new SupplierServiceImpl();
    BrandRepository brandRepository = new BrandRepositoryImpl();

    List<BrandEntity> brandEntityList = new ArrayList<>();
    ObservableList<Brand> brandList = FXCollections.observableArrayList();

    ModelMapper mapper = new ModelMapper();

    @Override
    public ObservableList<Supplier> getSuppliers() {
        return supplierService.getAll();
    }

    @Override
    public boolean save(Brand brand) {
        return brandRepository.save(mapper.map(brand, BrandEntity.class));
    }

    @Override
    public ObservableList<Brand> getAllBrands() {
        return getBrandList(getBrandEntityList());
    }

    private ObservableList<Brand> getBrandList(List<BrandEntity> brandEntityList) {
        if(! brandList.isEmpty()) {
            brandList.clear();
        }

        for (BrandEntity entity : brandEntityList){
            brandList.add(mapper.map(entity, Brand.class));
        }
        return brandList;
    }

    private List<BrandEntity> getBrandEntityList(){
        if(! brandEntityList.isEmpty()){
            brandEntityList.clear();
        }
        brandEntityList = brandRepository.getAll();

        return brandEntityList;
    }
}
