package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Role;
import edu.softserve.zoo.persistence.specification.hibernate.CriterionSpecification;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.Restrictions;

/**
 * @author Julia Siroshtan
 */
public class RoleGetAllSpecification implements CriterionSpecification<Role> {

    @Override
    public Class<Role> getType() {
        return Role.class;
    }

    @Override
    public Criterion query() {
        return Restrictions.sqlRestriction("");
    }
}
