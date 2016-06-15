package edu.softserve.zoo.persistence.repository.impl;

import edu.softserve.zoo.core.model.Warehouse;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>Implementation of the {@link WarehouseRepository} specific for {@link Warehouse} domain objects</p>
 *
 * @author Bohdan Cherniakh
 */
@Repository
public class WarehouseRepositoryImpl extends AbstractRepository<Warehouse> implements WarehouseRepository {
}
