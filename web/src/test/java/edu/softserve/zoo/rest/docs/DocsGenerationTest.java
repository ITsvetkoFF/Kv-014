package edu.softserve.zoo.rest.docs;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.softserve.zoo.annotation.DocsClassDescription;
import edu.softserve.zoo.annotation.DocsFieldDescription;
import edu.softserve.zoo.annotation.DocsParamDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.BaseDto;
import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.persistence.WebException;
import edu.softserve.zoo.util.Validator;
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
import org.springframework.http.ResponseEntity;
import org.springframework.restdocs.RestDocumentation;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.request.ParameterDescriptor;
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

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.*;
import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Taras Zubrei
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:spring/web-test-ctx.xml")
@WebAppConfiguration
public class DocsGenerationTest {
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
    private static final Logger LOGGER = LoggerFactory.getLogger(DocsGenerationTest.class);

    private MockMvc mockMvc;

    /**
     * Set up MockMVC
     */
    @Before
    public void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.context)
                .apply(documentationConfiguration(this.restDocumentation))
                .build();
    }

    private RestDocumentationResultHandler documentPrettyPrintReqResp(String useCase) {
        return document(useCase,
                preprocessRequest(prettyPrint()),
                preprocessResponse(prettyPrint()));
    }

    @Test
    public void run() throws Exception {
        StringBuilder documentation = new StringBuilder();
        documentation.append("= Zoo REST API Documentation\n")
                .append("Taras Zubrei;\n")
                .append(":doctype: book\n")
                .append(":icons: font\n")
                .append(":source-highlighter: highlightjs\n")
                .append(":toc: left\n")
                .append(":toclevels: 4\n")
                .append(":sectlinks:\n")
                .append("\n")
                .append("[introduction]\n")
                .append("= Introduction\n")
                .append("\n")
                .append("RESTful API for zoo\n")
                .append("\n")
                .append("[[overview]]\n")
                .append("= Overview\n")
                .append("\n")
                .append("[[overview-http-verbs]]\n")
                .append("== HTTP verbs\n")
                .append("All controllers try to adhere as closely as possible to standard HTTP and REST conventions in its\n")
                .append("use of HTTP verbs.\n")
                .append("|===\n")
                .append("| Verb | Usage\n")
                .append("\n")
                .append("| `GET`\n")
                .append("| Used to retrieve a resource\n")
                .append("\n")
                .append("| `POST`\n")
                .append("| Used to create a new resource\n")
                .append("\n")
                .append("| `PATCH`\n")
                .append("| Used to update an existing resource, including partial updates\n")
                .append("\n")
                .append("| `DELETE`\n")
                .append("| Used to delete an existing resource\n")
                .append("|===\n")
                .append("\n")
                .append("[[overview-http-status-codes]]\n")
                .append("== HTTP status codes\n")
                .append("All controllers try to adhere as closely as possible to standard HTTP and REST conventions in its\n")
                .append("use of HTTP status codes.\n")
                .append("\n")
                .append("|===\n")
                .append("| Status code | Usage\n")
                .append("\n")
                .append("| `200 OK`\n")
                .append("| Standard response for successful HTTP requests. The actual response will depend on the request method used. In a GET request, the response will contain an entity corresponding to the requested resource. In a POST request, the response will contain an entity describing or containing the result of the action.\n")
                .append("\n")
                .append("| `404 Not Found`\n")
                .append("| The requested resource could not be found but may be available again in the future. Subsequent requests by the client are permissible.\n")
                .append("|===\n")
                .append("\n")
                .append("[[resources]]\n")
                .append("= Resources\n");
        for (Map.Entry<RequestMappingInfo, HandlerMethod> mapping :
                requestMappingHandlerMapping.getHandlerMethods()
                        .entrySet()
                        .stream()
                        .sorted((a, b) -> a.getKey().getMethodsCondition().getMethods().stream().findFirst().get() == RequestMethod.DELETE ? 1 : -1)
                        .collect(Collectors.toList())) {

            final RequestMethod requestMethod = mapping.getKey().getMethodsCondition().getMethods().stream().findFirst().get();
            if (!Arrays.asList(RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PATCH,
                    RequestMethod.POST, RequestMethod.PUT).contains(requestMethod))
                throw ApplicationException.getBuilderFor(WebException.class)
                        .withMessage("Not supported Request Method")
                        .build();

            final String path = mapping.getKey().getPatternsCondition().getPatterns().stream().findFirst().get();
            LOGGER.info(requestMethod + ": " + path);

            final ParameterDescriptor[] pathParameters = Arrays.stream(mapping.getValue().getMethodParameters())
                    .filter(parameter -> parameter.getParameterAnnotation(PathVariable.class) != null)
                    .map(parameter -> {
                        parameter.initParameterNameDiscovery(parameterNameDiscoverer);
                        return parameter;
                    })
                    .map(parameter -> new ImmutablePair<>(parameter.getParameterName(), parameter.getParameterAnnotation(DocsParamDescription.class)))
                    .map(pair -> {
                        Validator.notNull(pair.right, ApplicationException.getBuilderFor(WebException.class).withMessage("Every Path Variable parameter must be annotated with '" + DocsParamDescription.class.getSimpleName() + "' annotation").build());
                        return pair;
                    })
                    .map(pair -> parameterWithName(pair.left).description(pair.right.value()))
                    .toArray(ParameterDescriptor[]::new);

            boolean isArray = false;
            Type generic = mapping.getValue().getMethod().getGenericReturnType();
            Class dtoClass;
            if (generic instanceof ParameterizedType) {
                dtoClass = (Class) ((ParameterizedType) generic).getActualTypeArguments()[0]; //TODO: Reflection Utils in Spring ??What method??
                isArray = true;
            } else {
                dtoClass = (Class) generic;
            }

            List<Field> fields = new LinkedList<>(Arrays.asList(dtoClass.getDeclaredFields()));
            if (BaseDto.class.equals(dtoClass.getSuperclass())) {
                fields.addAll(0, Arrays.asList(dtoClass.getSuperclass().getDeclaredFields()));
            }

            boolean finalIsArray = isArray;
            final FieldDescriptor[] responseFields = !ResponseEntity.class.equals(dtoClass) ? fields.stream()
                    .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsFieldDescription.class)))
                    .map(pair -> {
                        Validator.notNull(pair.right, ApplicationException.getBuilderFor(WebException.class).withMessage("Every field must be annotated with '" + DocsFieldDescription.class.getSimpleName() + "' annotation").build());
                        return pair;
                    })
                    .filter(pair -> !pair.right.optional())
                    .map(pair -> fieldWithPath((finalIsArray ? "[]." : "") + pair.left.getName()).description(pair.right.value()))
                    .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};

            Optional<MethodParameter> methodParameter = Arrays.stream(mapping.getValue().getMethodParameters())
                    .filter(parameter -> parameter.getParameterAnnotation(RequestBody.class) != null)
                    .findFirst();

            final FieldDescriptor[] requestFields = methodParameter.isPresent() ? Arrays.stream(methodParameter
                    .get()
                    .getParameterType()
                    .getDeclaredFields())
                    .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsFieldDescription.class)))
                    .map(pair -> {
                        Validator.notNull(pair.right, ApplicationException.getBuilderFor(WebException.class).withMessage("Every field must be annotated with '" + DocsFieldDescription.class.getSimpleName() + "' annotation").build());
                        return pair;
                    })
                    .filter(pair -> !pair.right.optional())
                    .map(pair -> fieldWithPath(pair.left.getName()).description(pair.right.value()))
                    .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};

            final String snippetsPath = "/" + requestMethod.toString().toLowerCase() + path.replaceAll("\\{id}", "id");
            RestDocumentationResultHandler document = documentPrettyPrintReqResp(snippetsPath);
            if (pathParameters.length > 0)
                document.snippets(pathParameters(pathParameters));
            if (responseFields.length > 0)
                document.snippets(responseFields(responseFields));
            if (requestFields.length > 0)
                document.snippets(requestFields(requestFields));
            Method testDto = ReflectionUtils.findMethod(mapping.getValue().getMethod().getDeclaringClass(), "getTestDto");
            if (Arrays.asList(RequestMethod.PATCH, RequestMethod.POST, RequestMethod.PUT).contains(requestMethod))
                Validator.notNull(testDto, ApplicationException.getBuilderFor(WebException.class).withMessage("'getTestDto' method is missing. It is needed for POST, PATCH and PUT").build());
            final DocsTest testValue = mapping.getValue().getMethod().getAnnotation(DocsTest.class);
            Validator.notNull(testValue, ApplicationException.getBuilderFor(WebException.class).withMessage("Every field must be annotated with '" + DocsTest.class.getSimpleName() + "' annotation").build());
            final Method httpMethod = ReflectionUtils.findMethod(RestDocumentationRequestBuilders.class, requestMethod.toString().toLowerCase(), String.class, Object[].class);
            this.mockMvc.perform(((MockHttpServletRequestBuilder) httpMethod.invoke(this, path, testValue.pathParameters()))
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(testDto != null ? objectMapper.writeValueAsString(testDto.invoke(this)) : ""))
                    .andExpect(status().isOk())
                    .andDo(document);

            Class entity = (Class) ((ParameterizedType) mapping.getValue().getMethod().getDeclaringClass().getGenericSuperclass()).getActualTypeArguments()[1];
            DocsClassDescription docsClassDescription = mapping.getValue().getMethod().getDeclaringClass().getAnnotation(DocsClassDescription.class);
            Validator.notNull(docsClassDescription, ApplicationException.getBuilderFor(WebException.class).withMessage("All rest controllers have to be annotated with '" + DocsClassDescription.class.getSimpleName() + "' annotation").build());
            documentation
                    .append("\n[[resource-")
                    .append(entity.getSimpleName())
                    .append("-")
                    .append(requestMethod.name().toLowerCase())
                    .append("]]\n== ")
                    .append(StringUtils.capitalize(requestMethod.name().toLowerCase()))
                    .append(" ")
                    .append(entity.getSimpleName())
                    .append("\n").append(docsClassDescription.value())
                    .append("\n");
            if (pathParameters.length > 0)
                documentation.append("=== Path parameters\n\ninclude::{snippets}")
                        .append(snippetsPath)
                        .append("/path-parameters.adoc[]\n");
            if (requestFields.length > 0)
                documentation.append("=== Request fields\n\ninclude::{snippets}")
                        .append(snippetsPath)
                        .append("/request-fields.adoc[]\n");
            if (requestFields.length > 0)
                documentation.append("=== Response fields\n\ninclude::{snippets}")
                        .append(snippetsPath)
                        .append("/response-fields.adoc[]\n");
            documentation.append("=== Example request\n\ninclude::{snippets}")
                    .append(snippetsPath)
                    .append("/curl-request.adoc[]\n");
            documentation.append("=== Example response\n\ninclude::{snippets}")
                    .append(snippetsPath)
                    .append("/http-response.adoc[]\n\n");
        }
        Files.write(Paths.get(System.getProperty("user.dir") + "/src/test/resources/asciidoc/documentation.adoc"), Arrays.asList(documentation.toString().split("\\n")), StandardOpenOption.TRUNCATE_EXISTING);
        //TODO: limit get all
    }

}
