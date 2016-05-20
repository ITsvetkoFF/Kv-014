package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import edu.softserve.zoo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * {@link edu.softserve.zoo.service.Service} implementation
 * for {@link Warehouse} entity.
 *
 * @author Andrii Abramov on 20-May-16.
 */
@Service
public class WarehouseServiceImpl extends AbstractService<Warehouse> implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    Repository<Warehouse> getRepository() {
        return warehouseRepository;
    }
}
