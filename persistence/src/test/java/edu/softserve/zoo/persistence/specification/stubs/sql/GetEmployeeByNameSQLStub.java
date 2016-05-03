package edu.softserve.zoo.persistence.specification.stubs.sql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByNameSQLStub implements SQLSpecification<Employee> {
    private static final Class<Employee> EMPLOYEE_CLASS = Employee.class;
    private final String firstName;

    public GetEmployeeByNameSQLStub(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public Class<Employee> getType() {
        return EMPLOYEE_CLASS;
    }

    @Override
    public String query() {
        return "SELECT * FROM ZOO.EMPLOYEES WHERE FIRST_NAME LIKE '" + firstName + "'";
    }
}
