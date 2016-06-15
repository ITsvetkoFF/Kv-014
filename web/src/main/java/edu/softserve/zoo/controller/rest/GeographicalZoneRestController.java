package edu.softserve.zoo.controller.rest;


import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.GeographicalZoneDto;
import edu.softserve.zoo.model.GeographicalZone;
import edu.softserve.zoo.service.GeographicalZoneService;
import edu.softserve.zoo.service.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.GEO_ZONES;

/**
 * Rest Controller to manage requests, which deal with GeographicalZone
 *
 * @author Vadym Holub
 */
@RestController
@RequestMapping(GEO_ZONES)
@DocsClassDescription("Geographical zone resource")
public class GeographicalZoneRestController extends AbstractRestController<GeographicalZoneDto, GeographicalZone> {

    @Autowired
    private GeographicalZoneService geographicalZoneService;

    @Override
    protected Service<GeographicalZone> getService() {
        return geographicalZoneService;
    }

    @DocsTest
    @RequestMapping(method = RequestMethod.GET)
    @Override
    public List<GeographicalZoneDto> getAll() {
        return super.getAll();
    }
}