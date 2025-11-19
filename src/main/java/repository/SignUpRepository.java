package repository;

import model.entity.AdminEntity;

public interface SignUpRepository {
    void save(AdminEntity adminEntity);
}
