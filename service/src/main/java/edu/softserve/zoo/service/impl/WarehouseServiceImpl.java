package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import edu.softserve.zoo.service.WarehouseService;
import edu.softserve.zoo.service.exception.WarehouseException;
import edu.softserve.zoo.util.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * {@link edu.softserve.zoo.service.Service} implementation for {@link Warehouse} entity.
 *
 * @author Andrii Abramov on 20-May-16.
 */
@Service
public class WarehouseServiceImpl extends AbstractService<Warehouse> implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    @Transactional
    public Warehouse findOne(Long id) {

        validateNullableArgument(id);
        return super.findOne(id);
    }

    @Override
    @Transactional
    public Warehouse save(Warehouse entity) {

        validateNullableArgument(entity);

        boolean warehouseOverflow = entity.getAmount() > entity.getMaxCapacity();
        Validator.isTrue(!warehouseOverflow,
                ApplicationException.getBuilderFor(WarehouseException.class)
                        .forReason(WarehouseException.Reason.AMOUNT_GREATER_THAN_CAPACITY)
                        .withMessage("Warehouse overflow")
                        .build()
        );

        return super.save(entity);
    }

    @Override
    @Transactional
    public void delete(Long id) {

        validateNullableArgument(id);
        super.delete(id);
    }


    @Override
    @Transactional
    public Warehouse update(Warehouse entity) {

        validateNullableArgument(entity);

        boolean warehouseOverflow = entity.getAmount() > entity.getMaxCapacity();
        Validator.isTrue(!warehouseOverflow,
                ApplicationException.getBuilderFor(WarehouseException.class)
                        .forReason(WarehouseException.Reason.AMOUNT_GREATER_THAN_CAPACITY)
                        .withMessage("Warehouse overflow")
                        .build()
        );

        return super.update(entity);
    }

    @Override
    Repository<Warehouse> getRepository() {
        return warehouseRepository;
    }

    @Override
    Class<Warehouse> getType() {
        return Warehouse.class;
    }
}
