package repository;

import model.entity.AdminEntity;

import java.util.List;

public interface SignInRepository {
    List<AdminEntity> getAll();
}
