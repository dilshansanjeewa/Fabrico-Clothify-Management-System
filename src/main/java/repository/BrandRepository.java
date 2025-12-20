package repository;

import model.entity.BrandEntity;

import java.util.List;

public interface BrandRepository {
    boolean save(BrandEntity brand);
    List<BrandEntity> getAll();
}
