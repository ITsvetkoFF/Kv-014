package edu.softserve.zoo.persistence.test.repository;

import edu.softserve.zoo.model.*;
import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.Repository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetAllSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.task.TaskGetAllByAssigneeIdSpecification;
import edu.softserve.zoo.persistence.specification.hibernate.impl.task.TaskGetAllByAssignerIdSpecification;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Before;
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

/**
 * @author Taras Zubrei
 * @author Julia Avdeionok
 */
@Transactional
public class TaskRepositoryTest extends AbstractRepositoryTest<Task>{

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    EmployeeRepository employeeRepository;
    @Autowired
    ZooZoneRepository zooZoneRepository;

    private Task expectedTask;
    private static final Long VALID_TASK_ID = 5L;
    private static final Long INVALID_TASK_ID = 33L;
    private static final Long VALID_ASSIGNEE_ID = 3L;
    private static final Long VALID_ASSIGNER_ID = 1L;
    private static final Long TASK_AMOUNT = 18L;
    private static final int TASK_AMOUNT_BY_ASSIGNEE_ID = 6;
    private static final int TASK_AMOUNT_BY_ASSIGNER_ID = 9;
    private static final Long NEXT_TASK_ID = 19L;

    @Test
    public void taskStatistics() {
        TaskStatistics statistics = taskRepository.getStatistics(2L);
        Assert.notNull(statistics, "Statistics of an employee has to be not null");
        Assert.notEmpty(statistics.getTaskStatuses(), "Task statuses map of the employee(id=" + 2 + ") has to be not empty");
        Assert.notEmpty(statistics.getTaskTypes(), "Task types map of the employee(id=" + 2 + ") has to be not empty");
    }

       @Test
    public void findOneTest(){
        Task actualTask = super.findOne(new GetByIdSpecification<>(getType(), VALID_TASK_ID), VALID_TASK_ID);
        assertEquals(actualTask, expectedTask);
    }

    @Test
    public void findGetAllByAssigneeIdTest(){
        super.find(new TaskGetAllByAssigneeIdSpecification(VALID_ASSIGNEE_ID), TASK_AMOUNT_BY_ASSIGNEE_ID);
    }

    @Test
    public void findGetAllByAssignerIdTest(){
        super.find(new TaskGetAllByAssignerIdSpecification(VALID_ASSIGNER_ID), TASK_AMOUNT_BY_ASSIGNER_ID);
    }

    @Test
    public void countTest(){
        Long actualAmount = taskRepository.count();
        assertEquals(TASK_AMOUNT, actualAmount);
    }

    @Test
    public void saveTest(){
        Task actualTask = super.save(expectedTask, NEXT_TASK_ID);
        assertEquals(actualTask, expectedTask);
    }

    @Test(expected = PersistenceProviderException.class)
    public void saveWithNullFieldsTest(){
        expectedTask.setAssignee(null);
        expectedTask.setZone(null);
        super.save(expectedTask, NEXT_TASK_ID);
    }

    @Test
    public void deleteTest(){
        super.delete(VALID_TASK_ID);
    }

    @Test
    public void deleteWithWrongIdTest(){
        assertFalse(taskRepository.delete(INVALID_TASK_ID, getType()));
    }

    @Test
    public void updateTest(){
        Task expectedTask = super.findOne(new GetByIdSpecification<>(getType(), VALID_TASK_ID), VALID_TASK_ID);
        expectedTask.setStatus(Task.TaskStatus.FAILED);
        Task actualTask = super.update(expectedTask);
        assertEquals(actualTask, expectedTask);
    }

    @Before
    public void setupData(){
        expectedTask = new Task();
        expectedTask.setId(VALID_TASK_ID);
        Employee assignee = employeeRepository.findOne(new GetByIdSpecification<>(Employee.class, VALID_ASSIGNEE_ID));
        Employee assigner = employeeRepository.findOne(new GetByIdSpecification<>(Employee.class, VALID_ASSIGNER_ID));
        expectedTask.setAssignee(assignee);
        expectedTask.setAssigner(assigner);
        expectedTask.setStatus(Task.TaskStatus.IN_PROGRESS);
        expectedTask.setTaskType(Task.TaskType.FEEDING);
        expectedTask.setEstimatedStart(LocalDateTime.of(2016,5,14,10,0));
        expectedTask.setEstimatedFinish(LocalDateTime.of(2016,5,16,10,0));
        expectedTask.setActualStart(LocalDateTime.of(2016,5,15,10,0));
        ZooZone zone = zooZoneRepository.findOne(new GetByIdSpecification<>(ZooZone.class, 4L));
        expectedTask.setZone(zone);
    }

    @Override
    protected Repository<Task> getRepository() {
        return taskRepository;
    }

    @Override
    protected Class<Task> getType() {
        return Task.class;
    }
}
