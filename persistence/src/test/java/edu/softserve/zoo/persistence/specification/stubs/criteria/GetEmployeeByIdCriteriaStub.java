package edu.softserve.zoo.persistence.specification.stubs.criteria;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.CriteriaSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Bohdan Cherniakh
 */
public class GetEmployeeByIdCriteriaStub implements CriteriaSpecification<Employee> {
    private Integer id;

    public GetEmployeeByIdCriteriaStub(Integer id) {
        this.id = id;
    }

    @Override
    public Class getType() {
        return Employee.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.idEq(id);
    }
}
