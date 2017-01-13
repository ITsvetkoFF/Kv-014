package edu.softserve.zoo;

import java.lang.annotation.*;

/**
 * Use this annotation to mark the Controller Test Class.
 *
 * @author Andrii Abramov on 6/15/16.
 * @see ControllerCoverageTest
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ControllerTest {

    Class<?> forController();

}
