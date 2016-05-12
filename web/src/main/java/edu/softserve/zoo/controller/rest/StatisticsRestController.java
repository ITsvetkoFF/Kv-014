package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.model.Statistics;
import edu.softserve.zoo.service.StatisticsService;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.stream.Collectors;
import static edu.softserve.zoo.controller.rest.StatisticsRestController.API_V1;
import static edu.softserve.zoo.controller.rest.StatisticsRestController.STAT;


/**
 * Created by Taras on 11.05.2016.
 */
@RestController
@RequestMapping(API_V1 + STAT)
public class StatisticsRestController {
    static final String API_V1 = "api/v1";
    static final String STAT = "/statistics";

    @Autowired
    StatisticsService service;

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public Statistics getStatistics() {
        return service.getZooStatistics();
    }

    @RequestMapping(path = "/fed_animals", method = RequestMethod.GET, produces = "application/json")
    public Float getFedAnimalsPercentage() {
        return service.getFedAnimalsPercentage();
    }

    @RequestMapping(path = "/employee_task_statuses/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<ImmutablePair<String, Integer>> getEmployeeTasksStatuses(@PathVariable Integer id) {
        return service.getEmployeeTasksStatuses(id).stream().map(pair -> new ImmutablePair<>(pair.getLeft().name().toLowerCase(), pair.getRight())).collect(Collectors.toList());
    }

    @RequestMapping(path = "/employee_task_types/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<ImmutablePair<String, Integer>> getEmployeeTasksTypes(@PathVariable Integer id) {
        return service.getEmployeeTasksTypes(id).stream().map(pair -> new ImmutablePair<>(pair.getLeft().name().toLowerCase(), pair.getRight())).collect(Collectors.toList());
    }
}
