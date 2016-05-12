package edu.softserve.zoo.controller.rest;

import edu.softserve.zoo.dto.TaskDto;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.service.Service;
import edu.softserve.zoo.service.TasksService;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MappingContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static edu.softserve.zoo.controller.rest.AbstractRestController.API_V1;
import static edu.softserve.zoo.controller.rest.TaskController.TASKS_API;


/**
 *  This {@link TaskController} stands for basic request handling relied to {@link Task} entitie.
 *
 * @author Julia Avdeionok on 11-May-16.
 */
@RestController
@RequestMapping(API_V1 + TASKS_API)
public class TaskController extends AbstractRestController<TaskDto, Task> {

    static final String TASKS_API = "/tasks";

    @Autowired
    private TasksService tasksService;

    @Autowired
    private ModelMapper modelMapper;

    public TaskController(){
        super(Task.class, TaskDto.class);
    }

    @RequestMapping(method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public Collection<TaskDto> tasksGetAllByAssigneeAndTaskStatus(@RequestParam("assigneeId") Integer assigneeId,
                                                               @RequestParam("task_status") String taskStatus){
        modelMapper.addConverter(new Converter<TaskDto.TaskStatusDto, String>() {
            @Override
            public String convert(MappingContext<TaskDto.TaskStatusDto, String> context) {
                return context.getSource() == null ? null : context.getSource().getStatusDto().toString();
            }
        });
        Collection<Task> tasksCollection =
                tasksService.tasksGetAllByAssigneeAndTaskStatus(assigneeId, Task.TaskStatus.Status.valueOf(taskStatus));

        List<TaskDto> taskDtoCollection = new ArrayList<>();
        for(Task task :tasksCollection){
            TaskDto taskDto = convertToDto(task);
            taskDtoCollection.add(taskDto);
        }

        return  taskDtoCollection;
    }

    @Override
    protected Service<Task> getService() {
        return tasksService;
    }


}
