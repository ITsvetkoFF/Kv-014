package edu.softserve.zoo.service;

import edu.softserve.zoo.model.Task;

import java.util.List;

/**
 * <p>TaskService extends {@link Service}, provides service implementation for domain object {@link Task}</p>
 *
 * @author Julia Avdeionok
 */
public interface TaskService extends Service<Task> {
    List<Task> taskGetAllByAssignerId(Long assignerId);
    List<Task> taskGetAllByAssigneeId(Long assigneeId);
}