package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static edu.softserve.zoo.controller.rest.Routes.TASK_TYPES;

/**
 * Created by Julia Avdeionok
 */

@DocsClassDescription("Task types resource")
@RestController
@RequestMapping(TASK_TYPES)
public class TaskTypesRestController {

    @Autowired
    private TaskService taskService;

    @DocsTest
    @RequestMapping(method = RequestMethod.GET)
    public List<Task.TaskType> getTaskTypes(){
        return taskService.getTaskTypes();
    }
}
