package service;

import javafx.collections.ObservableList;
import model.dto.Supplier;

import java.util.List;

public interface SupplierService {
    boolean save(Supplier supplier);
    ObservableList<Supplier> getAll();
    boolean update(Supplier supplier);
    boolean delete(Supplier supplier);
    List<Supplier> getSupplierList();
}
