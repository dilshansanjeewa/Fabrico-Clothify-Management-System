package service.impl;

import model.dto.Admin;
import model.entity.AdminEntity;
import repository.SignUpRepository;
import repository.impl.SignUpRepositoryImpl;
import service.SignUpService;

public class SignUpServiceImpl implements SignUpService {

    private SignUpRepository signUpRepository = new SignUpRepositoryImpl();

    @Override
    public void save(Admin admin) {
        signUpRepository.save(getEntity(admin));
    }

    private AdminEntity getEntity(Admin admin){
        AdminEntity adminEntity = new AdminEntity();
        adminEntity.setEmail(admin.getEmail());
        adminEntity.setPassword(admin.getPassword());
        adminEntity.setFullName(admin.getFullName());
        adminEntity.setImgPath(admin.getImgPath());

        return adminEntity;
    }
}
