package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.provider.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.hibernate.*;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.type.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ClassUtils;

import java.util.*;

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
        supportedProcessingStrategies.put(CriterionSpecification.class, new CriterionProcessingStrategy());
        supportedProcessingStrategies.put(SQLSpecification.class, new SQLProcessingStrategy());
        supportedProcessingStrategies.put(HQLSpecification.class, new HQLProcessingStrategy());
        supportedProcessingStrategies.put(DetachedCriteriaSpecification.class, new DetachedCriteriaProcessingStrategy());
        supportedProcessingStrategies.put(SQLScalarSpecification.class, new SQLScalarProcessingStrategy());
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
            //TODO - Add Reason when they are done
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
            //TODO - Add Reason when they are done
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
            //TODO - Add Reason when they are done
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
     * @return The collection of domain objects or empty collection
     * if there are no objects in the database that match the specification.
     * @throws PersistenceException if {@code specification} is incorrect or query errors
     * @throws NullPointerException if {@code specification} is {@code null}
     * @see Specification
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public Collection<T> find(Specification<T> specification) {
        Objects.requireNonNull(specification, "Specification can not be undefined");

        Collection<T> data = null;
        try {
            data = processSpecification(specification);
        } catch (HibernateException ex) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "find", ex.getMessage());
            //TODO - Add Reason when they are done
            throw ApplicationException.getBuilderFor(PersistenceException.class).
                    causedBy(ex).withMessage("Can not perform find by current specification").build();
        }
        return data;
    }

    private Collection<T> processSpecification(Specification<T> specification) {
        SpecificationProcessingStrategy<T> processingStrategy = getProcessingStrategy(specification);
        return processingStrategy.process(specification);
    }

    private SpecificationProcessingStrategy<T> getProcessingStrategy(Specification<T> specification) {
        Class<?> specificationType = getSpecificationType(specification);
        return supportedProcessingStrategies.get(specificationType);
    }

    private Class<?> getSpecificationType(Specification<T> specification) {

        Set<Class<?>> supportedSpecificationTypes = getSupportedSpecificationTypes(specification);

        //Should this check be in a separate method?
        //Supported specification type: there can be only one. Here we are...
        if (supportedSpecificationTypes.isEmpty()) {
            StringBuilder errorMessageBuilder = new StringBuilder("Unsupported specification: ");
            errorMessageBuilder.append(specification.getClass().getSimpleName());
            errorMessageBuilder.append(". Specification does not implement supported specification type.");

            LOGGER.debug("Unable to get an appropriate processing strategy! " + errorMessageBuilder.toString());
            //TODO - Add Reason when they are done
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .withMessage(errorMessageBuilder.toString()).build();
        } else if (supportedSpecificationTypes.size() > 1) {
            StringBuilder errorMessageBuilder = new StringBuilder("Incorrect specification: ");
            errorMessageBuilder.append(specification.getClass().getSimpleName());
            errorMessageBuilder.append(". Specification implements more than one supported specification types.");

            LOGGER.debug("Unable to get an appropriate processing strategy! " + errorMessageBuilder.toString());
            //TODO - Add Reason when they are done
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .withMessage(errorMessageBuilder.toString()).build();
        }

        return supportedSpecificationTypes.stream().findFirst().get();
    }

    private Set<Class<?>> getSupportedSpecificationTypes(Specification<T> specification) {
        Set<Class<?>> specificationInterfaces = getSpecificationInterfaces(specification);
        specificationInterfaces.retainAll(supportedProcessingStrategies.keySet());
        return specificationInterfaces;
    }

    private Set<Class<?>> getSpecificationInterfaces(Specification<T> specification) {
        //Getting interfaces of a class with superclass interfaces.
        // Or this should be implemented separately of Spring Framework?
        return ClassUtils.getAllInterfacesAsSet(specification);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }

    private class CriterionProcessingStrategy implements SpecificationProcessingStrategy<T> {
        @Override
        public List<T> process(Specification<T> specification) {
            CriterionSpecification<T> criterionSpecification = (CriterionSpecification<T>) specification;
            return getSession().createCriteria(criterionSpecification.getType()).add(criterionSpecification.query()).list();
        }
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

    private class DetachedCriteriaProcessingStrategy implements SpecificationProcessingStrategy<T> {
        @Override
        public Collection<T> process(Specification<T> specification) {
            DetachedCriteriaSpecification<T> detachedCriteriaSpecification
                    = (DetachedCriteriaSpecification<T>) specification;
            return detachedCriteriaSpecification.query().getExecutableCriteria(getSession()).list();
        }
    }
    private class SQLScalarProcessingStrategy implements SpecificationProcessingStrategy<T> {
        @Override
        public List<T> process(Specification<T> specification) {
            SQLScalarSpecification<T> sqlScalarSpecification = (SQLScalarSpecification<T>) specification;
            SQLQuery query = getSession().createSQLQuery(sqlScalarSpecification.query());
            for (Map.Entry<String, Type> entry : sqlScalarSpecification.scalarValues())
                query = query.addScalar(entry.getKey(), entry.getValue());
            return query.list();
        }
    }
}