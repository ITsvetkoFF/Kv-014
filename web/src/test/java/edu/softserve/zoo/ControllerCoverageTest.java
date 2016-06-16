package edu.softserve.zoo;

import edu.softserve.zoo.util.AppProfiles;
import org.junit.Assert;
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
import org.springframework.stereotype.Controller;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * This test was written to force the team to test their US Controllers.
 * When the Test Class was written for certain Controller it should be annotated with {@link ControllerTest} annotation.
 * In annotation you have to specify the Controller Class for which the test class for written.
 *
 * @author Andrii Abramov on 6/15/16.
 * @see ControllerTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/web-test-ctx.xml")
@ActiveProfiles(AppProfiles.TEST)
public class ControllerCoverageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(ControllerCoverageTest.class);

    private static final String controllerTestsBasePackage = "edu.softserve.zoo.web.test.controller.rest";
    private static final String CONTROLLER_COVERED_MESSAGE = "Good job! Somebody wrote tests for {} in {} class.";
    private static final String CONTROLLER_NOT_COVERED_MESSAGE = "Warning! No mapping for {}.";

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<?>, Optional<Class<?>>> testToControllerMapping = new HashMap<>();

    @Test
    public void testControllerCoverage() {

        final Map<String, Object> controllers = applicationContext.getBeansWithAnnotation(Controller.class);
        final Map<String, Object> restControllers = applicationContext.getBeansWithAnnotation(RestController.class);
        controllers.putAll(restControllers);

        Assert.assertTrue("Controllers were not found.", controllers.size() > 0);

        testToControllerMapping = restControllers.values().stream().collect(Collectors.toMap(
                Object::getClass,
                e -> Optional.empty() // type inference bug ?
        ));

        // false - to turn off basic include filter (e.g. @Component, @Service, etc.)
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(ControllerTest.class));

        scanner.findCandidateComponents(controllerTestsBasePackage)
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::loadClass)
                .forEach(clazz -> {
                    final ControllerTest annotation = AnnotationUtils.getAnnotation(clazz, ControllerTest.class);
                    testToControllerMapping.put(annotation.forController(), Optional.of(clazz));
                });

        testToControllerMapping.entrySet().stream().forEach(entry -> {
            final Optional<Class<?>> testClass = entry.getValue();
            if (testClass.isPresent()) {
                final String className = testClass.get().getName();
                LOGGER.info(CONTROLLER_COVERED_MESSAGE, entry.getKey(), className);
            } else {
                LOGGER.warn(CONTROLLER_NOT_COVERED_MESSAGE, entry.getKey());
            }
        });

        final boolean allControllersCovered =
                testToControllerMapping.values().stream().allMatch(Optional::isPresent);

        Assert.assertTrue("Not all controllers covered yet.", allControllersCovered);
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
