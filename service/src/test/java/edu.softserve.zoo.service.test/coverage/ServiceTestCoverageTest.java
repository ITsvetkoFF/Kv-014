package edu.softserve.zoo.service.test.coverage;

import edu.softserve.zoo.service.config.ServiceConfig;
import edu.softserve.zoo.service.test.coverage.annotation.ServiceTest;
import edu.softserve.zoo.util.AppProfiles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;

import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static org.junit.Assert.assertTrue;

/**
 * This test was written to force the team to write tests for their US Services.
 * When the Test Class was written for certain Service it should be annotated with {@link edu.softserve.zoo.service.test.coverage.annotation.ServiceTest} annotation.
 * In annotation you have to specify the Service Class for which the test class for written.
 *
 * @author Andrii Abramov on 6/17/16.
 * @see edu.softserve.zoo.service.test.coverage.annotation.ServiceTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ServiceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
public class ServiceTestCoverageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ServiceTestCoverageTest.class);

    private static final String SERVICES_TESTS_BASE_PACKAGE = "edu.softserve.zoo.service.test.service";
    private static final String SERVICES_COVERED_MESSAGE = "Good job! Somebody wrote tests for {} in {} class.";
    private static final String SERVICES_NOT_COVERED_MESSAGE = "Warning! No tests for {}.";
    private static final String NO_SERVICES_FOUND = "No services were found in %s package. Maybe you should check package?";
    private static final String NOT_ALL_SERVICES_TESTED = "Not all services are covered by tests yet.";

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<?>, Optional<Class<?>>> testToServiceMapping;

    @Test
    public void testServiceCoverage() {

        final Map<String, Object> services = applicationContext.getBeansWithAnnotation(Service.class);

        assertTrue(format(NO_SERVICES_FOUND, SERVICES_TESTS_BASE_PACKAGE), services.size() > 0);

        testToServiceMapping = services.values().stream().collect(Collectors.toMap(
                Object::getClass,
                e -> Optional.empty() // type inference bug ?
        ));

        // false - to turn off basic include filter (e.g. @Component, @Service, etc.)
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ServiceTest.class));

        // found tests with @ServiceTest annotation
        final Set<BeanDefinition> serviceTestClasses = scanner.findCandidateComponents(SERVICES_TESTS_BASE_PACKAGE);

        serviceTestClasses
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::loadClass)
                .forEach(clazz -> {
                    final Class<?> forService = extractServiceFromTest(clazz);
                    testToServiceMapping.put(forService, Optional.of(clazz));
                });

        testToServiceMapping.entrySet().stream().forEach(this::checkIfTestPresent);

        final boolean allServicesCovered =
                testToServiceMapping.values().stream().allMatch(Optional::isPresent);


        assertTrue(NOT_ALL_SERVICES_TESTED, allServicesCovered);
    }

    private void checkIfTestPresent(Map.Entry<Class<?>, Optional<Class<?>>> entry) {
        final Optional<Class<?>> testClass = entry.getValue();
        final String serviceClassName = entry.getKey().getSimpleName();

        if (testClass.isPresent()) {
            final String testClassName = testClass.get().getSimpleName();
            LOGGER.info(SERVICES_COVERED_MESSAGE, serviceClassName, testClassName);
        } else {
            LOGGER.warn(SERVICES_NOT_COVERED_MESSAGE, serviceClassName);
        }
    }

    private Class<?> extractServiceFromTest(Class<?> testClass) {
        final ServiceTest annotation = AnnotationUtils.getAnnotation(testClass, ServiceTest.class);
        return annotation.forService();
    }

    private Class<?> loadClass(String className) {
        try {
            // null - use default ClassLoader
            return ClassUtils.forName(className, null);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Error while loading class: " + className, e);
        }
    }

}