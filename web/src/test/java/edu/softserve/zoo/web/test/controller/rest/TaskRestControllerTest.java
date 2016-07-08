package edu.softserve.zoo.web.test.controller.rest;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import edu.softserve.zoo.controller.rest.Routes;
import edu.softserve.zoo.converter.ModelConverter;
import edu.softserve.zoo.dto.EmployeeDto;
import edu.softserve.zoo.dto.TaskDto;
import edu.softserve.zoo.dto.TaskStatisticsDto;
import edu.softserve.zoo.dto.ZooZoneDto;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.model.Task;
import edu.softserve.zoo.model.ZooZone;
import edu.softserve.zoo.service.TaskService;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.web.test.config.WebTestConfig;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDateTime;
import java.util.List;

import static edu.softserve.zoo.model.Task.TaskType.FEEDING;
import static org.junit.Assert.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by Julia Avdeionok
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebTestConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@WebAppConfiguration
public class TaskRestControllerTest {

    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private TaskService taskService;

    @Autowired
    protected ModelConverter converter;

    private MockMvc mockMvc;
    private TaskDto expectedTaskDto;

    private static final Long VALID_TASK_ID = 5L;
    private static final Long VALID_ASSIGNEE_ID = 3L;
    private static final String PARAM_ASSIGNEE_ID = "3";
    private static final String PARAM_ASSIGNER_ID = "1";
    private static final Long VALID_ASSIGNER_ID = 1L;
    private static final Long VALID_ZONE_ID = 4L;
    private static final Long TASK_AMOUNT = 18L;
    private static final int ALL_TASK_AMOUNT = 18;
    private static final int TASK_AMOUNT_BY_ASSIGNEE_ID = 6;
    private static final int TASK_AMOUNT_BY_ASSIGNER_ID = 9;
    private static final Long NEXT_TASK_ID = 19L;
    private static final Long invalidEmployeeId = 12L;
    private static final LocalDateTime VALID_Estimated_Start_TIME = LocalDateTime.of(2016, 5, 14, 10, 0);
    private static final LocalDateTime VALID_Estimated_Finish_TIME = LocalDateTime.of(2016, 5, 16, 10, 0);
    private static final LocalDateTime VALID_Actual_Start_TIME = LocalDateTime.of(2016, 5, 15, 10, 0);
    private static final LocalDateTime INVALID_FINISH_TIME = LocalDateTime.of(2016, 5, 13, 10, 0);

    @Before
    public void setUp() throws Exception {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();

        expectedTaskDto = new TaskDto();
        expectedTaskDto.setId(VALID_TASK_ID);
        EmployeeDto assignee = new EmployeeDto();
        assignee.setId(VALID_ASSIGNEE_ID);
        EmployeeDto assigner = new EmployeeDto();
        assigner.setId(VALID_ASSIGNER_ID);
        expectedTaskDto.setAssignee(assignee);
        expectedTaskDto.setAssigner(assigner);
        expectedTaskDto.setStatus(Task.TaskStatus.IN_PROGRESS);
        expectedTaskDto.setTaskType(FEEDING);
        expectedTaskDto.setEstimatedStart(VALID_Estimated_Start_TIME);
        expectedTaskDto.setEstimatedFinish(VALID_Estimated_Finish_TIME);
        expectedTaskDto.setActualStart(VALID_Actual_Start_TIME);
        ZooZoneDto zone = new ZooZoneDto();
        zone.setId(VALID_ZONE_ID);
        expectedTaskDto.setZone(zone);
    }

    @Test
    public void tasksGetAllByAssignee() throws Exception {
        this.mockMvc.perform(get(Routes.TASKS).param("assigneeId", PARAM_ASSIGNEE_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<TaskDto> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<TaskDto>>() {});
                    Assert.assertEquals(TASK_AMOUNT_BY_ASSIGNEE_ID, response.size());
                });
    }

    @Test
    public void tasksGetAllByAssigner() throws Exception {
        this.mockMvc.perform(get(Routes.TASKS).param("assignerId", PARAM_ASSIGNER_ID)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<TaskDto> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<TaskDto>>() {});
                    Assert.assertEquals(TASK_AMOUNT_BY_ASSIGNER_ID, response.size());
                });

    }

    @Test
    public void getAll() throws Exception {
        this.mockMvc.perform(get(Routes.TASKS)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    List<TaskDto> response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(),
                            new TypeReference<List<TaskDto>>() {});
                    Assert.assertEquals(ALL_TASK_AMOUNT,  response.size());
                });
    }


}