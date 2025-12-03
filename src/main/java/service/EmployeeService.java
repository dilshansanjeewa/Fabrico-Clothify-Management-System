package service;

import model.dto.Employee;

public interface EmployeeService {
    boolean saveEmployee(Employee employee);
    String getNewVisibleId();
}
