package edu.softserve.zoo.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.Error;
import edu.softserve.zoo.dto.StatisticsDto;
import edu.softserve.zoo.dto.TaskStatusDto;
import edu.softserve.zoo.dto.TaskTypeDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.service.StatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.Routes.STAT;


/**
 * @author Taras Zubrei
 */
@RestController
@RequestMapping(STAT)
public class StatisticsRestController {

    @Autowired
    StatisticsService service;

    @Autowired
    ObjectMapper objectMapper;

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public Error handleApplicationException(ApplicationException exception) {
        return new Error(exception);
    }

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler
    public Error handleNotFoundExceptionException(NotFoundException exception) {
        return new Error(exception);
    }

    @RequestMapping(method = RequestMethod.GET, produces = "application/json")
    public StatisticsDto getStatistics() {
        return objectMapper.convertValue(service.getZooStatistics(), StatisticsDto.class);
    }

    @RequestMapping(path = "/fed_animals", method = RequestMethod.GET, produces = "application/json")
    public Float getFedAnimalsPercentage() {
        return service.getFedAnimalsPercentage();
    }

    @RequestMapping(path = "/employee_task_statuses/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<TaskStatusDto> getEmployeeTasksStatuses(@PathVariable Long id) {
        return service.getEmployeeTasksStatuses(id).entrySet().stream()
                .map(pair -> new TaskStatusDto(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
    }

    @RequestMapping(path = "/employee_task_types/{id}", method = RequestMethod.GET, produces = "application/json")
    public List<TaskTypeDto> getEmployeeTasksTypes(@PathVariable Long id) {
        return service.getEmployeeTasksTypes(id).entrySet().stream()
                .map(pair -> new TaskTypeDto(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList());
    }
}
