package edu.softserve.zoo.persistence.specification.hibernate;

import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Plain SQL based specification. Produces SQL query for processing.
 *
 * @author Bohdan Cherniakh
 */
public interface SQLSpecification<T> extends HibernateSpecification<T> {

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

    @Override
    default List<T> process(SessionFactory sessionFactory) {
        return sessionFactory.getCurrentSession().createSQLQuery(query()).addEntity(getType()).list();
    }
}
