package service.impl;

import model.dto.Employee;
import model.entity.EmployeeEntity;
import org.modelmapper.ModelMapper;
import repository.EmployeeRepository;
import repository.impl.EmployeeRepositoryImpl;
import service.EmployeeService;

public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository employeeRepository = new EmployeeRepositoryImpl();

    private ModelMapper mapper = new ModelMapper();

    private EmployeeEntity createEntity(Employee employee){
        EmployeeEntity employeeEntity = new EmployeeEntity();

        employeeEntity.setFirstName(employee.getFirstName());
        employeeEntity.setLastName(employee.getLastName());
        employeeEntity.setGender(employee.getGender());
        employeeEntity.setDob(employee.getDob());
        employeeEntity.setEmail(employee.getEmail());
        employeeEntity.setPhone(employee.getPhone());
        employeeEntity.setProvince(employee.getProvince());
        employeeEntity.setDistrict(employee.getDistrict());
        employeeEntity.setStreetAddress(employee.getStreetAddress());
        employeeEntity.setPostalCode(employee.getPostalCode());
        employeeEntity.setImgPath(employee.getImgPath());
        return employeeEntity;
    }

    @Override
    public boolean saveEmployee(Employee employee) {
        return employeeRepository.save(mapper.map(employee, EmployeeEntity.class));
    }

    @Override
    public String getNewVisibleId() {
        return createNewVisibleId();
    }

    private String createNewVisibleId() {
        EmployeeEntity last = getLast();

        if(last == null || last.getVisibleId() == null){
            return "EMP001";
        }

        return String.format("EMP%03d", Integer.parseInt(last.getVisibleId().substring(3))+1);
    }

    private EmployeeEntity getLast() {
        return employeeRepository.getLastEmployee();
    }
}
