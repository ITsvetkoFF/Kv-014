package edu.softserve.zoo.web.test.controller.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.dto.InvalidRequestDto;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * This class is a base component for dealing with Illegal Request Testing.
 *
 * @author Andrii Abramov on 6/14/16.
 */

public abstract class AbstractValidationTest<T> {

    private static final Locale[] availableLocales = {Locale.ENGLISH, Locale.forLanguageTag("uk")};

    @Autowired
    private MessageSource messageSource;

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ObjectMapper objectMapper;

    private MockMvc mockMvc;

    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(context)
                .dispatchOptions(true)
                .build();
    }

    @Test
    public void testInvalidPut() throws Exception {

        final T requestBody = getRequestBody();
        final String body = objectMapper.writeValueAsString(requestBody);

        final String route = getRoute();

        for (final Locale locale : availableLocales) {

            final MockHttpServletRequestBuilder request = put(route)
                    .accept(APPLICATION_JSON_UTF8)
                    .locale(locale)
                    .contentType(APPLICATION_JSON_UTF8)
                    .content(body);

            final MvcResult response = mockMvc.perform(request)
                    .andExpect(status().is4xxClientError())
                    .andDo(print())
                    .andExpect(content().contentType(APPLICATION_JSON_UTF8))
                    .andReturn();

            final String responseBody = response.getResponse().getContentAsString();
            final InvalidRequestDto actualResponse = objectMapper.readValue(responseBody, InvalidRequestDto.class);
            final InvalidRequestDto expectedResponse = getExpectedInvalidRequestDto(locale);

            assertEquals(expectedResponse, actualResponse);

        }

    }

    /**
     * @return object to put into body of request.
     */
    public abstract T getRequestBody();

    /**
     * @param locale for messages
     * @return expected in REST response dto.
     */
    public abstract InvalidRequestDto getExpectedInvalidRequestDto(Locale locale);

    /**
     * @return route where we send request
     */
    public abstract String getRoute();

    /**
     * @param messageKey {@link MessageSource#getMessage(String, Object[], Locale)}
     * @param locale     {@link MessageSource#getMessage(String, Object[], Locale)}
     * @param params     {@link MessageSource#getMessage(String, Object[], Locale)}
     * @return message which will be shown to user.
     */
    protected String getExpectedMessage(String messageKey, Locale locale, Object... params) {
        return messageSource.getMessage(messageKey, params, locale);
    }

}
