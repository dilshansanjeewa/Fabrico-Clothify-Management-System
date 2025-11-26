package repository;

import model.entity.BrandEntity;

public interface BrandRepository {
    boolean save(BrandEntity brand);
}
