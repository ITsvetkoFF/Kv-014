package edu.softserve.zoo.config;

import edu.softserve.zoo.util.AppProfiles;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;
import java.io.IOException;
import java.util.Properties;

/**
 * @author Taras Zubrei
 */

public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebInit.class);

    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class<?>[]{WebConfig.class};
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class<?>[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/"};
    }

    @Override
    public void onStartup(ServletContext container) throws ServletException {
        container.setInitParameter("spring.profiles.default", AppProfiles.PRODUCTION);
        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();

        ServletRegistration.Dynamic dispatcher =
                container.addServlet("spring-mvc-dispatcher",
                        new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

        /* Enabling CORS support */
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOrigin("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");

        /* Getting required token header name */
        try {
            Resource resource = new ClassPathResource("security.properties");
            Properties properties = PropertiesLoaderUtils.loadProperties(resource);
            config.addExposedHeader(properties.getProperty("token.header"));
        } catch (IOException e) {
            LOGGER.warn("Unable to read header name property from file: {}", e.getMessage());
        }

        source.registerCorsConfiguration("/**", config);
        FilterRegistration.Dynamic corsFilter = container.addFilter("corsFilter",
                new CorsFilter(source));
        corsFilter.addMappingForUrlPatterns(null, false, "/*");

        /* Needed to enable locale handling in security filters */
        FilterRegistration.Dynamic localizationFilter = container.addFilter("localizationFilter",
                new RequestContextFilter());
        localizationFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic securityFilterChain = container.addFilter("springSecurityFilterChain",
                new DelegatingFilterProxy());
        securityFilterChain.addMappingForUrlPatterns(null, false, "/*");

        super.onStartup(container);
    }
}