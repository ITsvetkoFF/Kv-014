package edu.softserve.zoo.service;

import edu.softserve.zoo.exceptions.ApplicationException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/service-test-ctx.xml")
@ActiveProfiles("test")
@Transactional
public class TaskServiceTest {
    @Autowired
    private TaskService taskService;
    private final Long invalidEmployeeId = 12L;

    @Test(expected = ApplicationException.class)
    public void invalidEmployeeIdForStatistics() {
        taskService.getStatistics(invalidEmployeeId);
    }
    @Test(expected = ApplicationException.class)
    public void invalidEmployeeIdForAllByAssignee() {
        taskService.taskGetAllByAssigneeId(invalidEmployeeId);
    }
    @Test(expected = ApplicationException.class)
    public void invalidEmployeeIdForAllByAssigner() {
        taskService.taskGetAllByAssignerId(invalidEmployeeId);
    }
}
