package edu.softserve.zoo.controller.rest;


import edu.softserve.zoo.dto.TaskDto;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.TASKS;

/**
 * RestController implementation for {@link Task} entity.
 */

@RestController
@RequestMapping(TASKS)
public class TasksRestController extends AbstractRestController<TaskDto, Task> {

    @Autowired
    private TaskService taskService;

    @RequestMapping(method = RequestMethod.GET, params = "assigneeId")
    public List<TaskDto> tasksGetAllByAssignee(@RequestParam("assigneeId") Long assigneeId) {
        List<Task> taskList = taskService.taskGetAllByAssigneeId(assigneeId);
        return converter.convertToDto(taskList);
    }

    @RequestMapping(method = RequestMethod.GET, params = "assignerId")
    public List<TaskDto> tasksGetAllByAssigner(@RequestParam("assignerId") Long assignerId) {
        List<Task> taskList = taskService.taskGetAllByAssignerId(assignerId);
        return converter.convertToDto(taskList);
    }

    @Override
    @RequestMapping(method = RequestMethod.GET)
    public List<TaskDto> getAll() {
        return super.getAll();
    }

    @Override
    protected Service<Task> getService() {
        return taskService;
    }
}
