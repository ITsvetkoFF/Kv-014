package edu.softserve.zoo.persistence.test.coverage.annotation;

import java.lang.annotation.*;

/**
 * Use this annotation to mark the Repository Test Class.
 *
 * @author Andrii Abramov on 6/15/16.
 * @see edu.softserve.zoo.persistence.test.coverage.RepositoryTestCoverageTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RepositoryTest {

    Class<?> forRepository();

}