package edu.softserve.zoo.web.test.i18n;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.exceptions.NotFoundException;
import edu.softserve.zoo.exceptions.ValidationException;
import edu.softserve.zoo.persistence.exception.PersistenceProviderException;
import edu.softserve.zoo.persistence.exception.SpecificationException;
import edu.softserve.zoo.service.exception.AnimalException;
import edu.softserve.zoo.service.exception.HouseException;
import edu.softserve.zoo.service.exception.WarehouseException;
import edu.softserve.zoo.service.exception.ZooZoneException;
import edu.softserve.zoo.util.AppProfiles;
import edu.softserve.zoo.web.test.config.WebTestConfig;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.stream.Stream;

/**
 * Test to check the presence of localized messages of {@link ExceptionReason} successors
 *
 * @author Serhii Alekseichenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebTestConfig.class)
@WebAppConfiguration
@ActiveProfiles(AppProfiles.TEST)
public class I18nTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nTest.class);
    private static final String UKRAINIAN = "uk";

    @Autowired
    MessageSource messageSource;

    @Test
    public void i18nMessageTest() {
        List<Class<? extends ApplicationException>> exceptions = Arrays.asList(
                NotFoundException.class,
                ValidationException.class,
                PersistenceProviderException.class,
                SpecificationException.class,
                WarehouseException.class,
                ZooZoneException.class,
                AnimalException.class,
                HouseException.class);

        // filters collection of Exceptions by ExceptionReason and retrieves each reason from it
        exceptions.stream()
                .map(Class::getDeclaredClasses)
                .flatMap(Stream::of)
                .filter(ExceptionReason.class::isAssignableFrom)
                .flatMap(enums -> Stream.of(enums.getEnumConstants()))
                .forEach(reason -> getMessages((ExceptionReason) reason));
    }

    private void getMessages(ExceptionReason reason) {
        try {
            messageSource.getMessage(reason.getMessage(), null, Locale.ENGLISH);
            messageSource.getMessage(reason.getMessage(), null, Locale.forLanguageTag(UKRAINIAN));
        } catch (NoSuchMessageException e) {
            LOGGER.info(e.getMessage());
            Assert.fail("Exception reason without localized message founded: " + reason);
        }
    }
}
