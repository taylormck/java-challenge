package jp.co.axa.apidemo.controllers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class EmployeeController {

  @Autowired
  private EmployeeService employeeService;

  public void setEmployeeService(EmployeeService employeeService) {
      this.employeeService = employeeService;
  }

  @GetMapping("/employees")
  public List<Employee> getEmployees() {
    List<Employee> employees = employeeService.retrieveEmployees();
      return employees;
  }

  @GetMapping("/employees/{employeeId}")
  public Employee getEmployee(@PathVariable(name="employeeId")Long employeeId) 
    throws ResponseStatusException
  {
    Employee employee = employeeService.getEmployee(employeeId);

    if (employee == null) {
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Employee not found for this id: " + employeeId
      );
    }

    return employee;
  }

  @PostMapping("/employees")
  public void saveEmployee(Employee employee){
    employeeService.saveEmployee(employee);
    System.out.println("Employee Saved Successfully");
  }

  @DeleteMapping("/employees/{employeeId}")
  public void deleteEmployee(@PathVariable(name="employeeId")Long employeeId){
    employeeService.deleteEmployee(employeeId);
    System.out.println("Employee Deleted Successfully");
  }

  @PutMapping("/employees/{employeeId}")
  public void updateEmployee(
    @RequestBody Employee employee,
    @PathVariable(name="employeeId")Long employeeId
  ){
    Employee emp = employeeService.getEmployee(employeeId);

    if(emp == null){
      throw new ResponseStatusException(
        HttpStatus.NOT_FOUND,
        "Employee not found for this id: " + employeeId
      );
    }

    employeeService.updateEmployee(employee);
  }
}
