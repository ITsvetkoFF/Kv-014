package edu.softserve.zoo.persistence.specification.hibernate.impl.employee;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ilya Doroshenko
 */
public class GetEmployeeByEmailSpecification implements DetachedCriteriaSpecification<Employee> {
    private static final String EMPLOYEE_EMAIL = "email";
    private String email;

    public GetEmployeeByEmailSpecification(String email) {
        this.email = email;
    }

    @Override
    public DetachedCriteria query() {
        Validator.notNull(email, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_EMAIL)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null email")
                .build());
        return DetachedCriteria.forClass(Employee.class).add(Restrictions.eq(EMPLOYEE_EMAIL, email));
    }
}

