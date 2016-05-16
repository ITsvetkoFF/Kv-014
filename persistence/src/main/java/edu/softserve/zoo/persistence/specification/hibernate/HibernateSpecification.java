package edu.softserve.zoo.persistence.specification.hibernate;


import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.SessionFactory;

import java.util.List;

/**
 * Interface defines the {@link Specification} processing strategy for Hibernate.
 *
 * @author Vadym Golub
 */
public interface HibernateSpecification<T> extends Specification<T> {

    List<T> process(SessionFactory sessionFactory);
}