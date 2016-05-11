package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Warehouse;

import java.util.Collection;

/**
 * Created by Andrii Abramov on 11-May-16.
 */
public interface WarehouseService extends Service<Warehouse> {
    Collection<Warehouse> getAllWarehouses();

    Warehouse getBySupply(String supplyName);
}
