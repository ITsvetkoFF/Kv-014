package edu.softserve.zoo.persistence.specification.hibernate.impl;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.DetachedCriteriaSpecification;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;

/**
 * Implements common specification for getting the only one entity of type T with specified ID
 *
 * @param <T> Specifies the entity class. Stands for compile time validating {@link Specification}
 * @author Andrii Abramov on 13-May-16.
 */
public class GetByIdSpecification<T extends BaseEntity> implements DetachedCriteriaSpecification<T> {

    private final Class<T> FOR_ENTITY;
    private final Long ID;

    /**
     * @param forEntity Entity class
     * @param id        identifier to filter entities
     */
    public GetByIdSpecification(Class<T> forEntity, Long id) {
        this.FOR_ENTITY = forEntity;
        this.ID = id;
    }

    @Override
    public DetachedCriteria query() {
        return DetachedCriteria.forClass(FOR_ENTITY)
                .add(Restrictions.idEq(ID));
    }
}
