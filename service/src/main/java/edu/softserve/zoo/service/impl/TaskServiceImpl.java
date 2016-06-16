package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.task.TaskGetAllByAssigneeIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.task.TaskGetAllByAssignerIdSpecification;
import edu.softserve.zoo.service.EmployeeService;
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

    @Autowired
    private EmployeeService employeeService;

    @Override
    Repository<Task> getRepository() {
        return taskRepository;
    }

    @Override
    Class<Task> getType() {
        return Task.class;
    }

    @Override
    @Transactional
    public List<Task> taskGetAllByAssignerId(Long assignerId) {
        validateNullableArgument(assignerId);
        employeeService.findOne(assignerId);
        return taskRepository.find(new TaskGetAllByAssignerIdSpecification(assignerId));
    }

    @Override
    @Transactional
    public List<Task> taskGetAllByAssigneeId(Long assigneeId) {
        validateNullableArgument(assigneeId);
        employeeService.findOne(assigneeId);
        return taskRepository.find(new TaskGetAllByAssigneeIdSpecification(assigneeId));
    }

    @Override
    @Transactional
    public TaskStatistics getStatistics(Long employeeId) {
        validateNullableArgument(employeeId);
        employeeService.findOne(employeeId);
        return taskRepository.getStatistics(employeeId);
    }
}
