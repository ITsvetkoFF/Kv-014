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
 * Gets employee with specified ID, also loads his roles.
 *
 * @author Ilya Doroshenko
 */
public class GetEmployeeByIdWithRolesSpecification implements DetachedCriteriaSpecification<Employee> {

    private static final String EMPLOYEE_ROLES = "roles";

    private final Long ID;

    public GetEmployeeByIdWithRolesSpecification(Long id) {
        Validator.notNull(id, ApplicationException.getBuilderFor(SpecificationException.class)
                .forReason(SpecificationException.Reason.NULL_ID_VALUE_IN_SPECIFICATION)
                .withMessage("cannot perform " + this.getClass().getSimpleName() + " with null id")
                .build());
        this.ID = id;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Employee.class)
                .add(Restrictions.idEq(ID))
                .setFetchMode(EMPLOYEE_ROLES, FetchMode.JOIN);
    }
}
