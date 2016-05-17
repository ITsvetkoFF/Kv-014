package edu.softserve.zoo.persistence.provider.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.exceptions.persistence.PersistenceException;
import edu.softserve.zoo.persistence.exception.NotFoundException;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.provider.specification_processing.provider.ProcessingStrategyProvider;
import edu.softserve.zoo.persistence.provider.specification_processing.strategy.SpecificationProcessingStrategy;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.impl.GetByIdSpecification;
import edu.softserve.zoo.util.Validator;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

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
    private static final String DELETE_QUERY = "delete from %s e where e.id = %d";

    @Autowired
    private SessionFactory sessionFactory;

    @Autowired
    private ProcessingStrategyProvider<T> processingStrategyProvider;

    public HibernatePersistenceProvider() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(Long id, Class<T> type) {
        try {
            Session session = getSession();
            Criteria criteria = new GetByIdSpecification<>(type, id).query().getExecutableCriteria(session);
            T entity = (T) criteria.uniqueResult();
            Validator.notNull(entity, ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND).build());
            return entity;
        } catch (HibernateException ex) {
            LOGGER.debug(ERROR_LOG_TEMPLATE, "findOne", ex.getMessage());
            //TODO - Add Reason when they are done
            throw ApplicationException.getBuilderFor(PersistenceException.class)
                    .causedBy(ex).withMessage(ex.getMessage()).build();
        }
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
     * @param id   id of domain object that should be deleted.
     * @param type of domain object that should be deleted.
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY)
    public boolean delete(Long id, Class type) {
        try {
            return getSession().createQuery(String.format(DELETE_QUERY, type.getSimpleName(), id)).executeUpdate() == 1;
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
     * @return The {@link List} of domain objects or null if there are no objects in the database that match the query.
     * if there are no objects in the database that match the specification.
     * @throws PersistenceException if {@code specification} is incorrect or query errors
     * @throws NullPointerException if {@code specification} is {@code null}
     * @see Specification
     */
    @Override
    @Transactional(propagation = Propagation.MANDATORY, readOnly = true)
    public List<T> find(Specification<T> specification) {
        Validator.notNull(specification, ApplicationException.getBuilderFor(PersistenceException.class)
                .withMessage("Specification can not be null")
                .build());

        List<T> data = null;
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

    private List<T> processSpecification(Specification<T> specification) {
        SpecificationProcessingStrategy<T> processingStrategy = processingStrategyProvider.getStrategy(specification);
        return processingStrategy.process(specification);
    }

    private Session getSession() {
        return sessionFactory.getCurrentSession();
    }
}