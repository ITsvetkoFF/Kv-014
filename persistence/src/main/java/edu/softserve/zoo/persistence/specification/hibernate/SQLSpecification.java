package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.core.model.BaseEntity;
import edu.softserve.zoo.persistence.specification.Specification;

/**
 * Plain SQL based specification. Produces SQL query for processing.
 *
 * @author Bohdan Cherniakh
 */
public interface SQLSpecification<T extends BaseEntity> extends Specification<T> {

    /**
     * Returns the type of a domain object.
     *
     * @return the type of a domain object to query.
     */
    Class<T> getType();

    /**
     * Returns valid SQL query as a string.
     *
     * @return string representation of an SQL query.
     */
    @Override
    String query();
}
