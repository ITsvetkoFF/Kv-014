package edu.softserve.zoo.persistence.specification.stubs.detached_criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetEmployeeByIdDetachedCriteria implements DetachedCriteriaSpecification<Employee> {
    private static final Class<Employee> ENTITY_TYPE = Employee.class;

    private final Integer id;

    public StubGetEmployeeByIdDetachedCriteria(Integer id) {
        this.id = id;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(ENTITY_TYPE).add(Restrictions.idEq(id));
    }
}
