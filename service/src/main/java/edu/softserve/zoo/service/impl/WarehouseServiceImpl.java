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
import edu.softserve.zoo.service.exception.BadRequestException;
import edu.softserve.zoo.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Andrii Abramov on 11-May-16.
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
    @Transactional(readOnly = true)
    public Collection<Warehouse> getAllWarehouses() {
        return warehouseRepository.find(
                new WarehouseGetAllSpecification()
        );
    }

    @Override
    @Transactional
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

        if (!deletionSuccess) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .withMessage("Error occurred while deleting entity with id: " + id)
                    .build();
        }

    }

    @Override
    @Transactional(readOnly = true)
    public Warehouse getBySupply(String supplyName) {
        Warehouse.Supply supply;
        try {
            supply = Warehouse.Supply.valueOf(supplyName.toUpperCase());
        } catch (IllegalArgumentException ex) {
            throw ApplicationException.getBuilderFor(BadRequestException.class)
                    .forReason(ExceptionReason.BAD_REQUEST)
                    .causedBy(ex)
                    .withMessage("There is no such warehouse supply: " + supplyName)
                    .build();
        }

        final Collection<Warehouse> toRemove = warehouseRepository.find(
                new WarehouseGetBySupplySpecification(supply)
        );

        if (toRemove.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .withMessage("Cannot find such supply: " + supply)
                    .build();
        }

        return toRemove.stream().findAny().get();

    }
}
