package edu.softserve.zoo.persistence.specification.stubs.hql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByNameHQLStub implements HQLSpecification<Employee> {
    private static final String HQL_QUERY = "from Employee where firstName = '";
    private String name;

    public GetEmployeeByNameHQLStub(String name) {
        this.name = name;
    }

    @Override
    public String query() {
        return "from Employee where firstName = '" + name + "'";
    }
}
