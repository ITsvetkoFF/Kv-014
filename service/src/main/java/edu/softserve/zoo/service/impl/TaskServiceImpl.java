package edu.softserve.zoo.service.impl;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.specification.impl.task.TaskGetAllByAssigneeIdSpecification;
import edu.softserve.zoo.persistence.specification.impl.task.TaskGetAllByAssignerIdSpecification;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.TaskService;
import edu.softserve.zoo.service.exception.ServiceReason;
import edu.softserve.zoo.util.Validator;
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
        Validator.notNull(employeeService.findOne(assignerId),
                ApplicationException
                        .getBuilderFor(NotFoundException.class)
                        .forReason(ServiceReason.NOT_FOUND)
                        .withMessage("Invalid Employee id supplied")
                        .build());

        return taskRepository.find(new TaskGetAllByAssignerIdSpecification(assignerId));
    }

    @Override
    @Transactional
    public List<Task> taskGetAllByAssigneeId(Long assigneeId) {
        Validator.notNull(employeeService.findOne(assigneeId),
                ApplicationException
                        .getBuilderFor(NotFoundException.class)
                        .forReason(ServiceReason.NOT_FOUND)
                        .withMessage("Invalid Employee id supplied")
                        .build());

        return taskRepository.find(new TaskGetAllByAssigneeIdSpecification(assigneeId));
    }

}
