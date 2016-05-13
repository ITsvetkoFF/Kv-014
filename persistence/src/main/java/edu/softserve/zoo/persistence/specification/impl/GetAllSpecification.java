package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;

/**
 * Implements common specification for getting all entities of type T
 *
 * @param <T> Specifies the entity class. Stands for compile time validating {@link Specification}
 * @author Andrii Abramov on 13-May-16.
 */
public class GetAllSpecification<T> implements DetachedCriteriaSpecification<T> {

    private final Class<T> FOR_ENTITY;

    /**
     * @param forEntity Entity class
     */
    public GetAllSpecification(Class<T> forEntity) {
        this.FOR_ENTITY = forEntity;
    }

    /**
     * @return Criteria that matches all entities
     */
    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(FOR_ENTITY);
    }
}
