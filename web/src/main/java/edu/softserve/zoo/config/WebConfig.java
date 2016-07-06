package edu.softserve.zoo.config;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import edu.softserve.zoo.converter.mapping.DtoMapperImpl;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.validation.InvalidRequestProcessor;
import edu.softserve.zoo.validation.impl.InvalidRequestProcessorImpl;
import org.springframework.beans.factory.config.PropertiesFactoryBean;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * @author Taras Zubrei
 */
@Configuration
@EnableWebMvc
@ComponentScan({"edu.softserve.zoo.controller.rest", "edu.softserve.zoo.converter",
        "edu.softserve.zoo.advice", "edu.softserve.zoo.service",
        "edu.softserve.zoo.persistence", "edu.softserve.zoo.web.security"})
@PropertySource({"classpath:web.properties", "classpath:security.properties"})
@ImportResource("classpath:spring/security-ctx.xml")
public class WebConfig extends WebMvcConfigurerAdapter {
    @Resource
    private Environment env;

    @Bean
    public DtoMapperImpl dtoMapper() {
        final DtoMapperImpl mapper = new DtoMapperImpl();
        mapper.setDtoPackage("edu.softserve.zoo.dto");
        return mapper;
    }

    @Bean
    public ObjectMapper objectMapper() {
        final Jackson2ObjectMapperBuilder jackson2ObjectMapperBuilder = new Jackson2ObjectMapperBuilder();
        jackson2ObjectMapperBuilder
                .serializationInclusion(JsonInclude.Include.NON_NULL)
                .deserializerByType(LocalDateTime.class,new LocalDateTimeDeserializer(DateTimeFormatter.ISO_DATE_TIME))
                .indentOutput(true)
                .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
        return jackson2ObjectMapperBuilder.build();
    }

    @Bean
    public InvalidRequestProcessor invalidRequestProcessor() {
        return new InvalidRequestProcessorImpl();
    }

    @Bean
    public AcceptHeaderLocaleResolver localeResolver() {
        return new AcceptHeaderLocaleResolver();
    }

    @Bean
    public ResourceBundleMessageSource messageSource() {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename(env.getProperty("i18n.messages"));
        messageSource.setDefaultEncoding(env.getProperty("i18n.encoding"));
        return messageSource;
    }

    private MappingJackson2HttpMessageConverter messageConverter() {
        final MappingJackson2HttpMessageConverter messageConverter = new MappingJackson2HttpMessageConverter();
        messageConverter.setObjectMapper(objectMapper());
        return messageConverter;
    }

    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {
        converters.add(messageConverter());
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        configurer.defaultContentType(MediaType.APPLICATION_JSON).ignoreAcceptHeader(true);
    }

    @Bean
    @Profile(AppProfiles.TEST)
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
        return new RequestMappingHandlerMapping();
    }

    @Bean
    @Profile(AppProfiles.TEST)
    public ParameterNameDiscoverer parameterNameDiscoverer() {
        return new DefaultParameterNameDiscoverer();
    }

    @Bean
    @Profile(AppProfiles.TEST)
    public PropertiesFactoryBean webTestProperties() {
        final PropertiesFactoryBean propertiesFactoryBean = new PropertiesFactoryBean();
        propertiesFactoryBean.setLocation(new ClassPathResource("asciidoc/web-test.properties"));
        return propertiesFactoryBean;
    }
}