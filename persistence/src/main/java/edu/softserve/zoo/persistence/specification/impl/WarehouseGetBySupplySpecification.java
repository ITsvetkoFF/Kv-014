package edu.softserve.zoo.persistence.specification.impl;

import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.persistence.specification.hibernate.HQLSpecification;
import org.springframework.util.StringUtils;

/**
 * @author Abrasha on 11-May-16.
 */
public class WarehouseGetBySupplySpecification implements HQLSpecification<Warehouse> {

    private static final String WAREHOUSE_TYPE = Warehouse.class.getSimpleName();
    private static final String SUPPLY_PROPERTY_TYPE = StringUtils.uncapitalize(Warehouse.Supply.class.getSimpleName());

    private final Warehouse.Supply FOR_SUPPLY;

    public WarehouseGetBySupplySpecification(Warehouse.Supply FOR_SUPPLY) {
        this.FOR_SUPPLY = FOR_SUPPLY;
    }

    @Override
    public String query() {
        return String.format("from %s w where w.%s = %s", WAREHOUSE_TYPE, SUPPLY_PROPERTY_TYPE, FOR_SUPPLY);
    }
}
