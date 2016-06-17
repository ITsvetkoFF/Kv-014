package edu.softserve.zoo.service.test.coverage.annotation;

import java.lang.annotation.*;

/**
 * Use this annotation to mark the Service Test Class.
 *
 * @author Andrii Abramov on 6/17/16.
 * @see edu.softserve.zoo.service.test.coverage.ServiceTestCoverageTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ServiceTest {

    Class<?> forService();

}