package edu.softserve.zoo.controller.rest;


import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsParamDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.TaskDto;
import edu.softserve.zoo.dto.TaskStatisticsDto;
import edu.softserve.zoo.dto.TaskStatusDto;
import edu.softserve.zoo.dto.TaskTypeDto;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.Routes.TASKS;

/**
 * RestController implementation for {@link Task} entity.
 */

@DocsClassDescription("Task resource")
@RestController
@RequestMapping(TASKS)
public class TaskRestController extends AbstractRestController<TaskDto, Task> {

    @Autowired
    private TaskService taskService;

    @DocsTest(pathParameters = "1")
    @RequestMapping(method = RequestMethod.GET, params = "assigneeId")
    public List<TaskDto> tasksGetAllByAssignee(@RequestParam("assigneeId") @DocsParamDescription("The id of assignee") Long assigneeId) {
        List<Task> taskList = taskService.taskGetAllByAssigneeId(assigneeId);
        return converter.convertToDto(taskList);
    }

    @DocsTest(pathParameters = "1")
    @RequestMapping(method = RequestMethod.GET, params = "assignerId")
    public List<TaskDto> tasksGetAllByAssigner(@RequestParam("assignerId") @DocsParamDescription("The id of assigner") Long assignerId) {
        List<Task> taskList = taskService.taskGetAllByAssignerId(assignerId);
        return converter.convertToDto(taskList);
    }

    @DocsTest
    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getAll() {
        return super.getAll();
    }

    @DocsTest(pathParameters = "2")
    @RequestMapping(path = "/statistics/{id}", method = RequestMethod.GET, produces = "application/json")
    public TaskStatisticsDto getStatistics(@PathVariable @DocsParamDescription("Employee id") Long id) {
        TaskStatisticsDto dto = new TaskStatisticsDto();
        TaskStatistics statistics = taskService.getStatistics(id);
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

    @Override
    protected Service<Task> getService() {
        return taskService;
    }
}
