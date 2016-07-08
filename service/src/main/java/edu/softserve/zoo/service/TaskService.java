package edu.softserve.zoo.service;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;

import java.util.List;

/**
 * <p>TaskService extends {@link Service}, provides service implementation for domain object {@link Task}</p>
 *
 * @author Julia Avdeionok
 */
public interface TaskService extends Service<Task> {
    /**
     * Returns the List of {@link Task} by specified {@link Employee} id
     *
     * @param assignerId of {@link Employee}
     * @return List of {@link Task}
     */
    List<Task> taskGetAllByAssignerId(Long assignerId);

    /**
     * Returns the List of {@link Task} by specified {@link Employee} id
     *
     * @param assigneeId of {@link Employee}
     * @return List of {@link Task}
     */
    List<Task> taskGetAllByAssigneeId(Long assigneeId);

    /**
     * Returns {@link TaskStatistics} by specified {@link Employee} id
     *
     * @param employeeId of {@link Employee}
     * @return List of {@link Task}
     */
    TaskStatistics getStatistics(Long employeeId);

    /**
     * Returns the List of available {@link Task.TaskType}
     *
     * @return List of {@link Task.TaskType}
     */
    List<Task.TaskType> getTaskTypes();
}