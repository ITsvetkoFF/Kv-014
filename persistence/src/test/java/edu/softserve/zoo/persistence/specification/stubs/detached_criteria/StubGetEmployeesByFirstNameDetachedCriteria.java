package edu.softserve.zoo.persistence.specification.stubs.detached_criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetEmployeesByFirstNameDetachedCriteria implements DetachedCriteriaSpecification<Employee> {
    private static final Class<Employee> ENTITY_TYPE = Employee.class;

    private final String firstName;

    public StubGetEmployeesByFirstNameDetachedCriteria(String firstName) {
        this.firstName = firstName;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(ENTITY_TYPE).add(Restrictions.eq("firstName", firstName));
    }
}
