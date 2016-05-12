package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Julia Siroshtan
 */
public class EmployeeGetByIdSpecification implements CriterionSpecification<Employee> {

    private Integer id;

    public EmployeeGetByIdSpecification(Integer id) {
        this.id = id;
    }

    @Override
    public Class<Employee> getType() {
        return Employee.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.idEq(id);
    }
}
