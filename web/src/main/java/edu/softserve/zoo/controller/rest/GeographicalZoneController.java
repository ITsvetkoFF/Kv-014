package edu.softserve.zoo.controller.rest;


import edu.softserve.zoo.dto.GeographicalZoneDto;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.GEO_ZONE;

/**
 * Rest Controller to manage requests, which deal with GeographicalZone
 *
 * @author Vadym Holub
 */
@RestController
@RequestMapping(GEO_ZONE)
public class GeographicalZoneController extends AbstractRestController<GeographicalZoneDto, GeographicalZone> {

    @Autowired
    private GeographicalZoneService geographicalZoneService;

    public GeographicalZoneController() {
        super(GeographicalZone.class);
    }

    @Override
    protected Service<GeographicalZone> getService() {
        return geographicalZoneService;
    }

}