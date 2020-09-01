package jp.co.axa.apidemo.controllers;

import static org.mockito.Mockito.when;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.ArrayList;
import java.util.NoSuchElementException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.services.EmployeeService;

@WebMvcTest(EmployeeController.class)
public class EmployeeControllerTest {

	@Autowired
  private MockMvc mockMvc;

  @MockBean
  private EmployeeService employeeService;

  String employeesPath = "/api/v1/employees";

	@Test
	public void getEmployeesShouldReturnEmptyListWhenThereAreNoRecords() throws Exception {
    ArrayList<Employee> expected = new ArrayList<Employee> ();
    when(employeeService.retrieveEmployees()).thenReturn(expected);

    JSONArray expectedJSON = new JSONArray(expected);

    this.mockMvc.perform(get(employeesPath))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().json(expectedJSON.toString()));
  } 

  @Test
  public void getEmployeesShouldReturnListOfEmployees() throws Exception {
    ArrayList<Employee> expected = new ArrayList<Employee> ();
    String[] names = {"alex", "brittany", "charlie"};

    for (int i = 0; i < names.length; ++i) {
      Employee e = new Employee();
      e.setId(0l + i);
      e.setName(names[i]);
      e.setSalary(100000);
      e.setDepartment("R&D");

      expected.add(e);
    }

    when(employeeService.retrieveEmployees()).thenReturn(expected);

    JSONArray expectedJSON = new JSONArray(expected);

    this.mockMvc.perform(get(employeesPath))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().json(expectedJSON.toString()));
  }

  @Test
  public void getEmployeesEmployeeIdShouldReturn404WhenNotFound() throws Exception {
    when(employeeService.getEmployee(ArgumentMatchers.anyLong()))
      .thenThrow(new NoSuchElementException("No such element"));

    this.mockMvc.perform(get(employeesPath + "/1"))
      .andDo(print())
      .andExpect(status().isNotFound());
  }

  @Test
  public void getEmployeesEmployeeIdShouldReturnEmployee() throws Exception {
    Employee expected = new Employee();
    expected.setId(1l);
    expected.setName("Jimmy");
    expected.setSalary(120000);
    expected.setDepartment("R&D");

    when(employeeService.getEmployee(1l)).thenReturn(expected);

    JSONObject expectedJSON = new JSONObject(expected);

    this.mockMvc.perform(get(employeesPath + "/1"))
      .andDo(print())
      .andExpect(status().isOk())
      .andExpect(content().json(expectedJSON.toString()));
  }

  @Test
  public void postEmployeesShouldSaveEmployee() throws Exception {
    Employee employee = new Employee();
    employee.setId(1l);
    employee.setName("Jimmy");
    employee.setSalary(120000);
    employee.setDepartment("R&D");

    JSONObject employeeJSON = new JSONObject(employee);
 
    this.mockMvc.perform(
      post(employeesPath)
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeJSON.toString())
      )
      .andExpect(status().isOk());

    verify(employeeService).saveEmployee(ArgumentMatchers.any(Employee.class));
  }

  @Test
  public void deleteEmployeesShouldDeleteEmployee() throws Exception {
    this.mockMvc.perform(delete(employeesPath + "/1"))
      .andExpect(status().isOk());
    verify(employeeService).deleteEmployee(1l);
  }

  @Test
  public void putEmployeesShouldReturn404WhenNotFound() throws Exception {
    Employee expected = new Employee();
    expected.setId(1l);
    expected.setName("Jimmy");
    expected.setSalary(120000);
    expected.setDepartment("R&D");

    // Do not return this employee on employeeService.getEmployee is called.
    when(employeeService.getEmployee(ArgumentMatchers.anyLong()))
      .thenReturn(null);

    JSONObject employeeJSON = new JSONObject(expected);

    this.mockMvc.perform(
      put(employeesPath + "/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeJSON.toString())
      )
      .andExpect(status().isNotFound());
  }

  @Test
  public void putEmployeesShouldUpdateEmployee() throws Exception {
    Employee expected = new Employee();
    expected.setId(1l);
    expected.setName("Jimmy");
    expected.setSalary(120000);
    expected.setDepartment("R&D");

    when(employeeService.getEmployee(ArgumentMatchers.anyLong()))
      .thenReturn(expected);

    JSONObject employeeJSON = new JSONObject(expected);
 
    this.mockMvc.perform(
      put(employeesPath + "/1")
        .contentType(MediaType.APPLICATION_JSON)
        .content(employeeJSON.toString())
      )
      .andExpect(status().isOk());

    verify(employeeService).updateEmployee(ArgumentMatchers.any(Employee.class));
  } 
}