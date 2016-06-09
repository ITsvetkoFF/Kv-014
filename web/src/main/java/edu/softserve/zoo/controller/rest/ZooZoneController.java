package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.ZooZoneDto;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.ZooZoneService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Rest controller for {@link ZooZone} domain objects.
 *
 * @author Vadym Holub.
 */
@RestController
@RequestMapping(Routes.ZOO_ZONES)
public class ZooZoneController extends AbstractRestController<ZooZoneDto, ZooZone> {

    @Autowired
    private ZooZoneService zooZoneService;

    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<ZooZoneDto> getAll() {
        return super.getAll();
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.GET)
    @Override
    public ZooZoneDto getById(@PathVariable Long id) {
        return super.getById(id);
    }

    @RequestMapping(method = RequestMethod.POST)
    @Override
    public ZooZoneDto create(@RequestBody ZooZoneDto dto) {
        return super.create(dto);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.PUT)
    @Override
    public ZooZoneDto update(@RequestBody ZooZoneDto dto, @PathVariable Long id) {
        return super.update(dto, id);
    }

    @RequestMapping(path = "/{id}", method = RequestMethod.DELETE)
    @Override
    public ResponseEntity delete(@PathVariable Long id) {
        return super.delete(id);
    }

    @Override
    protected Service<ZooZone> getService() {
        return zooZoneService;
    }
}
