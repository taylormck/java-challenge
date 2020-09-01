package jp.co.axa.apidemo.services;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;
import jp.co.axa.apidemo.specifications.EmployeeSpecifications;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService{

    @Autowired
    private EmployeeRepository employeeRepository;

    public void setEmployeeRepository(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    public List<Employee> retrieveEmployees() {
        return employeeRepository.findAll();
    }

    public Employee getEmployee(Long employeeId) throws NoSuchElementException {
        Optional<Employee> optEmp = employeeRepository.findById(employeeId);

        return optEmp.get();
    }

    public List<Employee> getEmployeesByName(String employeeName) {
        return employeeRepository.findAll(
            EmployeeSpecifications.getEmployeesByName(employeeName)
        );
    }

    public List<Employee> getEmployeesByDepartment(String department) {
        return employeeRepository.findAll(
            EmployeeSpecifications.getEmployeesByDepartment(department)
        );
    }

    public void saveEmployee(Employee employee){
        employeeRepository.save(employee);
    }

    public void deleteEmployee(Long employeeId){
        employeeRepository.deleteById(employeeId);
    }

    public void updateEmployee(Employee employee) {
        employeeRepository.save(employee);
    }
}