package edu.softserve.zoo.service;


import edu.softserve.zoo.model.Task;

import java.util.Collection;

/**
 * Created by Julia Avdeionok
 */
public interface TasksService extends Service<Task> {
    Collection<Task> tasksGetAllByAssigneeAndTaskStatus(Integer assigneeId, Task.TaskStatus.Status taskStatus);
    Collection<Task> tasksGetAllByAssignee(Integer assigneeId);
    Collection<Task> tasksGetAllByTaskStatus(Task.TaskStatus.Status taskStatus);

}
