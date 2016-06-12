import edu.softserve.zoo.config.WebConfig;
import edu.softserve.zoo.exception.WebReason;
import edu.softserve.zoo.exceptions.ExceptionReason;
import edu.softserve.zoo.persistence.exception.PersistenceReason;
import edu.softserve.zoo.service.exception.ServiceReason;
import edu.softserve.zoo.util.AppProfiles;
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

import java.util.Locale;

/**
 * Test to check the presence of localized messages of {@link ExceptionReason} successors
 *
 * @author Serhii Alekseichenko
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = WebConfig.class)
@WebAppConfiguration
@ActiveProfiles(AppProfiles.TEST)
public class I18nTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(I18nTest.class);
    private static final String UKRAINIAN = "uk";

    @Autowired
    MessageSource messageSource;

    @Test
    public void persistenceReasonTest() {
        getMessages(PersistenceReason.values());
    }

    @Test
    public void serviceReasonTest() {
        getMessages(ServiceReason.values());
    }

    @Test
    public void webReasonTest() {
        getMessages(WebReason.values());
    }

    private void getMessages(ExceptionReason[] reasons) {
        for (ExceptionReason reason : reasons) {
            try {
                messageSource.getMessage(reason.getMessage(), null, Locale.ENGLISH);
                messageSource.getMessage(reason.getMessage(), null, Locale.forLanguageTag(UKRAINIAN));
            } catch (NoSuchMessageException e) {
                LOGGER.info(e.getMessage());
                Assert.fail("Exception reason without localized message founded: " + reason);
            }
        }
    }
}
