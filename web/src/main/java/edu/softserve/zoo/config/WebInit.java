package edu.softserve.zoo.config;

import edu.softserve.zoo.util.AppProfiles;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.filter.RequestContextFilter;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

/**
 * @author Taras Zubrei
 */
public class WebInit extends AbstractAnnotationConfigDispatcherServletInitializer {
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
        container.setInitParameter("spring.profiles.default", AppProfiles.TEST);
        AnnotationConfigWebApplicationContext servletContext =
                new AnnotationConfigWebApplicationContext();

        ServletRegistration.Dynamic dispatcher =
                container.addServlet("spring-mvc-dispatcher",
                        new DispatcherServlet(servletContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/");

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
