package edu.softserve.zoo.annotation;

import edu.softserve.zoo.model.BaseEntity;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation maps Target type (exactly on {@code Class<? extends BaseDto>})
 *
 * @author Andrii Abramov on 25-May-16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dto {
    /**
     * Points on related Entity to which Target is mapped.
     */
    Class<? extends BaseEntity> value();
}
