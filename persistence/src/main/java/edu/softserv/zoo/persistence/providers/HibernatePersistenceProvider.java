package edu.softserv.zoo.persistence.providers;

import edu.softserv.zoo.persistence.exceptions.PersistenceException;
import java.io.Serializable;
import java.util.List;

import edu.softserv.zoo.persistence.specification.Specification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

/**
 * Classname: HibernatePersistenceProvider
 * Version: 1.0
 * Created: 19.04.2016
 */
public class HibernatePersistenceProvider<E, K extends Serializable> implements PersistenceProvider<E, K> {

    private SessionFactory sessionFactory;
    private Class<E> entityType;

    @Override
    public void create(E entity) throws PersistenceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.save(entity);
            session.flush();
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    //ErrorLogging goes here
                    throw new PersistenceException(ex.getMessage(), ex.getCause());
                }
            }
        }
    }

    @Override
    public E read(K key) throws PersistenceException {
        Session session = null;
        E entity = null;
        try {
            session = sessionFactory.openSession();
            //Or session.get(...)?
            entity = (E) session.load(entityType, key);
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    //ErrorLogging goes here
                    throw new PersistenceException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return entity;
    }

    @Override
    public Iterable<E> readAll() throws PersistenceException {
        List<E> entities = null;
        Session session = null;
        try {
            session = sessionFactory.openSession();
            //Or session.get(...)?
            entities = session.createCriteria(entityType).list();
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    //ErrorLogging goes here
                    throw new PersistenceException(ex.getMessage(), ex.getCause());
                }
            }
        }
        return entities;
    }

    @Override
    public void update(E entity) throws PersistenceException{
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.update(entity);
            session.flush();
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    //ErrorLogging goes here
                    throw new PersistenceException(ex.getMessage(), ex.getCause());
                }
            }
        }
    }

    @Override
    public void delete(E entity) throws PersistenceException {
        Session session = null;
        try {
            session = sessionFactory.openSession();
            session.delete(entity);
            session.flush();
        } catch (HibernateException ex) {
            throw new PersistenceException(ex.getMessage(), ex.getCause());
        } finally {
            if (session != null) {
                try {
                    session.close();
                } catch (HibernateException ex) {
                    //ErrorLogging goes here
                    throw new PersistenceException(ex.getMessage(), ex.getCause());
                }
            }
        }
    }

    /*TODO - figure out how to use Specification/Creteria API
     */
    @Override
    public Iterable<E> query(Specification<E> specification) {
        return null;
    }
}
