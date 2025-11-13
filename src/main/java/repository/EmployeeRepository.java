package repository;

import model.entity.EmployeeEntity;

public interface EmployeeRepository {
    boolean save(EmployeeEntity employeeEntity);
}
