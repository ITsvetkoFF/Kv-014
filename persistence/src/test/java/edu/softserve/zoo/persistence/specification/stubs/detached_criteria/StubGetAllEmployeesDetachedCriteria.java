package edu.softserve.zoo.persistence.specification.stubs.detached_criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllEmployeesDetachedCriteria implements DetachedCriteriaSpecification<Employee> {
    private static final Class<Employee> ENTITY_TYPE = Employee.class;

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(ENTITY_TYPE);
    }
}
