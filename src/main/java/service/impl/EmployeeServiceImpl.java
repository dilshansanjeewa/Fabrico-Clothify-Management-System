package service.impl;

import model.dto.EmployeeDto;
import model.entity.EmployeeEntity;
import repository.EmployeeRepository;
import repository.impl.EmployeeRepositoryImpl;
import service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

    EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    private EmployeeEntity createEntity(EmployeeDto employee){
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setGender(employee.getGender());
        employeeEntity.setDob(employee.getDob());
        employeeEntity.setEmail(employee.getEmail());
        employeeEntity.setPhone(employee.getPhone());
        employeeEntity.setPassword(employee.getPassword());
        employeeEntity.setRole(employee.getRole());
        employeeEntity.setProvince(employee.getProvince());
        employeeEntity.setDistrict(employee.getDistrict());
        employeeEntity.setStreetAddress(employee.getStreetAddress());
        employeeEntity.setPostalCode(employee.getPostalCode());
        employeeEntity.setImgPath(employee.getImage());
        return employeeEntity;
    }

    @Override
    public boolean saveEmployee(EmployeeDto employee) {
        return employeeRepository.save(createEntity(employee));
    }
}
