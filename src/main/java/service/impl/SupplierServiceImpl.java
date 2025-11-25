package service.impl;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import model.dto.Supplier;
import model.entity.SupplierEntity;
import org.modelmapper.ModelMapper;
import repository.SupplierRepository;
import repository.impl.SupplierRepositoryImpl;
import service.SupplierService;

import java.util.ArrayList;
import java.util.List;

public class SupplierServiceImpl implements SupplierService {

    private SupplierRepository supplierRepository = new SupplierRepositoryImpl();

    private ModelMapper mapper = new ModelMapper();

    private List<SupplierEntity> supplierEntityList = new ArrayList<>();
    private ObservableList<Supplier> supplierList = FXCollections.observableArrayList();

    @Override
    public boolean save(Supplier supplier) {
        return supplierRepository.save(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public ObservableList<Supplier> getAll() {
        if(! supplierEntityList.isEmpty()){
            supplierEntityList.clear();
        }

        supplierEntityList = supplierRepository.getAll();
        return getSuppliers(supplierEntityList);
    }

    @Override
    public boolean update(Supplier supplier) {
        return supplierRepository.update(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public boolean delete(Supplier supplier) {
        return supplierRepository.delete(mapper.map(supplier, SupplierEntity.class));
    }

    @Override
    public List<Supplier> getSupplierList() {
        return supplierList;
    }

    private ObservableList<Supplier> getSuppliers(List<SupplierEntity> supplierEntityList) {
        if(! supplierList.isEmpty()) {
            supplierList.clear();
        }

        for(SupplierEntity entity : supplierEntityList) {
            supplierList.add(mapper.map(entity, Supplier.class));
        }
        return supplierList;
    }
}
