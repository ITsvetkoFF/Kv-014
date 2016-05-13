package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.hibernate.type.Type;

import java.util.List;

/**
 * Plain SQL based specification. Produces SQL query for processing.
 *
 * @author Bohdan Cherniakh
 */
public interface SQLSpecification<T> extends Specification<T> {

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
    List<ImmutablePair<String, Type>> scalarValues();
}
