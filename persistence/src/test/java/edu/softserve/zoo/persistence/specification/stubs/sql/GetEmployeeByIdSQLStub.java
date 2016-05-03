package edu.softserve.zoo.persistence.specification.stubs.sql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByIdSQLStub implements SQLSpecification<Employee> {
    private static final Class<Employee> EMPLOYEE_CLASS = Employee.class;
    private final Integer id;

    public GetEmployeeByIdSQLStub(Integer id) {
        this.id = id;
    }

    @Override
    public Class<Employee> getType() {
        return EMPLOYEE_CLASS;
    }

    @Override
    public String query() {
        return "SELECT * FROM employees WHERE ID=" + id;
    }
}
