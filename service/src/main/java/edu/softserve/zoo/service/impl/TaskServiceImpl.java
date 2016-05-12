package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.TasksRepository;
import edu.softserve.zoo.persistence.specification.impl.TasksGetAllByAssigneeAndTaskStatusSpecification;
import edu.softserve.zoo.persistence.specification.impl.TasksGetAllByAssigneeSpecification;
import edu.softserve.zoo.persistence.specification.impl.TasksGetAllByTaskStatusSpecification;
import edu.softserve.zoo.service.TasksService;
import edu.softserve.zoo.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

/**
 * @author Julia Avdeionok
 */
@Service
public class TaskServiceImpl extends ServiceImpl<Task> implements TasksService {

    @Autowired
    private TasksRepository tasksRepository;

    @Transactional(readOnly = true)
    @Override
    public Collection<Task> tasksGetAllByAssigneeAndTaskStatus(Integer assigneeId, Task.TaskStatus.Status taskStatus) {
        //TODO add checking if such assigneeId is present
        return tasksRepository.find(new TasksGetAllByAssigneeAndTaskStatusSpecification(assigneeId, taskStatus));
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Task> tasksGetAllByAssignee(Integer assigneeId) {
        Collection<Task> tasksGetAllByAssigneeCollection = tasksRepository.find(
                new TasksGetAllByAssigneeSpecification(assigneeId));
        if (tasksGetAllByAssigneeCollection.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .withMessage("Cannot find such assignee: " + assigneeId)
                    .build();
        }

        return tasksGetAllByAssigneeCollection;
    }

    @Transactional(readOnly = true)
    @Override
    public Collection<Task> tasksGetAllByTaskStatus(Task.TaskStatus.Status taskStatus) {
        Collection<Task> tasksGetAllByTaskStatusCollection = tasksRepository.find(
                new TasksGetAllByTaskStatusSpecification(taskStatus));
        if (tasksGetAllByTaskStatusCollection.isEmpty()) {
            throw ApplicationException.getBuilderFor(NotFoundException.class)
                    .forReason(ExceptionReason.NOT_FOUND)
                    .withMessage("Cannot find such task status: " + taskStatus)
                    .build();
        }
        return tasksGetAllByTaskStatusCollection;
    }


    @Override
    public void deleteById(Integer id) {

    }

    @Override
    Repository<Task> getRepository() {
        return tasksRepository;
    }



}
