package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.criterion.DetachedCriteria;

/**
 * DetachedCriteria based {@link Specification} interface. Produces {@link DetachedCriteria}
 * for processing.
 * @author Bohdan Cherniakh
 * @see DetachedCriteria
 */
public interface DetachedCriteriaSpecification<T extends BaseEntity> extends Specification<T> {
    @Override
    DetachedCriteria query();
}
