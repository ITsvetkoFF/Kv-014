package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import static edu.softserve.zoo.controller.rest.Routes.STAT;


/**
 * @author Taras Zubrei
 */
@RestController
@RequestMapping(STAT)
public class StatisticsRestController extends AbstractRestController {

    @Autowired
    StatisticsService service;

    protected StatisticsRestController() {
        super(null);
    }

    @RequestMapping(path = "/fed_animals", method = RequestMethod.GET, produces = "application/json")
    public Float getFedAnimalsPercentage() {
        return service.getFedAnimalsPercentage();
    }

    @Override
    protected Service getService() {
        return null;
    }
}
