package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link WarehouseRepository} specific for {@link Warehouse} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class WarehouseRepositoryImpl extends AbstractRepository<Warehouse> implements WarehouseRepository {
    @Override
    protected Class<Warehouse> getEntityType() {
        return Warehouse.class;
    }

    @Override
    public Warehouse update(Warehouse entity) {

        validateNullableArgument(entity);
        return super.update(entity);
    }

    @Override
    public boolean delete(Long id, Class<Warehouse> type) {

        validateNullableArgument(id);
        validateNullableArgument(type);
        return super.delete(id, type);
    }

    @Override
    public Warehouse save(Warehouse entity) {

        validateNullableArgument(entity);
        return super.save(entity);
    }
}
