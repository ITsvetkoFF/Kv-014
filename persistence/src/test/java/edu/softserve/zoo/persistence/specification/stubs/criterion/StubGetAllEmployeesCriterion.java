package edu.softserve.zoo.persistence.specification.stubs.criterion;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class StubGetAllEmployeesCriterion implements CriterionSpecification<Employee> {

    private static final Class<Employee> ENTITY_TYPE = Employee.class;
    private static final Criterion QUERY = Restrictions.sqlRestriction("");

    @Override
    public Class<Employee> getType() {
        return ENTITY_TYPE;
    }

    @Override
    public Criterion query() {
        return QUERY;
    }
}
