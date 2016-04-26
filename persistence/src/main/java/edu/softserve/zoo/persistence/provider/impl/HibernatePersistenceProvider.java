package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.persistence.exception.PersistenceException;

import java.util.Collection;
import java.util.List;

import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.specification.Specification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>Hibernate based implementation of the {@link PersistenceProvider}.</p>
 * <p>Implements CRUD operations with relational database</p>
 *
 * @author Bohdan Cherniakh
 * @param <T> the type of the domain objects which are stored. Should be properly mapped.
 */
@Component
public class HibernatePersistenceProvider<T> implements PersistenceProvider<T> {

    @Autowired
    private SessionFactory sessionFactory;

    /**
     * Saves the domain object into the relational database.
     * @param entity - an object that should be saved.
     * @return saved entity with generated identifier.
     */

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T save(T entity) {
        try {
            Session session = getSession();
            session.save(entity);
            return entity;
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
            //TODO add logging properly (after issue #42)
        }
    }

    /**
     * Updates the tables connected with domain object in the relational database.
     * @param entity - the domain object that should be updated.
     * @return updated entity.
     */

    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public T update(T entity) {
        try {
            Session session = getSession();
            session.update(entity);
            return entity;
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
            //TODO add logging properly (after issue #42)
        }
    }

    /**
     * Deletes the given entity from the persistent storage.
     * @param entity domain object that should be deleted.
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(T entity) {
        try {
            Session session = getSession();
            session.delete(entity);
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
            //TODO add logging properly (after issue #42)
        }
    }

    /**
     * Finds the collection of domain objects in the relational database. The search criteria is defined by the
     * Specification object.
     * @param specification the specification object that describes the query that should be performed.
     * @return The collection of domain objects or null if there are no objects in the database that match the query.
     * @see Specification
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true )
    public Collection<T> find(Specification<T> specification) {
        List<T> data = null;
        try {
            Session session = getSession();
            //TODO -clarify the implementation according to issue 47
            String hqlQuery = (String) specification.query();
            data = session.createQuery(hqlQuery).list();
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
            //TODO add logging properly (after issue #42)
        }
        return data;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}
