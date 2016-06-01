package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.TaskStatisticsDto;
import edu.softserve.zoo.dto.TaskStatusDto;
import edu.softserve.zoo.dto.TaskTypeDto;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.Routes.API_V1;

/**
 * Just mock for testing
 *
 * @author Taras Zubrei
 */
@RestController
@RequestMapping(API_V1 + "/tasks")
public class TaskRestController {
    @Autowired
    TaskRepository repo;
    @RequestMapping(path = "/statistics/{id}", method = RequestMethod.GET, produces = "application/json")
    @Transactional
    public TaskStatisticsDto getEmployeeTasksStatuses(@PathVariable Long id) {
        TaskStatisticsDto dto = new TaskStatisticsDto();
        TaskStatistics statistics = repo.getStatistics(id);
        dto.setTaskStatuses(
                statistics.getTaskStatuses().entrySet().stream()
                .map(pair -> new TaskStatusDto(pair.getKey(), pair.getValue()))
                .collect(Collectors.toList())
        );
        dto.setTaskTypes(
                statistics.getTaskTypes().entrySet().stream()
                        .map(pair -> new TaskTypeDto(pair.getKey(), pair.getValue()))
                        .collect(Collectors.toList())
        );
        return dto;
    }
}
