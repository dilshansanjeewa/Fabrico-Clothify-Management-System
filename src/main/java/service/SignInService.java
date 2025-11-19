package service;

import model.dto.Admin;

public interface SignInService {
    String[] IsExist(String email, String password);
}
