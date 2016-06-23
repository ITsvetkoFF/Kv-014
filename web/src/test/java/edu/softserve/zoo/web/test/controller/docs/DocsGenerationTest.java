package edu.softserve.zoo.web.test.controller.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.annotation.*;
import edu.softserve.zoo.controller.rest.EmployeeRestController;
import edu.softserve.zoo.controller.rest.UserController;
import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.dto.TaskDto;
import edu.softserve.zoo.exception.DocsGenerationException;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.util.Validator;
import edu.softserve.zoo.web.test.config.WebTestConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.http.MediaType;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.operation.preprocess.ContentModifyingOperationPreprocessor;
import org.springframework.restdocs.operation.preprocess.OperationPreprocessor;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import javax.servlet.Filter;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static edu.softserve.zoo.controller.rest.Routes.LOGIN;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.post;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebTestConfig.class)
@ActiveProfiles(AppProfiles.TEST)
@WebAppConfiguration
public class DocsGenerationTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(DocsGenerationTest.class);
    @Rule
    public final RestDocumentation restDocumentation = new RestDocumentation(
            "build/generated-snippets"
    );
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;
    @Autowired
    private ParameterNameDiscoverer parameterNameDiscoverer;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private List<Filter> springSecurityFilters;
    @Resource(name = "webTestProperties")
    private Properties properties;
    private MockMvc mockMvc;

    /**
     * Set up MockMVC
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .addFilters(springSecurityFilters.toArray(new Filter[springSecurityFilters.size()]))
                .build();
    }

    private RestDocumentationResultHandler documentPrettyPrintReqResp(String useCase) {
        return document(useCase,
                preprocessRequest(prettyPrint()),
                preprocessResponse(limit(3), prettyPrint()));
    }

    @Test
    public void run() throws Exception {
        StringBuilder documentation = new StringBuilder(properties.getProperty("fileBegin"));
        String token = this.mockMvc.perform(post(LOGIN)
                .contentType(MediaType.APPLICATION_JSON)
                .header("Username", "ITSV@GMAIL.COM")
                .header("Password", "123456"))
                .andExpect(status().isOk())
                .andReturn().getResponse().getHeader("X-Auth-Token");
        System.out.println(token);
        for (Map.Entry<RequestMappingInfo, HandlerMethod> mapping :
                requestMappingHandlerMapping.getHandlerMethods()
                        .entrySet()
                        .stream()
                        .sorted((a, b) -> a.getKey().getMethodsCondition().getMethods().stream().findFirst().get() == RequestMethod.DELETE ? 1 : -1)
                        .collect(Collectors.toList())) {

            final RequestMethod requestMethod = mapping.getKey().getMethodsCondition().getMethods().stream().findFirst().get();

            if (!Arrays.asList(RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PATCH,
                    RequestMethod.POST, RequestMethod.PUT).contains(requestMethod))
                throw ApplicationException.getBuilderFor(DocsGenerationException.class)
                        .withMessage("Not supported Request Method")
                        .build();

            final String path = mapping.getKey().getPatternsCondition().getPatterns().stream().findFirst().get();
            LOGGER.info(requestMethod + ": " + path);
            final boolean isArray = mapping.getValue().getMethod().getGenericReturnType() instanceof ParameterizedType;
            final Class dtoClass = getDtoClass(mapping.getValue());
            final ParameterDescriptor[] pathParameters = getPathParameters(mapping.getValue());
            final FieldDescriptor[] requestFields = getRequestFields(mapping.getValue());
            final FieldDescriptor[] responseFields = getResponseFields(isArray, dtoClass, mapping.getValue().getMethod().getDeclaringClass());
            String snippetsPath = requestMethod.toString().toLowerCase() + StringUtils.remove(StringUtils.remove(path, '{'), '}');

            final RestDocumentationResultHandler document = documentPrettyPrintReqResp(snippetsPath);
            if (pathParameters.length > 0)
                document.snippets(pathParameters(pathParameters));
            if (responseFields.length > 0)
                document.snippets(responseFields(responseFields));
            if (requestFields.length > 0)
                document.snippets(requestFields(requestFields));

            String testDto = properties.getProperty("dto." + dtoClass.getSimpleName());
            if (Arrays.asList(RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT).contains(requestMethod))
                Validator.notNull(testDto, ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage("'dto." + dtoClass.getSimpleName() + "' property is missing. It is needed for POST, PATCH and PUT").build());
            else
                testDto = null;
            final DocsTest testValue = mapping.getValue().getMethod().getAnnotation(DocsTest.class);
            Validator.notNull(testValue, ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage(String.format("Every method with request mappings must be annotated with '%s' annotation. Problem method: %s", DocsTest.class.getSimpleName(), mapping.getValue().getMethod())).build());
            final Method httpMethod = ReflectionUtils.findMethod(RestDocumentationRequestBuilders.class, requestMethod.toString().toLowerCase(), String.class, Object[].class);
            this.mockMvc.perform(((MockHttpServletRequestBuilder) httpMethod.invoke(this, path, testValue.pathParameters()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .header("X-Auth-Token", token)
                    .content(testDto != null ? testDto : ""))
                    .andExpect(status().isOk())
                    .andDo(document);

            Class entity;
            if (mapping.getValue().getMethod().getDeclaringClass().getGenericSuperclass() instanceof ParameterizedType)
                entity = (Class) ((ParameterizedType) mapping.getValue().getMethod().getDeclaringClass().getGenericSuperclass()).getActualTypeArguments()[1];
            else
                entity = dtoClass;
            DocsClassDescription docsClassDescription = mapping.getValue().getMethod().getDeclaringClass().getAnnotation(DocsClassDescription.class);
            Validator.notNull(docsClassDescription, ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage(String.format("All rest controllers have to be annotated with '%s' annotation. Problem class: %s", DocsClassDescription.class.getSimpleName(), mapping.getValue().getMethod().getDeclaringClass())).build());
            snippetsPath = "/" + snippetsPath;
            documentation
                    .append(String.format("\n[[resource-%s-%s]]\n== %s - %s\n", entity.getSimpleName(), StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(mapping.getValue().getMethod().getName()), '-'), entity.getSimpleName(), StringUtils.join(StringUtils.splitByCharacterTypeCamelCase(mapping.getValue().getMethod().getName()), ' ')))
                    .append(docsClassDescription.value())
                    .append("\n\n");
            if (pathParameters.length > 0)
                documentation.append("=== Path parameters\n\n")
                        .append(String.format("include::{snippets}%s/path-parameters.adoc[]\n", snippetsPath));
            if (requestFields.length > 0)
                documentation.append("=== Request fields\n\n")
                        .append(String.format("include::{snippets}%s/request-fields.adoc[]\n", snippetsPath));
            if (responseFields.length > 0)
                documentation.append("=== Response fields\n\n")
                        .append(String.format("include::{snippets}%s/response-fields.adoc[]\n", snippetsPath));
            documentation.append("=== Example request\n\n")
                    .append(String.format("include::{snippets}%s/curl-request.adoc[]\n", snippetsPath));
            documentation.append("=== Example response\n\n")
                    .append(String.format("include::{snippets}%s/http-response.adoc[]\n\n", snippetsPath));
        }
        File file = new File(System.getProperty("user.dir") + "/src/test/resources/asciidoc/documentation.adoc");
        if (!file.exists())
            file.createNewFile();
        Files.write(file.toPath(), Arrays.asList(documentation.toString().split("\\n")), StandardOpenOption.TRUNCATE_EXISTING);
    }

    private FieldDescriptor[] getRequestFields(HandlerMethod method) {
        Optional<MethodParameter> methodParameter = Arrays.stream(method.getMethodParameters())
                .filter(parameter -> parameter.getParameterAnnotation(RequestBody.class) != null)
                .findFirst();
        return methodParameter.isPresent() ? Arrays.stream(methodParameter
                .get()
                .getParameterType()
                .getDeclaredFields())
                .map(field -> {
                    Validator.notNull(field.getAnnotation(DocsFieldDescription.class), ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage(String.format("Every field must be annotated with '%s' annotation. Problem field: %s", DocsFieldDescription.class.getSimpleName(), field)).build());
                    return field;
                })
                .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsFieldDescription.class)))
                .filter(pair -> !pair.right.optional())
                .map(pair -> fieldWithPath(pair.left.getName()).description(pair.right.value()))
                .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};
    }

    private FieldDescriptor[] getResponseFields(final boolean isArray, Class<?> dtoClass, Class<?> controller) {
        List<Field> fields = new LinkedList<>(Arrays.asList(dtoClass.getDeclaredFields()));
        if (BaseDto.class.equals(dtoClass.getSuperclass())) {
            fields.addAll(0, Arrays.asList(dtoClass.getSuperclass().getDeclaredFields()));
        }
        return dtoClass.getAnnotation(Dto.class) != null || dtoClass.getAnnotation(IrrespectiveDto.class) != null ? fields.stream()
                .map(field -> {
                    Validator.notNull(field.getAnnotation(DocsFieldDescription.class), ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage(String.format("Every field must be annotated with '%s' annotation. Problem field: %s", DocsFieldDescription.class.getSimpleName(), field)).build());
                    return field;
                })
                .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsFieldDescription.class)))
                .filter(pair -> !pair.right.optional() && (!TaskDto.class.equals(dtoClass) && !EmployeeRestController.class.equals(controller)))
                .map(pair -> fieldWithPath((isArray ? "[]." : "") + pair.left.getName()).description(pair.right.value()))
                .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};
    }

    private ParameterDescriptor[] getPathParameters(HandlerMethod method) {
        return Arrays.stream(method.getMethodParameters())
                .filter(parameter -> parameter.getParameterAnnotation(PathVariable.class) != null)
                .map(parameter -> {
                    parameter.initParameterNameDiscovery(parameterNameDiscoverer);
                    return parameter;
                })
                .map(parameter -> {
                    Validator.notNull(parameter.getParameterAnnotation(DocsParamDescription.class), ApplicationException.getBuilderFor(DocsGenerationException.class).withMessage(String.format("Every Path Variable parameter must be annotated with '%s' annotation. Problem parameter: %s in %s", DocsParamDescription.class.getSimpleName(), parameter.getParameterName(), parameter.getMethod())).build());
                    return parameter;
                })
                .map(parameter -> new ImmutablePair<>(parameter.getParameterName(), parameter.getParameterAnnotation(DocsParamDescription.class)))
                .map(pair -> parameterWithName(pair.left).description(pair.right.value()))
                .toArray(ParameterDescriptor[]::new);
    }

    private Class<?> getDtoClass(HandlerMethod method) {
        Type generic = method.getMethod().getGenericReturnType();
        Class dtoClass;
        if (generic instanceof ParameterizedType) {
            dtoClass = (Class) ((ParameterizedType) generic).getActualTypeArguments()[0];
        } else {
            dtoClass = (Class) generic;
        }
        return dtoClass;
    }

    private OperationPreprocessor limit(int limit) {
        return new ContentModifyingOperationPreprocessor((originalContent, contentType) -> {
            try {
                if (originalContent.length > 0 && Objects.equals("[", new String(originalContent, 0, 1))) {
                    return objectMapper.writeValueAsBytes(objectMapper.readValue(originalContent, List.class).stream().limit(limit).collect(Collectors.toList()));
                } else
                    return originalContent;
            } catch (IOException ex) {
                return originalContent;
            }
        });
    }

}
