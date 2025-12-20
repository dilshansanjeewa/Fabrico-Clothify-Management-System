package repository;

import model.entity.SupplierEntity;

import java.util.List;

public interface SupplierRepository {
    boolean save(SupplierEntity supplierEntity);
    List<SupplierEntity> getAll();
    boolean update(SupplierEntity supplierEntity);
    boolean delete(SupplierEntity supplierEntity);
}
