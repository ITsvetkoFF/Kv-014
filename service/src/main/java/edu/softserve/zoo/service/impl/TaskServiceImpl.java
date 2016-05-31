package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.impl.task.TaskGetAllByAssigneeIdSpecification;
import edu.softserve.zoo.persistence.specification.impl.task.TaskGetAllByAssignerIdSpecification;
import edu.softserve.zoo.service.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * <p> Service implementation of {@link TaskService} for domain object {@link Task}</p>
 *
 * @author Julia Avdeionok
 */
@Service
public class TaskServiceImpl extends AbstractService<Task> implements TaskService {

    @Autowired
    private TaskRepository taskRepository;

    @Override
    Repository<Task> getRepository() {
        return taskRepository;
    }

    @Override
    @Transactional
    public List<Task> taskGetAllByAssignerId(Long assignerId) {
        return taskRepository.find(new TaskGetAllByAssignerIdSpecification(assignerId));
    }

    @Override
    @Transactional
    public List<Task> taskGetAllByAssigneeId(Long assigneeId) {
        return taskRepository.find(new TaskGetAllByAssigneeIdSpecification(assigneeId));
    }

}
