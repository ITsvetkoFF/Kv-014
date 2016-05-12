package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Role;

import java.util.Collection;
import java.util.List;

/**
 * @author Julia Siroshtan
 */
public interface EmployeeService extends Service<Employee> {
    Collection<Employee> getAll();
    Employee findOne(Integer id);
}
