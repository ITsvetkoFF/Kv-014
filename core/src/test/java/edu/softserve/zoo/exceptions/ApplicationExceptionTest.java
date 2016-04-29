package edu.softserve.zoo.exceptions;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

/**
 * Test class for application exception builder.
 *
 * @author Julia Siroshtan
 */
public final class ApplicationExceptionTest {

    private final static String MESSAGE = "Stub exception test message";

    @SuppressWarnings("ThrowableInstanceNeverThrown")
    private final static Throwable CAUSE = new Exception("Stub exception caused by this");

    @Test(expected = StubCustomException.class)
    public void buildExceptionTest() {
        final ApplicationException exception = ApplicationException.getBuilderFor(StubCustomException.class)
                .withMessage(MESSAGE)
                .causedBy(CAUSE)
                .build();

        assertEquals("Exception is constructed with wrong message", MESSAGE, exception.getMessage());
        assertEquals("Exception is constructed with wrong cause", CAUSE, exception.getCause());
        assertNull("Exception is constructed with wrong reason", exception.getReason());

        throw exception;
    }

    @SuppressWarnings("ThrowableResultOfMethodCallIgnored")
    @Test
    public void buildEmptyExceptionTest() {
        final ApplicationException exception = ApplicationException.getBuilderFor(StubCustomException.class)
                .build();

        assertNull("Exception message should be null", exception.getMessage());
        assertNull("Exception cause should be null", exception.getCause());
        assertNull("Exception reason should be null", exception.getReason());
    }
}
