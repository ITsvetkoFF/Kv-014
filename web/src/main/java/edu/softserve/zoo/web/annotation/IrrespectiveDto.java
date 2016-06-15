package edu.softserve.zoo.web.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * This annotation should be used when
 * Target (exactly {@code Class<? extends BaseDto>}) has no related Entity.
 *
 * @author Andrii Abramov on 25-May-16.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface IrrespectiveDto {
}
