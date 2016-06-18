package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.provider.JdbcPersistenceProvider;
import edu.softserve.zoo.persistence.provider.PersistenceProvider;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.Specification;
import edu.softserve.zoo.persistence.specification.impl.CountSpecification;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigInteger;
import java.util.List;

/**
 * <p>Abstract implementation of the <tt>Repository</tt>. Help to implement concrete repositories for every
 * domain object. Implements routine CRUD operations.</p>
 *
 * @param <T> type of the domain object.
 * @author Bohdan Cherniakh
 */
public abstract class AbstractRepository<T extends BaseEntity> implements Repository<T> {

    /**
     * {@inheritDoc}
     */
    @Autowired
    private PersistenceProvider<T> persistenceProvider;
    @Autowired
    private JdbcPersistenceProvider jdbcPersistenceProvider;

    /**
     * {@inheritDoc}
     */
    @Override
    public T findOne(Specification<T> specification) {
        return persistenceProvider.findOne(specification);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Long count() {
        return jdbcPersistenceProvider.findOne(new CountSpecification<>(getEntityType()), BigInteger::longValue);
    }

    protected abstract Class<T> getEntityType();

    /**
     * {@inheritDoc}
     */
    @Override
    public T save(T entity) {
        return persistenceProvider.save(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean delete(Long id, Class<T> type) {
        return persistenceProvider.delete(id, type);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public T update(T entity) {
        return persistenceProvider.update(entity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<T> find(Specification<T> specification) {
        return persistenceProvider.find(specification);
    }

    /**
     * Checks if provided {@code arg} is null.
     * If so, throws {@link ValidationException} with {@link ValidationException.Reason#ARGUMENT_IS_NULL} reason.
     *
     * @param arg argument to check
     */
    protected void validateNullableArgument(Object arg) {
        Validator.notNull(arg, ApplicationException.getBuilderFor(ValidationException.class)
                .forReason(ValidationException.Reason.ARGUMENT_IS_NULL)
                .withMessage("Provided argument can't be null")
                .build());
    }
}
