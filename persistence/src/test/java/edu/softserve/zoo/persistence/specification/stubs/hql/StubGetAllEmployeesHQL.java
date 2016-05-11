package edu.softserve.zoo.persistence.specification.stubs.hql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllEmployeesHQL implements HQLSpecification<Employee> {
    private static final String HQL_QUERY = "from Employee";

    @Override
    public String query() {
        return HQL_QUERY;
    }
}
