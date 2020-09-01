package jp.co.axa.apidemo.specifications;

import org.springframework.data.jpa.domain.Specification;

import jp.co.axa.apidemo.entities.Employee;

// Shouldn't _have_ to do this, but it makes the compiler warnings go away.
import jp.co.axa.apidemo.entities.Employee_;

public class EmployeeSpecifications {

  public static Specification<Employee> getEmployeesByName(String name) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get(Employee_.name), name);
    };
  }

  public static Specification<Employee> getEmployeesByDepartment(String department) {
    return (root, query, criteriaBuilder) -> {
      return criteriaBuilder.equal(root.get(Employee_.department), department);
    };
  }
}
