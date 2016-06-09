package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.BaseEntity;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetAllSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.exception.ServiceReason;
import edu.softserve.zoo.util.Validator;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author Vadym Holub
 */

public abstract class AbstractService<T extends BaseEntity> implements Service<T> {

    private final Class<T> type = getType();

    /**
     * {@inheritDoc}
     */
    @Transactional
    @Override
    public T findOne(Long id) {
        T result = getRepository().findOne(new GetByIdSpecification<>(type, id));
        Validator.notNull(result, ApplicationException.getBuilderFor(NotFoundException.class)
                .forReason(ServiceReason.NOT_FOUND).build());
        return result;
    }

    @Transactional
    @Override
    public List<T> findAll() {
        return getRepository().find(new GetAllSpecification<>(type));
    }

    @Transactional
    @Override
    public T save(T entity) {
        return getRepository().save(entity);
    }

    @Transactional
    @Override
    public T update(T entity) {
        return getRepository().update(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        getRepository().delete(id, type);
    }

    abstract Repository<T> getRepository();

    abstract Class<T> getType();
}
