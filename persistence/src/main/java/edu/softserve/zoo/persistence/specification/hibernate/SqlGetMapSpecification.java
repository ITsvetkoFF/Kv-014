package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;

/**
 * SQL based specification. Produces SQL query for processing.
 *
 * @author Serhii Alekseichenko
 */
public interface SqlGetMapSpecification<K, V> extends Specification {

    /**
     * Returns the type of a key in map.
     *
     * @return the type of result map key.
     */
    Class<K> getKeyType();

    /**
     * Returns the type of a value in map.
     *
     * @return the type of result map value.
     */
    Class<V> getValueType();

    /**
     * Returns valid SQL query as a string.
     *
     * @return string representation of an SQL query.
     */
    @Override
    String query();
}
