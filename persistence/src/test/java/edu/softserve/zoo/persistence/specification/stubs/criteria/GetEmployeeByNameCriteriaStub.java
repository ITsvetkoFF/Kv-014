package edu.softserve.zoo.persistence.specification.stubs.criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

import java.util.Objects;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByNameCriteriaStub implements CriteriaSpecification <Employee> {
    private String firstName;

    public GetEmployeeByNameCriteriaStub(String firstName) {
        Objects.requireNonNull(firstName);
        this.firstName = firstName;
    }

    @Override
    public Class<Employee> getType() {
        return Employee.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.like("firstName", firstName);
    }
}
