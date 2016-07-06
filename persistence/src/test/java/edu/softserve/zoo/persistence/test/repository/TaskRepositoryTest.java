package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.TaskStatistics;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetAllSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@Transactional
public class TaskRepositoryTest {
    @Autowired
    TaskRepository taskRepository;
    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ZooZoneRepository zooZoneRepository;

    @Test
    public void taskStatistics() {
        TaskStatistics statistics = taskRepository.getStatistics(2L);
        Assert.notNull(statistics, "Statistics of an employee has to be not null");
        Assert.notEmpty(statistics.getTaskStatuses(), "Task statuses map of the employee(id=" + 2 + ") has to be not empty");
        Assert.notEmpty(statistics.getTaskTypes(), "Task types map of the employee(id=" + 2 + ") has to be not empty");
    }

    @Test
    public void crud() {
        Task task = new Task();
        Employee assignee = employeeRepository.findOne(new GetByIdSpecification<>(Employee.class, 2L));
        Employee assigner = employeeRepository.findOne(new GetByIdSpecification<>(Employee.class, 1L));
        task.setAssignee(assignee);
        task.setAssigner(assigner);
        task.setStatus(Task.TaskStatus.ACCOMPLISHED);
        task.setTaskType(Task.TaskType.HEALTH_INSPECTION);
        task.setActualStart(LocalDateTime.now().minusDays(1));
        task.setActualFinish(LocalDateTime.now().minusMinutes(2));
        task.setEstimatedStart(LocalDateTime.now().minusHours(5));
        task.setEstimatedFinish(LocalDateTime.now().plusHours(19));
        ZooZone zone = zooZoneRepository.findOne(new GetByIdSpecification<>(ZooZone.class, 1L));
        task.setZone(zone);

        task = taskRepository.save(task);
        Assert.notNull(task, "Create operation failed");
        Assert.notNull(task.getId(), "Id has to be not null after persisting");
        Assert.notNull(taskRepository.findOne(new GetByIdSpecification<>(Task.class, 5L)), "Read one operation failed");
        Assert.notEmpty(taskRepository.find(new GetAllSpecification<>(Task.class)), "Read all operation failed");
        task.setTaskType(Task.TaskType.FEEDING);
        task = taskRepository.save(task);
        assertEquals("Update operation failed", Task.TaskType.FEEDING, task.getTaskType());
        Assert.isTrue(taskRepository.delete(task.getId(), Task.class), "Delete operation failed");
    }
}
