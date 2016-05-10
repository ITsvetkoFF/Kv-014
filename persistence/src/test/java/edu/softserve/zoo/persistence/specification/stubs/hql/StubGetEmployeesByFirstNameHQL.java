package edu.softserve.zoo.persistence.specification.stubs.hql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetEmployeesByFirstNameHQL implements HQLSpecification<Employee> {
    private static final String ENTITY_NAME = Employee.class.getSimpleName();
    private static final String HQL_QUERY = "from " + ENTITY_NAME + " where firstName='%s'";

    private final String firstName;

    public StubGetEmployeesByFirstNameHQL(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public String query() {
        return String.format(HQL_QUERY, firstName);
    }
}
