package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsParamDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.function.Supplier;

import static edu.softserve.zoo.controller.rest.Routes.DASHBOARD;


/**
 * @author Taras Zubrei
 */
@RestController
@RequestMapping(DASHBOARD)
@DocsClassDescription("Dashboard resource")
public class DashboardRestController extends AbstractRestController {

    private final HashMap<DashboardType, Supplier> dashboardTypeMethodMap = fillDashboardTypeMethodMap();

    private HashMap<DashboardType, Supplier> fillDashboardTypeMethodMap() {
        HashMap<DashboardType, Supplier> map = new HashMap<>();
        map.put(DashboardType.FED_ANIMALS, this::getFedAnimalsPercentage);
        return map;
    }

    @Autowired
    StatisticsService service;

    @DocsTest(pathParameters = "fed-animals")
    @RequestMapping(path = "/{type}", method = RequestMethod.GET, produces = "application/json")
    public ResponseEntity getDashboardFor(@PathVariable @DocsParamDescription("The type of statistics") String type) {
        DashboardType dashboardType = DashboardType.valueOf(type.toUpperCase().replace('-', '_'));
        return ResponseEntity.ok(dashboardTypeMethodMap.get(dashboardType).get());
    }

    private Float getFedAnimalsPercentage() {
        return service.getFedAnimalsPercentage();
    }

    @Override
    protected Service getService() {
        return null;
    }

    private enum DashboardType {
        FED_ANIMALS
    }
}
