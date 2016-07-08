package edu.softserve.zoo.service.test;

import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.service.EmployeeService;
import edu.softserve.zoo.service.TaskService;
import edu.softserve.zoo.service.ZooZoneService;
import edu.softserve.zoo.service.config.ServiceConfig;
import edu.softserve.zoo.service.exception.TaskException;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static edu.softserve.zoo.model.Task.TaskType.FEEDING;
import static org.junit.Assert.assertEquals;

/**
 * @author Taras Zubrei
 * @author Julia Avdeionok
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
    private static final Long VALID_ASSIGNEE_ID = 3L;
    private static final Long VALID_ASSIGNER_ID = 1L;
    private static final Long VALID_ZONE_ID = 4L;
    private static final Long TASK_AMOUNT = 18L;
    private static final int ALL_TASK_AMOUNT = 18;
    private static final int TASK_AMOUNT_BY_ASSIGNEE_ID = 6;
    private static final int TASK_AMOUNT_BY_ASSIGNER_ID = 9;
    private static final Long NEXT_TASK_ID = 19L;
    private static final Long invalidEmployeeId = 12L;
    private static final LocalDateTime INVALID_FINISH_TIME = LocalDateTime.of(2016, 5, 13, 10, 0);
    private final List<Task.TaskType> EXPECTED_TASK_TAPES =
            Arrays.asList(Task.TaskType.FEEDING,
                    Task.TaskType.HEALTH_INSPECTION,
                    Task.TaskType.GIVE_MEDICINE);

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForStatistics() {
        taskService.getStatistics(invalidEmployeeId);
    }

    @Test
    public void taskGetAllByAssignerId() throws Exception {
        List<Task> tasksGetAllByAssigner = taskService.taskGetAllByAssignerId(VALID_ASSIGNER_ID);
        assertEquals(TASK_AMOUNT_BY_ASSIGNER_ID, tasksGetAllByAssigner.size());
        tasksGetAllByAssigner.forEach(task -> assertEquals(VALID_ASSIGNER_ID, task.getAssigner().getId()));
    }

    @Test
    public void taskGetAllByAssigneeId() throws Exception {
        List<Task> tasksGetAllByAssignee = taskService.taskGetAllByAssigneeId(VALID_ASSIGNEE_ID);
        assertEquals(TASK_AMOUNT_BY_ASSIGNEE_ID, tasksGetAllByAssignee.size());
        tasksGetAllByAssignee.forEach(task -> assertEquals(VALID_ASSIGNEE_ID, task.getAssignee().getId()));
    }

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForAllByAssignee() {
        taskService.taskGetAllByAssigneeId(invalidEmployeeId);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForAllByAssignee() {
        taskService.taskGetAllByAssigneeId(null);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForAllByAssigner() {
        taskService.taskGetAllByAssignerId(null);
    }

    @Test(expected = NotFoundException.class)
    public void invalidEmployeeIdForAllByAssigner() {
        taskService.taskGetAllByAssignerId(invalidEmployeeId);
    }

    @Test(expected = ValidationException.class)
    public void nullEmployeeIdForStatistics() {
        taskService.getStatistics(null);
    }

    @Test
    public void save() throws Exception {
        expectedTask.setId(NEXT_TASK_ID);
        taskService.save(expectedTask);
        Task savedTask = taskService.findOne(NEXT_TASK_ID);
        assertEquals(savedTask, expectedTask);
    }

    @Test(expected = TaskException.class)
    public void saveInvalidTime() throws Exception {
        expectedTask.setId(NEXT_TASK_ID);
        expectedTask.setEstimatedFinish(INVALID_FINISH_TIME);
        taskService.save(expectedTask);
    }

    @Test
    public void getTaskTypes() throws Exception {
        List<Task.TaskType> actualTaskTypes = taskService.getTaskTypes();
        assertEquals(actualTaskTypes, EXPECTED_TASK_TAPES);
    }

    @Test
    public void findOne() throws Exception {
        Task actualTask = taskService.findOne(VALID_TASK_ID);
        assertEquals(actualTask, expectedTask);
    }

    @Test
    public void count() throws Exception {
        Long actualTaskCount = taskService.count();
        assertEquals(actualTaskCount, TASK_AMOUNT);
    }

    @Test
    public void findAll() throws Exception {
        List<Task> actualTaskList = taskService.findAll();
        assertEquals(actualTaskList.size(), ALL_TASK_AMOUNT);
    }


    @Test
    public void update() throws Exception {
        expectedTask.setStatus(Task.TaskStatus.RESCHEDULED);
        Task actualTask = taskService.update(expectedTask);
        assertEquals(actualTask, expectedTask);
    }

    @Test(expected = NotFoundException.class)
    public void delete() throws Exception {
        taskService.delete(VALID_TASK_ID);
        taskService.findOne(VALID_TASK_ID);
    }

    @Before
    public void setupData() {
        expectedTask = new Task();
        expectedTask.setId(VALID_TASK_ID);
        Employee assignee = employeeService.findOne(VALID_ASSIGNEE_ID);
        Employee assigner = employeeService.findOne(VALID_ASSIGNER_ID);
        expectedTask.setAssignee(assignee);
        expectedTask.setAssigner(assigner);
        expectedTask.setStatus(Task.TaskStatus.IN_PROGRESS);
        expectedTask.setTaskType(FEEDING);
        expectedTask.setEstimatedStart(LocalDateTime.of(2016, 5, 14, 10, 0));
        expectedTask.setEstimatedFinish(LocalDateTime.of(2016, 5, 16, 10, 0));
        expectedTask.setActualStart(LocalDateTime.of(2016, 5, 15, 10, 0));
        ZooZone zone = zooZoneService.findOne(VALID_ZONE_ID);
        expectedTask.setZone(zone);
    }
}
