package edu.softserve.zoo.persistence.specification.stubs.sql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class GetAllEmployeesSQLStub implements SQLSpecification<Employee> {
    private static final Class<Employee> EMPLOYEE_TYPE = Employee.class;

    @Override
    public Class<Employee> getType() {
        return EMPLOYEE_TYPE;
    }

    @Override
    public String query() {
        return "SELECT * FROM employees";
    }
}
