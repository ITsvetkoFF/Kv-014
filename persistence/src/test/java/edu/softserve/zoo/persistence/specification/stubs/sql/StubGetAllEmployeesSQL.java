package edu.softserve.zoo.persistence.specification.stubs.sql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllEmployeesSQL implements SQLSpecification<Employee> {
    private static final Class<Employee> ENTITY_TYPE = Employee.class;
    private static final String SQL_QUERY = "SELECT * FROM zoo.employees";

    @Override
    public Class<Employee> getType() {
        return ENTITY_TYPE;
    }

    @Override
    public String query() {
        return SQL_QUERY;
    }
}
