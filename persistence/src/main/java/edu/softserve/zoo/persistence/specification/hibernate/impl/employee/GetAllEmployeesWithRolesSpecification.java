package edu.softserve.zoo.persistence.specification.hibernate.impl.employee;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.Criteria;
import org.hibernate.FetchMode;
import org.hibernate.criterion.DetachedCriteria;

/**
 * @author Ilya Doroshenko
 */
public class GetAllEmployeesWithRolesSpecification implements DetachedCriteriaSpecification<Employee> {

    private static final String EMPLOYEE_ROLES = "roles";

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(Employee.class)
                .setResultTransformer(Criteria.DISTINCT_ROOT_ENTITY)
                .setFetchMode(EMPLOYEE_ROLES, FetchMode.JOIN);
    }
}
