package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.WarehouseRepository;
import edu.softserve.zoo.persistence.specification.impl.WarehouseGetAllSpecification;
import edu.softserve.zoo.persistence.specification.impl.WarehouseGetByIdSpecification;
import edu.softserve.zoo.persistence.specification.impl.WarehouseGetBySupplySpecification;
import edu.softserve.zoo.service.WarehouseService;
import edu.softserve.zoo.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

/**
 * @author Abrasha on 11-May-16.
 */
@Service
public class WarehouseServiceImpl extends ServiceImpl<Warehouse> implements WarehouseService {

    @Autowired
    private WarehouseRepository warehouseRepository;

    @Override
    Repository<Warehouse> getRepository() {
        return warehouseRepository;
    }

    @Override
    public Collection<Warehouse> getAllWarehouses() {
        return warehouseRepository.find(
                new WarehouseGetAllSpecification()
        );
    }

    @Override
    public void deleteById(Integer id) {
        // TODO try catch
        final Collection<Warehouse> toRemove = warehouseRepository.find(
                new WarehouseGetByIdSpecification(id)
        );

        if (toRemove.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .build();
        }

        boolean deletionSuccess = warehouseRepository.delete(
                toRemove.stream().findAny().get()
        );


    }

    @Override
    public Warehouse getBySupply(Warehouse.Supply supply) {
        return warehouseRepository.find(
                new WarehouseGetBySupplySpecification(supply)
        ).stream().findAny().orElse(null); // TODO
    }
}
