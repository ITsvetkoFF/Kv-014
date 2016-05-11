package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Abrasha on 11-May-16.
 */
public class WarehouseGetByIdSpecification implements HQLSpecification<Warehouse> {

    private static final String WAREHOUSE_TYPE = Warehouse.class.getSimpleName();
    private Integer idToRemove;

    public WarehouseGetByIdSpecification(Integer idToRemove) {
        this.idToRemove = idToRemove;
    }


    @Override
    public String query() {
        return String.format("delete from %s w where w.id = %s", WAREHOUSE_TYPE, idToRemove);
    }
}
