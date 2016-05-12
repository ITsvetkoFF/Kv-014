package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Warehouse;

/**
 * @author Andrii Abramov on 11-May-16.
 */
public class WarehouseGetByIdSpecification extends GetByIdSpecification<Warehouse> {
    public WarehouseGetByIdSpecification(Integer id) {
        super(Warehouse.class.getSimpleName(), id);
    }
}
