package service;

import javafx.collections.ObservableList;
import model.dto.Brand;
import model.dto.Supplier;

public interface BrandService {
    ObservableList<Supplier> getSuppliers();
    boolean save(Brand brand);
    ObservableList<Brand> getAllBrands();
}
