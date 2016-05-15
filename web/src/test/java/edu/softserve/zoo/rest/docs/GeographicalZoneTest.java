package edu.softserve.zoo.rest.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;

import java.util.HashMap;
import java.util.Map;

import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
public class GeographicalZoneTest extends AbstractTest {
    private ObjectMapper objectMapper = new ObjectMapper();
    @Test
    public void run() throws Exception {
        Map<String, Object> zone = new HashMap<String, Object>() {{
            put("regionName", "Wanaka");
        }};

        getGeoZones();
        createGeoZone(zone);
        zone.put("regionName", "Sokovia");
        updateGeoZone(zone, 15L);
        deleteGeoZone(15L);
    }

    public void getGeoZones() throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("getGeoZones");

        document.snippets(responseFields(
                        fieldWithPath("[].id").description("id of created geozone"),
                        fieldWithPath("[].regionName").description("name of the geozone region"))
        );

        this.mockMvc.perform(get("/api/v1/geo_zone")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void createGeoZone(Map<String, Object> zone) throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("createGeoZone");

        document.snippets(requestFields(
                    fieldWithPath("regionName").description("name of the geozone region")),
                responseFields(
                        fieldWithPath("id").description("id of created geozone"),
                        fieldWithPath("regionName").description("name of the geozone region"))
        );

        this.mockMvc.perform(post("/api/v1/geo_zone")
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(zone)))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void updateGeoZone(Map<String, Object> zone, Long id) throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("updateGeoZone");

        document.snippets(pathParameters(
                    parameterWithName("id").description("id of geozone")),
            requestFields(
                    fieldWithPath("regionName").description("name of the geozone region")),
            responseFields(
                    fieldWithPath("id").description("id of updated geozone"),
                    fieldWithPath("regionName").description("name of updated geozone region"))
        );

        this.mockMvc.perform(put("/api/v1/geo_zone/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE).content(objectMapper.writeValueAsString(zone)))
                .andExpect(status().isOk())
                .andDo(document);
    }
    public void deleteGeoZone(Long id) throws Exception {

        RestDocumentationResultHandler document = documentPrettyPrintReqResp("deleteGeoZone");

        document.snippets(pathParameters(
                    parameterWithName("id").description("id of geozone"))
        );

        this.mockMvc.perform(delete("/api/v1/geo_zone/{id}", id)
                .contentType(MediaType.APPLICATION_JSON_VALUE))
                .andExpect(status().isOk())
                .andDo(document);
    }
}
