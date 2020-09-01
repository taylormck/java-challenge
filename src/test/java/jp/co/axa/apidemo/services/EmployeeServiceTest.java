package jp.co.axa.apidemo.services;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.domain.Specification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;

import jp.co.axa.apidemo.entities.Employee;
import jp.co.axa.apidemo.repositories.EmployeeRepository;

@WebMvcTest(EmployeeService.class)
public class EmployeeServiceTest {
  
  @Autowired
  private EmployeeService employeeService;

  @MockBean
  private EmployeeRepository employeeRepository;

  /**
   * Helper method to get a List of Employee objects.
   */
  private List<Employee> getExampleEmployees() {
    ArrayList<Employee> expected = new ArrayList<Employee>();
    String[] names = {"alex", "brittany", "charlie"};

    for (int i = 0; i < names.length; ++i) {
      Employee e = new Employee();
      e.setId(0l + i);
      e.setName(names[i]);
      e.setSalary(100000);
      e.setDepartment("R&D");

      expected.add(e);
    }

    return expected;
  }

  @BeforeEach
  public void beforeEach(){
    employeeRepository.deleteAll();
    employeeRepository.flush();
  }

  @Test
  public void retrieveEmployeesShouldReturnEmptyListOfEmployees() throws Exception {
    ArrayList<Employee> expected = new ArrayList<Employee>();
    when(employeeRepository.findAll()).thenReturn(expected);

    List<Employee> result = employeeService.retrieveEmployees();

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void retrieveEmployeesShouldReturnListOfEmployees() throws Exception {
    List<Employee> expected = this.getExampleEmployees();
    when(employeeRepository.findAll()).thenReturn(expected);

    List<Employee> result = employeeService.retrieveEmployees();

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getEmployeeShouldReturnThrowWhenNotFound() throws Exception {
    Optional<Employee> optional = Optional.empty();

    when(employeeRepository.findById(ArgumentMatchers.anyLong()))
      .thenReturn(optional);

    assertThrows(NoSuchElementException.class, () -> {
      employeeService.getEmployee(0l);
    });
  }

  @Test
  public void getEmployeeShouldReturnEmployeeWhenFound() throws Exception {
    Employee expected = getExampleEmployees().get(0);
    Optional<Employee> optional = Optional.of(expected);

    when(employeeRepository.findById(ArgumentMatchers.anyLong()))
      .thenReturn(optional);

    Employee result = employeeService.getEmployee(expected.getId());

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getEmployeesByNameShouldReturnEmptyWhenNotFound() throws Exception {
    ArrayList<Employee> expected = new ArrayList<Employee>();

    List<Employee> result = employeeService.getEmployeesByName("Jimmy");

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getEmployeesByNameShouldReturnListOfMatchingEmployees() throws Exception {
    List<Employee> employees = this.getExampleEmployees();
    List<Employee> expected = employees.subList(0, 1);

    String employeeName = employees.get(0).getName();

    // This is a little gross, but it's the cleanest way to get
    // ArgumentMatchers.any to play nice with token types like
    // Specification<Employee>.
    @SuppressWarnings({"unchecked", "rawtypes"})
    Class<Specification<Employee>> specClass = (Class<Specification<Employee>>) (Class) Specification.class;

    when(employeeRepository.findAll(ArgumentMatchers.any(specClass)))
      .thenReturn(expected);

    List<Employee> result = employeeService.getEmployeesByName(employeeName);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getEmployeesByDepartmentShouldReturnEmptyWhenNotFound() throws Exception {
    ArrayList<Employee> expected = new ArrayList<Employee>();

    List<Employee> result = employeeService.getEmployeesByDepartment("Sales");

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void getEmployeesByDepartmentShouldReturnListOfMatchingEmployees() throws Exception {
    List<Employee> expected = this.getExampleEmployees();
    String department = expected.get(0).getDepartment();

    // This is a little gross, but it's the cleanest way to get
    // ArgumentMatchers.any to play nice with token types like
    // Specification<Employee>.
    @SuppressWarnings({"unchecked", "rawtypes"})
    Class<Specification<Employee>> specClass = (Class<Specification<Employee>>) (Class) Specification.class;

    when(employeeRepository.findAll(ArgumentMatchers.any(specClass)))
      .thenReturn(expected);

    List<Employee> result = employeeService.getEmployeesByDepartment(department);

    assertThat(result).isEqualTo(expected);
  }

  @Test
  public void saveEmployeeShouldSaveEmployee() throws Exception {
    Employee expected = getExampleEmployees().get(0);

    employeeService.saveEmployee(expected);

    verify(employeeRepository).save(ArgumentMatchers.eq(expected));
  }

  @Test
  public void deleteEmployeeShouldDeleteEmployee() throws Exception {
    Employee expected = getExampleEmployees().get(0);

    employeeService.deleteEmployee(expected.getId());

    verify(employeeRepository).deleteById(ArgumentMatchers.eq(expected.getId()));
  }

  @Test
  public void updateEmployeeShouldUpdateEmployee() throws Exception {
    Employee expected = getExampleEmployees().get(0);

    employeeService.updateEmployee(expected);

    verify(employeeRepository).save(ArgumentMatchers.eq(expected));
  }
}
