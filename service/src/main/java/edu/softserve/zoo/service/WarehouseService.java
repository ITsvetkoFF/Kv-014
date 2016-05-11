package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Warehouse;

import java.util.Collection;

/**
 * Created by Abrasha on 11-May-16.
 */
public interface WarehouseService extends Service<Warehouse> {
    Collection<Warehouse> getAllWarehouses();

    Warehouse getBySupply(Warehouse.Supply supply);
}
