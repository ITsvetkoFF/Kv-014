package edu.softserve.zoo.rest.docs;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
public class StatisticsTest extends AbstractTest {
    @Test
    public void run() throws Exception {
        getStatistics();
        getFedAnimals();
        getTaskTypes(2L);
        getTaskStatuses(2L);
    }
    public void getStatistics() throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("getStatistics");

        document.snippets(
                responseFields(new FieldDescriptor[]{
                        fieldWithPath("animalsCount").description("count of animals"),
                        fieldWithPath("housesCount").description("count of houses"),
                        fieldWithPath("employeesCount").description("count of employees"),
                })
        );

        this.mockMvc.perform(get("/api/v1/statistics")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void getFedAnimals() throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("getFedAnimals");

        this.mockMvc.perform(get("/api/v1/statistics/fed_animals")
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void getTaskTypes(Long id) throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("getTaskTypes");

        document.snippets(
                pathParameters(parameterWithName("id").description("id of employee")),
                responseFields(
                        fieldWithPath("[].taskType").description("ordinal of task status enum"),
                        fieldWithPath("[].count").description("count of tasks of this type"))
        );

        this.mockMvc.perform(get("/api/v1/statistics/employee_task_types/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void getTaskStatuses(Long id) throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("getTaskStatuses");

        document.snippets(
                pathParameters(parameterWithName("id").description("id of employee")),
                responseFields(
                        fieldWithPath("[].taskStatus").description("ordinal of task status enum"),
                        fieldWithPath("[].count").description("count of tasks of this type"))
        );

        this.mockMvc.perform(get("/api/v1/statistics/employee_task_statuses/{id}", id)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }
}
