package service.impl;

import javafx.collections.ObservableList;
import model.dto.Brand;
import model.dto.Supplier;
import model.entity.BrandEntity;
import org.modelmapper.ModelMapper;
import repository.BrandRepository;
import repository.impl.BrandRepositoryImpl;
import service.BrandService;
import service.SupplierService;


public class BrandServiceImpl implements BrandService {
    SupplierService supplierService = new SupplierServiceImpl();
    BrandRepository brandRepository = new BrandRepositoryImpl();

    ModelMapper mapper = new ModelMapper();

    @Override
    public ObservableList<Supplier> getSuppliers() {
        return supplierService.getAll();
    }

    @Override
    public boolean save(Brand brand) {
        return brandRepository.save(mapper.map(brand, BrandEntity.class));
    }
}
