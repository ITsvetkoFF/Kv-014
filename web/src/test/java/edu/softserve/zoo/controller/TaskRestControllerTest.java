package edu.softserve.zoo.controller;

import edu.softserve.zoo.controller.rest.Routes;
import edu.softserve.zoo.model.Task;
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

import static org.hamcrest.Matchers.is;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/web-test-ctx.xml")
@ActiveProfiles("test")
@WebAppConfiguration
public class TaskRestControllerTest {
    @Autowired
    private WebApplicationContext context;
    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context).build();
    }
    @Test
    public void statistics() throws Exception {
        this.mockMvc.perform(get(Routes.TASKS+"/statistics/{id}", 2).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.taskStatuses[0].taskStatus", is(enumToString(Task.TaskStatus.ACCOMPLISHED))))
                .andExpect(jsonPath("$.taskStatuses[0].count", is(4)))
                .andExpect(jsonPath("$.taskStatuses[1].taskStatus", is(enumToString(Task.TaskStatus.FAILED))))
                .andExpect(jsonPath("$.taskStatuses[1].count", is(1)))
                .andExpect(jsonPath("$.taskStatuses[2].taskStatus", is(enumToString(Task.TaskStatus.IN_PROGRESS))))
                .andExpect(jsonPath("$.taskStatuses[2].count", is(2)))
                .andExpect(jsonPath("$.taskTypes[0].taskType", is(enumToString(Task.TaskType.FEEDING))))
                .andExpect(jsonPath("$.taskTypes[0].count", is(1)))
                .andExpect(jsonPath("$.taskTypes[1].taskType", is(enumToString(Task.TaskType.HEALTH_INSPECTION))))
                .andExpect(jsonPath("$.taskTypes[1].count", is(3)))
                .andExpect(jsonPath("$.taskTypes[2].taskType", is(enumToString(Task.TaskType.GIVE_MEDICINE))))
                .andExpect(jsonPath("$.taskTypes[2].count", is(3)));
        this.mockMvc.perform(get(Routes.TASKS+"/statistics/{id}", 12).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
    private String enumToString(Enum s){
        return org.apache.commons.lang3.StringUtils.capitalize(s.toString().toLowerCase().replace('_', ' '));
    }
}
