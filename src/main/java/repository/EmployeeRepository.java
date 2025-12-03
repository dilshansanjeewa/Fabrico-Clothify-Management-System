package repository;

import model.entity.EmployeeEntity;

import java.util.List;

public interface EmployeeRepository {
    boolean save(EmployeeEntity employeeEntity);
    EmployeeEntity getLastEmployee();
    List<EmployeeEntity> getAllEmployees();
    boolean updateEmployee(EmployeeEntity employeeEntity);
    boolean deleteEmployee(EmployeeEntity employeeEntity);
}
