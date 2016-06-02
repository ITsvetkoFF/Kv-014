package edu.softserve.zoo.rest.docs;

import edu.softserve.zoo.annotation.DocsDescription;
import edu.softserve.zoo.annotation.DocsTest;
import edu.softserve.zoo.dto.AnimalDto;
import edu.softserve.zoo.dto.BaseDto;
import org.apache.commons.lang3.tuple.ImmutablePair;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.documentationConfiguration;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.get;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
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
        for (Map.Entry<RequestMappingInfo, HandlerMethod> mapping :
                requestMappingHandlerMapping.getHandlerMethods()
                        .entrySet()
                        .stream()
                        .sorted((a, b) -> a.getKey().getMethodsCondition().getMethods().stream().findFirst().get() == RequestMethod.DELETE ? 1 : -1)
                        .collect(Collectors.toList())) {

            final RequestMethod requestMethod = mapping.getKey().getMethodsCondition().getMethods().stream().findFirst().get();
            if (! Arrays.asList(RequestMethod.GET, RequestMethod.DELETE, RequestMethod.PATCH,
                    RequestMethod.POST, RequestMethod.PUT).contains(requestMethod))
                return;

            final ParameterDescriptor[] pathParameters = Arrays.stream(mapping.getValue().getMethodParameters())
                    .map(parameter -> {
                        parameter.initParameterNameDiscovery(parameterNameDiscoverer);
                        return parameter;
                    })
                    .map(parameter -> parameterWithName(parameter.getParameterName()).description("the desc")) //TODO: Get real description
                    .toArray(ParameterDescriptor[]::new);

            boolean isArray = false;
            Type generic = mapping.getValue().getMethod().getGenericReturnType();
            Class dtoClass;
            if (generic instanceof ParameterizedType) {
                dtoClass = (Class) ((ParameterizedType) generic).getActualTypeArguments()[0];
                isArray = true;
            }
            else
                dtoClass = (Class) generic;

            List<Field> fields = new LinkedList<>(Arrays.asList(dtoClass.getDeclaredFields()));
            if (BaseDto.class.equals(dtoClass.getSuperclass())) {
                fields.addAll(0, Arrays.asList(dtoClass.getSuperclass().getDeclaredFields()));
            }

            boolean finalIsArray = isArray;
            final FieldDescriptor[] responseFields =  !ResponseEntity.class.equals(dtoClass) ? fields.stream()
                    .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsDescription.class))) //TODO: AntiVandal tests
                    .map(pair -> fieldWithPath( (finalIsArray ? "[]." : "") + pair.left.getName()).description(pair.right.value()))
                    .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};
            System.out.println("return = " + dtoClass);
            final String path = mapping.getKey().getPatternsCondition().getPatterns().stream().findFirst().get();
            System.out.println(requestMethod+": "+path);

            Optional<MethodParameter> methodParameter = Arrays.stream(mapping.getValue().getMethodParameters())
                    .filter(parameter -> parameter.getParameterAnnotation(RequestBody.class) != null)
                    .findFirst();

            final FieldDescriptor[] requestFields = methodParameter.isPresent() ? Arrays.stream(methodParameter
                    .get()
                    .getParameterType()
                    .getDeclaredFields())
                    .map(field -> new ImmutablePair<>(field, field.getAnnotation(DocsDescription.class)))
                    .map(pair -> fieldWithPath(pair.left.getName()).description(pair.right.value()))
                    .toArray(FieldDescriptor[]::new) : new FieldDescriptor[]{};

            RestDocumentationResultHandler document = documentPrettyPrintReqResp(requestMethod.toString().toLowerCase() + path);
            if (pathParameters.length > 0)
                document.snippets(pathParameters(pathParameters));
            if (responseFields.length > 0)
                document.snippets(responseFields(responseFields));
            if (requestFields.length > 0)
                document.snippets(requestFields(requestFields));

            final DocsTest testValue = mapping.getValue().getMethod().getAnnotation(DocsTest.class);
            final Method httpMethod = RestDocumentationRequestBuilders.class.getMethod(requestMethod.toString().toLowerCase(), String.class,Object[].class);
            if (requestMethod == RequestMethod.GET || requestMethod == RequestMethod.DELETE) {
                this.mockMvc.perform(((MockHttpServletRequestBuilder) httpMethod.invoke(this, path, testValue.pathParameters()))
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk())
                        .andDo(document);
            }
        }
    }

}
