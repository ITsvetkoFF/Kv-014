package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.criterion.Criterion;

/**
 * Criterion based specification interface. Produces {@link Criterion} for processing
 * by {@link org.hibernate.Criteria}.
 *
 * @author Bohdan Cherniakh
 * @see org.hibernate.Criteria
 * @see Criterion
 */
public interface CriteriaSpecification<T> extends Specification<T> {

    /**
     * Returns the type of a domain object.
     *
     * @return the type of a domain object to query.
     */
    Class<T> getType();

    /**
     * Returns the query restrictions.
     *
     * @return {@link Criterion} based query restrictions.
     */
    @Override
    Criterion query();
}
