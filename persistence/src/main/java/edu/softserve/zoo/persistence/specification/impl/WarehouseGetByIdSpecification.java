package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Andrii Abramov on 11-May-16.
 */
public class WarehouseGetByIdSpecification implements HQLSpecification<Warehouse> {

    private static final String WAREHOUSE_TYPE = Warehouse.class.getSimpleName();
    private int idToRemove;

    public WarehouseGetByIdSpecification(Integer idToRemove) {
        this.idToRemove = idToRemove;
    }


    @Override
    public String query() {
        return String.format("from %s w where w.id = %d", WAREHOUSE_TYPE, idToRemove);
    }
}
