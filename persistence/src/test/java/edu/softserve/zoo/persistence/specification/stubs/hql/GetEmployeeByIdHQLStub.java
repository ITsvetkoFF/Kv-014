package edu.softserve.zoo.persistence.specification.stubs.hql;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByIdHQLStub implements HQLSpecification<Employee> {
    private final Integer ID;
    private static final String hqlQuery = "from Employee where id='";

    public GetEmployeeByIdHQLStub(Integer ID) {
        this.ID = ID;
    }

    @Override
    public String query() {
        return new StringBuilder("from Employee where id=").append(ID).toString();
    }
}
