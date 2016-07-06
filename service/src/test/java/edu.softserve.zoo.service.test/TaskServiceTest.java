package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Animal;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.persistence.repository.EmployeeRepository;
import edu.softserve.zoo.persistence.repository.TaskRepository;
import edu.softserve.zoo.persistence.repository.ZooZoneRepository;
import edu.softserve.zoo.persistence.specification.hibernate.impl.GetByIdSpecification;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.TaskService;
import edu.softserve.zoo.service.ZooZoneService;
import edu.softserve.zoo.service.config.ServiceConfig;
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
import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@Transactional
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    @Autowired
    EmployeeService employeeService;
    @Autowired
    ZooZoneService zooZoneService;

    private Task expectedTask;
    private static final Long VALID_TASK_ID = 5L;
    private static final Long INVALID_TASK_ID = 33L;
    private static final Long VALID_ASSIGNEE_ID = 3L;
    private static final Long VALID_ASSIGNER_ID = 1L;
    private static final Long VALID_ZONE_ID = 4L;
    private static final Long TASK_AMOUNT = 18L;
    private static final int TASK_AMOUNT_BY_ASSIGNEE_ID = 6;
    private static final int TASK_AMOUNT_BY_ASSIGNER_ID = 4;
    private static final Long NEXT_TASK_ID = 19L;

    private static final Long invalidEmployeeId = 12L;
    private static final Long validEmployeeId = 2L;

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForStatistics() {
        taskService.getStatistics(invalidEmployeeId);
    }

    @Test
    public void taskGetAllByAssignerId() throws Exception{
        List<Task> tasksGetAllByAssigner = taskService.taskGetAllByAssignerId(VALID_ASSIGNER_ID);
        assertEquals(TASK_AMOUNT_BY_ASSIGNER_ID, tasksGetAllByAssigner.size());
        tasksGetAllByAssigner.forEach(task -> assertEquals(VALID_ASSIGNER_ID, task.getAssigner().getId()));
    }

    @Test
    public void taskGetAllByAssigneeId() throws Exception {

    }

    @Test
    public void getStatistics() throws Exception {

    }

    @Test
    public void save() throws Exception {

    }

    @Test
    public void getTaskTypes() throws Exception {

    }

    @Test
    public void findOne() throws Exception {

    }

    @Test
    public void count() throws Exception {

    }

    @Test
    public void findAll() throws Exception {

    }

    @Test
    public void save1() throws Exception {

    }

    @Test
    public void update() throws Exception {

    }

    @Test
    public void delete() throws Exception {

    }

    @Test
    public void validateNullableArgument() throws Exception {

    }

    @Test
    public void getRepository1() throws Exception {

    }

    @Test
    public void getType1() throws Exception {

    }

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForAllByAssignee() {
        taskService.taskGetAllByAssigneeId(invalidEmployeeId);
    }

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForAllByAssigner() {
        taskService.taskGetAllByAssignerId(invalidEmployeeId);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForStatistics() {
        taskService.getStatistics(null);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForAllByAssignee() {
        taskService.taskGetAllByAssigneeId(null);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForAllByAssigner() {
        taskService.taskGetAllByAssignerId(null);
    }

    @Test
    public void validEmployeeId() {
        Assert.notNull(taskService.getStatistics(validEmployeeId));
        Assert.notNull(taskService.taskGetAllByAssigneeId(validEmployeeId));
        Assert.notNull(taskService.taskGetAllByAssignerId(validEmployeeId));
    }

    @Before
    public void setupData(){
        expectedTask = new Task();
        expectedTask.setId(VALID_TASK_ID);
        Employee assignee = employeeService.findOne(VALID_ASSIGNEE_ID);
        Employee assigner = employeeService.findOne(VALID_ASSIGNER_ID);
        expectedTask.setAssignee(assignee);
        expectedTask.setAssigner(assigner);
        expectedTask.setStatus(Task.TaskStatus.IN_PROGRESS);
        expectedTask.setTaskType(Task.TaskType.FEEDING);
        expectedTask.setEstimatedStart(LocalDateTime.of(2016,5,14,10,0));
        expectedTask.setEstimatedFinish(LocalDateTime.of(2016,5,16,10,0));
        expectedTask.setActualStart(LocalDateTime.of(2016,5,15,10,0));
        ZooZone zone = zooZoneService.findOne(VALID_ZONE_ID);
        expectedTask.setZone(zone);
    }
}
