package edu.softserve.zoo.web.test.controller.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.controller.rest.Routes;
import edu.softserve.zoo.dto.TaskStatisticsDto;
import edu.softserve.zoo.dto.TaskStatusDto;
import edu.softserve.zoo.dto.TaskTypeDto;
import edu.softserve.zoo.model.Task;
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
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Arrays;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebTestConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@WebAppConfiguration
public class EmployeeRestControllerTest {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }

    @Test
    public void statistics() throws Exception {
        final TaskStatisticsDto dto = new TaskStatisticsDto();
        dto.setTaskTypes(Arrays.asList(
                new TaskTypeDto(Task.TaskType.FEEDING, 1L),
                new TaskTypeDto(Task.TaskType.HEALTH_INSPECTION, 3L),
                new TaskTypeDto(Task.TaskType.GIVE_MEDICINE, 3L)
        ));
        dto.setTaskStatuses(Arrays.asList(
                new TaskStatusDto(Task.TaskStatus.ACCOMPLISHED, 4L),
                new TaskStatusDto(Task.TaskStatus.FAILED, 1L),
                new TaskStatusDto(Task.TaskStatus.IN_PROGRESS, 2L)
        ));

        this.mockMvc.perform(get(Routes.EMPLOYEES + "/{id}/performance", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(mvcResult -> {
                    TaskStatisticsDto response = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), TaskStatisticsDto.class);
                    Assert.assertEquals("Response validation failed", dto, response);
                });
        this.mockMvc.perform(get(Routes.EMPLOYEES + "/{id}/performance", 12).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}
