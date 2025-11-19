package service.impl;

import model.dto.Admin;
import model.entity.AdminEntity;
import repository.SignInRepository;
import repository.impl.SignInRepositoryImpl;
import service.SignInService;
import util.PasswordUtil;

import java.util.List;

public class SignInServiceImpl implements SignInService {

    SignInRepository signInRepository = new SignInRepositoryImpl();
    List<AdminEntity> adminEntityList;

    @Override
    public String[] IsExist(String email, String password) {
        getAll();

        for (AdminEntity adminEntity : adminEntityList){
            if(checkEmail(adminEntity.getEmail(),email) && checkPassword(adminEntity.getPassword(), password)){
                return new String[] {
                        adminEntity.getFullName(),
                        adminEntity.getImgPath()
                };
            }
        }
        return null;
    }

    private boolean checkEmail(String entityEmail, String enteredEmail){

        return enteredEmail.equals(entityEmail);
    }

    private boolean checkPassword(String entityPassword, String enteredPassword){

        return PasswordUtil.verifyPassword(enteredPassword, entityPassword);
    }

    private void getAll(){
        adminEntityList = signInRepository.getAll();
    }
}
