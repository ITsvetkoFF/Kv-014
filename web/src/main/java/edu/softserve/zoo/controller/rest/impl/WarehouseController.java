package edu.softserve.zoo.controller.rest.impl;

import edu.softserve.zoo.dto.WarehouseDto;
import edu.softserve.zoo.model.Warehouse;
import edu.softserve.zoo.service.WarehouseService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.Routes.WAREHOUSES;

/**
 * RestController implementation for {@link Warehouse} entity.
 *
 * @author Andrii Abramov on 20-May-16.
 */
@RestController
@RequestMapping(WAREHOUSES)
public class WarehouseController {

    @Autowired
    private WarehouseService warehouseService;

    @Autowired
    private ModelMapper modelMapper;

    @RequestMapping(method = RequestMethod.GET)
    public List<WarehouseDto> getAll() {
        final List<Warehouse> warehouses = warehouseService.findAll(Warehouse.class);
        return warehouses.stream()
                .map(e -> modelMapper.map(e, WarehouseDto.class))
                .collect(Collectors.toList());
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public WarehouseDto getById(@PathVariable Long id) {
        final Warehouse warehouse = warehouseService.findOne(id, Warehouse.class);
        return modelMapper.map(warehouse, WarehouseDto.class);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PATCH)
    public WarehouseDto update(@RequestBody WarehouseDto dto, @PathVariable Long id) {
        dto.setId(id);
        final Warehouse entity = modelMapper.map(dto, Warehouse.class);
        warehouseService.update(entity);
        Warehouse response = warehouseService.findOne(id, Warehouse.class);
        return modelMapper.map(response, WarehouseDto.class);
    }


}
