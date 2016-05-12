package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.GeographicalZoneDto;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static edu.softserve.zoo.controller.rest.GeographicalZoneController.GEO_ZONE;
import static edu.softserve.zoo.controller.rest.AbstractRestController.API_V1;

/**
 * Rest Controller to manage requests, which deal with GeographicalZone
 *
 * @author Vadym Holub
 */
@RestController
@RequestMapping(API_V1 + GEO_ZONE)
public class GeographicalZoneController extends AbstractRestController<GeographicalZoneDto, GeographicalZone> {

    protected static final String GEO_ZONE = "/geo_zone";

    @Autowired
    private GeographicalZoneService geographicalZoneService;

    public GeographicalZoneController() {
        super(GeographicalZone.class, GeographicalZoneDto.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Collection<GeographicalZoneDto> getAll() {
        Collection<GeographicalZone> geographicalZones = geographicalZoneService.getAll();
        List<GeographicalZoneDto> dtoZones = new ArrayList<>(14);
        for (GeographicalZone zone : geographicalZones) {
            GeographicalZoneDto dtoZone = convertToDto(zone);
            dtoZones.add(dtoZone);
        }
        return dtoZones;
    }

    @Override
    protected Service<GeographicalZone> getService() {
        return geographicalZoneService;
    }

}
