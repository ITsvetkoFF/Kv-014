package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.cfg.NotYetImplementedException;
import org.hibernate.type.Type;

import java.util.Map;

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
    default Class<T> getType() {
        throw new NotYetImplementedException();
    }

    /**
     * Returns the map of scalar values.
     *
     * @return the map of scalar values.
     */
    default Map<String, Type> getScalar() {
        throw new NotYetImplementedException();
    }

    /**
     * Returns valid SQL query as a string.
     *
     * @return string representation of an SQL query.
     */
    @Override
    String query();
}
