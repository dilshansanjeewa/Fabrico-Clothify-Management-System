package service;

import javafx.collections.ObservableList;
import model.dto.Employee;

public interface EmployeeService {
    boolean saveEmployee(Employee employee);
    String getNewVisibleId();
    ObservableList<Employee> getAllEmployees();
    boolean updateEmployee(Employee employee);
}
