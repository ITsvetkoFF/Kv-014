package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;

/**
 * HQL based specification. Produces HQL statement for processor.
 *
 * @author Bohdan Cherniakh
 */
public interface HQLSpecification<T extends BaseEntity> extends Specification<T> {

    /**
     * Returns valid HQL query as a string.
     *
     * @return string representation of an HQL query.
     */
    @Override
    String query();
}
