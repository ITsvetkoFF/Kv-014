package edu.softserve.zoo.persistence.specification.stubs.criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class GetAllEmployeesCriteriaStub implements CriteriaSpecification<Employee> {

    @Override
    public Class<Employee> getType() {
        return Employee.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.isNotNull("id");
    }
}
