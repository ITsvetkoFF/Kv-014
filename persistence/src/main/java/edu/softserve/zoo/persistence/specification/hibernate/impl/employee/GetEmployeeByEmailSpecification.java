package edu.softserve.zoo.persistence.specification.hibernate.impl.employee;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * @author Ilya Doroshenko
 */
public class GetEmployeeByEmailSpecification implements DetachedCriteriaSpecification<Employee> {
    private static final String EMPLOYEE_EMAIL = "email";
    private static final String EMPLOYEE_ROLES = "roles";
    private String email;

    public GetEmployeeByEmailSpecification(String email) {
        Validator.notNull(email, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_EMAIL)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null email")
                .build());
        this.email = email;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Employee.class)
                .setFetchMode(EMPLOYEE_ROLES, FetchMode.JOIN)
                .add(Restrictions.eq(EMPLOYEE_EMAIL, email));
    }
}

