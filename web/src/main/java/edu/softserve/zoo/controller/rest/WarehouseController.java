package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.WarehouseDto;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.AbstractRestController.API_V1;
import static edu.softserve.zoo.controller.rest.WarehouseController.WAREHOUSE_ROUTE;

/**
 * This {@link WarehouseController} stands for basic request handling relied to {@link Warehouse} and {@link Warehouse.Supply} entities.
 *
 * @author Andrii Abramov on 11-May-16.
 */
@RestController
@RequestMapping(API_V1 + WAREHOUSE_ROUTE)
public class WarehouseController extends AbstractRestController<WarehouseDto, Warehouse> {

    static final String WAREHOUSE_ROUTE = "/warehouse";

    @Autowired
    private WarehouseService warehouseService;

    public WarehouseController() {
        super(Warehouse.class, WarehouseDto.class);
    }

    /**
     * @return {@link Collection} of all present Warehouse (Supply) entities.
     */
    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<WarehouseDto> getWarehouseData() {
        return warehouseService.getAllWarehouses()
                .stream()
                .map(super::convertToDto)
                .collect(Collectors.toList());
    }

    /**
     * @param supply - String in path (case insensitive) to see exactly Warehouse (Supply)
     * @return WarehouseDto with e
     */
    @RequestMapping(value = "/{supply}", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public WarehouseDto getWarehouseBySupplyType(@PathVariable String supply) {
        final Warehouse warehouse = warehouseService.getBySupply(supply);
        return convertToDto(warehouse);
    }

    @Override
    protected Service<Warehouse> getService() {
        return warehouseService;
    }
}
