package edu.softserve.zoo.persistence.specification.hibernate;

import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;

import java.util.List;

/**
 * DetachedCriteria based {@link Specification} interface. Produces {@link DetachedCriteria}
 * for processing.
 *
 * @author Bohdan Cherniakh
 * @see DetachedCriteria
 */

public interface DetachedCriteriaSpecification<T> extends HibernateSpecification<T> {
    @Override
    DetachedCriteria query();

    @Override
    default List<T> process(SessionFactory sessionFactory) {
        return query().getExecutableCriteria(sessionFactory.getCurrentSession()).list();
    }
}
