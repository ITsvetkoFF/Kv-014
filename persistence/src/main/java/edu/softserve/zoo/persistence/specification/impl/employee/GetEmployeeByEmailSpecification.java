package edu.softserve.zoo.persistence.specification.impl.employee;

import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Species;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ilya Doroshenko
 */

public class GetEmployeeByEmailSpecification implements DetachedCriteriaSpecification<Employee> {
    private static final String EMPLOYEE_EMAIL = "email";//Specification.getPropertyNameForWhereClause(Employee.class, "email");
    private String email;

    public GetEmployeeByEmailSpecification(String email) {
        this.email = email;
    }

    @Override
    public DetachedCriteria query() {


        return DetachedCriteria.forClass(Employee.class).add(Restrictions.eq(EMPLOYEE_EMAIL, email));
    }
}

