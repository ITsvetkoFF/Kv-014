package edu.softserve.zoo.persistence.specification.hibernate;

import org.hibernate.SessionFactory;

import java.util.List;

/**
 * HQL based specification. Produces HQL statement for processor.
 *
 * @author Bohdan Cherniakh
 */
public interface HQLSpecification<T> extends HibernateSpecification<T> {

    /**
     * Returns valid HQL query as a string.
     *
     * @return string representation of an HQL query.
     */
    @Override
    String query();

    @Override
    default List<T> process(SessionFactory sessionFactory) {
        return sessionFactory.getCurrentSession().createQuery(query()).list();
    }
}
