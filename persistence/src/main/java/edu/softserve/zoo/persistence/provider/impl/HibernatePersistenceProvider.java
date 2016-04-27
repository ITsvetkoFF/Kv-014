package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.provider.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.SQLSpecification;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>Hibernate based implementation of the {@link PersistenceProvider}.</p>
 * <p>Implements CRUD operations with relational database</p>
 *
 * @param <T> the type of the domain objects which are stored. Should be properly mapped.
 * @author Bohdan Cherniakh
 */
@Component
public class HibernatePersistenceProvider<T> implements PersistenceProvider<T> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HibernatePersistenceProvider.class);
    private static final String ERROR_LOG_TEMPLATE = "An exception occurred during {} operation. Message: {}";

    private final Map<Class<?>, SpecificationProcessingStrategy<T>> supportedProcessingStrategies = new HashMap<>();

    @Autowired
    private SessionFactory sessionFactory;

    public HibernatePersistenceProvider() {
        supportedProcessingStrategies.put(CriteriaSpecification.class, new CriteriaProcessingStrategy());
        supportedProcessingStrategies.put(SQLSpecification.class, new SQLProcessingStrategy());
        supportedProcessingStrategies.put(HQLSpecification.class, new HQLProcessingStrategy());
    }

    /**
     * Saves the domain object into the relational database.
     *
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
            LOGGER.debug(ERROR_LOG_TEMPLATE, "save", ex.getMessage());
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .causedBy(ex).withMessage(ex.getMessage()).build();
        }
    }

    /**
     * Updates the tables connected with domain object in the relational database.
     *
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
            LOGGER.debug(ERROR_LOG_TEMPLATE, "update", ex.getMessage());
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .causedBy(ex).withMessage(ex.getMessage()).build();
        }
    }

    /**
     * Deletes the given entity from the persistent storage.
     *
     * @param entity domain object that should be deleted.
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public void delete(T entity) {
        try {
            Session session = getSession();
            session.delete(entity);
        } catch (HibernateException ex) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "delete", ex.getMessage());
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .causedBy(ex).withMessage(ex.getMessage()).build();
        }
    }

    /**
     * Finds the collection of domain objects in the relational database. The search criteria is defined by the
     * Specification object.
     *
     * @param specification the {@link Specification} object that describes the specification that should be processed
     *                      using appropriate {@link SpecificationProcessingStrategy}.
     * @return The {@link List} of domain objects or null if there are no objects in the database that match the query.
     * if there are no objects in the database that match the specification.
     * @see Specification
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public List<T> find(Specification<T> specification) {
        Collection<T> data = null;
        try {
            data = getProcessingStrategy(specification).process(specification);
        } catch (HibernateException ex) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "find", ex.getMessage());
            throw ApplicationException.getBuilderFor(PersistenceException.class).
                    causedBy(ex).withMessage("Can not perform find by current specification").build();
        }
        return data;
    }

    private SpecificationProcessingStrategy<T> getProcessingStrategy(Specification<T> specification) {
        Class<?> specificationType = getSpecificationType(specification);
        SpecificationProcessingStrategy processingStrategy = supportedProcessingStrategies.get(specificationType);
        if (processingStrategy == null) {
            LOGGER.debug("Unable to get an appropriate processing strategy for received specification!");
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .withMessage("Unsupported specification").build();
        }
        return processingStrategy;
    }

    private Class<?> getSpecificationType(Specification<T> specification) {
        final Class<?>[] interfaces = specification.getClass().getInterfaces();
        Class<?> specificationType = null;
        if (interfaces.length > 0) {
            specificationType = interfaces[0];
        }
        return specificationType;
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }


    private class SQLProcessingStrategy implements SpecificationProcessingStrategy<T> {
        @Override
        public List<T> process(Specification<T> specification) {
            SQLSpecification<T> sqlSpecification = (SQLSpecification<T>) specification;
            return getSession().createSQLQuery(sqlSpecification.query()).addEntity(sqlSpecification.getType()).list();
        }
    }

    private class HQLProcessingStrategy implements SpecificationProcessingStrategy<T> {
        @Override
        public List<T> process(Specification<T> specification) {
            HQLSpecification<T> hqlSpecification = (HQLSpecification<T>) specification;
            return getSession().createQuery(hqlSpecification.query()).list();
        }
    }
}