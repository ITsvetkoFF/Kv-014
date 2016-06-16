package edu.softserve.zoo.persistence.test.coverage;

import edu.softserve.zoo.persistence.config.PersistenceConfig;
import edu.softserve.zoo.persistence.test.coverage.annotation.RepositoryTest;
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
import org.springframework.stereotype.Repository;
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
 * This test was written to force the team to write tests for their US Repositories.
 * When the Test Class was written for certain Repository it should be annotated with {@link RepositoryTest} annotation.
 * In annotation you have to specify the Repository Class for which the test class for written.
 *
 * @author Andrii Abramov on 6/15/16.
 * @see edu.softserve.zoo.persistence.test.coverage.annotation.RepositoryTest
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = PersistenceConfig.class)
@ActiveProfiles(AppProfiles.TEST)
public class RepositoryTestCoverageTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(RepositoryTestCoverageTest.class);

    private static final String REPOSITORIES_TESTS_BASE_PACKAGE = "edu.softserve.zoo.persistence.test.repository";
    private static final String REPOSITORY_COVERED_MESSAGE = "Good job! Somebody wrote tests for {} in {} class.";
    private static final String REPOSITORY_NOT_COVERED_MESSAGE = "Warning! No tests for {}.";
    private static final String NO_REPOSITORIES_FOUND = "No repositories were found in %s package. Maybe you should check package?";
    private static final String NOT_ALL_REPOSITORIES_TESTED = "Not all repositories covered by tests yet.";

    @Autowired
    private ApplicationContext applicationContext;

    private Map<Class<?>, Optional<Class<?>>> testToRepositoryMapping;

    @Test
    public void testRepositoryCoverage() {

        final Map<String, Object> repositories = applicationContext.getBeansWithAnnotation(Repository.class);

        assertTrue(format(NO_REPOSITORIES_FOUND, REPOSITORIES_TESTS_BASE_PACKAGE), repositories.size() > 0);

        testToRepositoryMapping = repositories.values().stream().collect(Collectors.toMap(
                Object::getClass,
                e -> Optional.empty() // type inference bug ?
        ));

        // false - to turn off basic include filter (e.g. @Component, @Service, etc.)
        final ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
        scanner.addIncludeFilter(new AnnotationTypeFilter(RepositoryTest.class));

        // found tests with @RepositoryTest annotation
        final Set<BeanDefinition> repositoryTestClasses = scanner.findCandidateComponents(REPOSITORIES_TESTS_BASE_PACKAGE);

        repositoryTestClasses
                .stream()
                .map(BeanDefinition::getBeanClassName)
                .map(this::loadClass)
                .forEach(clazz -> {
                    final Class<?> forRepository = extractRepositoryFromTest(clazz);
                    testToRepositoryMapping.put(forRepository, Optional.of(clazz));
                });

        testToRepositoryMapping.entrySet().stream().forEach(this::checkIfTestPresent);

        final boolean allRepositoriesCovered =
                testToRepositoryMapping.values().stream().allMatch(Optional::isPresent);


        assertTrue(NOT_ALL_REPOSITORIES_TESTED, allRepositoriesCovered);
    }

    private void checkIfTestPresent(Map.Entry<Class<?>, Optional<Class<?>>> entry) {
        final Optional<Class<?>> testClass = entry.getValue();
        final String repositoryClassName = entry.getKey().getSimpleName();

        if (testClass.isPresent()) {
            final String testClassName = testClass.get().getSimpleName();
            LOGGER.info(REPOSITORY_COVERED_MESSAGE, repositoryClassName, testClassName);
        } else {
            LOGGER.warn(REPOSITORY_NOT_COVERED_MESSAGE, repositoryClassName);
        }
    }

    private Class<?> extractRepositoryFromTest(Class<?> testClass) {
        final RepositoryTest annotation = AnnotationUtils.getAnnotation(testClass, RepositoryTest.class);
        return annotation.forRepository();
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