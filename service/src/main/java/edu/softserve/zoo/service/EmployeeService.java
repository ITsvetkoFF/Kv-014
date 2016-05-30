package edu.softserve.zoo.service;


import edu.softserve.zoo.model.Employee;

public interface EmployeeService extends Service<Employee> {
    Employee getEmployeeByEmail(String email);
}
