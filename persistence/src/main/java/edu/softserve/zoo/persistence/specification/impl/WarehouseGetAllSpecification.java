package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;

/**
 * @author Andrii Abramov on 11-May-16.
 */
public class WarehouseGetAllSpecification implements HQLSpecification<Warehouse> {

    private static final String WAREHOUSE_TYPE = Warehouse.class.getSimpleName();

    @Override
    public String query() {
        return "from " + WAREHOUSE_TYPE;
    }
}
