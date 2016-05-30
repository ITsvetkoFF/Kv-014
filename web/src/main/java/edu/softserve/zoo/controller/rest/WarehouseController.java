package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.WarehouseDto;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.WarehouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.WAREHOUSES;

/**
 * RestController implementation for {@link Warehouse} entity.
 *
 * @author Andrii Abramov on 20-May-16.
 */
@RestController
@RequestMapping(WAREHOUSES)
public class WarehouseController extends AbstractRestController<WarehouseDto, Warehouse> {

    @Autowired
    private WarehouseService warehouseService;

    protected WarehouseController() {
        super(Warehouse.class);
    }

    @Override
    protected Service<Warehouse> getService() {
        return warehouseService;
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<WarehouseDto> getAll() {
        return super.getAll();
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WarehouseDto getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public WarehouseDto update(@RequestBody WarehouseDto dto, @PathVariable Long id) {
        return super.update(dto, id);
    }




}
