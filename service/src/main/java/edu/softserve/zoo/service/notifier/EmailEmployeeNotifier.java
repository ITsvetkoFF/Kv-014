package edu.softserve.zoo.service.notifier;

import edu.softserve.zoo.exceptions.ApplicationException;
import edu.softserve.zoo.model.Employee;
import edu.softserve.zoo.service.exception.NotifierException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;

import javax.mail.MessagingException;
import java.io.IOException;

/**
 * Implementation of {@link EmployeeNotifier} which uses email to communicate with employee.
 *
 * @author Ilya Doroshenko
 */
public class EmailEmployeeNotifier implements EmployeeNotifier {

    /**
     * Regular expression that matches for all percent symbols not followed by 's'. <br/>
     */
    private static final String SHIELD_PERCENT_REGEX = "(%)[^s]";

    private static final String UNSHIELDED_PERCENT = "%";

    private static final String SHIELDED_PERCENT = "%%";

    private static final String TEXT_HTML_TYPE = "text/html";

    private JavaMailSender mailSender;

    private MimeMessageHelper passwordMessageTemplate;

    /**
     * Sends email message with generated password to specified email. Called asynchronously.
     *
     * @param employee to send message to
     * @param password to include in message body
     */
    @Async
    public void sendPassword(Employee employee, String password) {

        try {
            passwordMessageTemplate.setTo(employee.getEmail());

            String text = (String) passwordMessageTemplate.getMimeMessage().getDataHandler().getContent();
            String formattedText = formatEmailText(text, employee.getFirstName(), password);
            passwordMessageTemplate.getMimeMessage().setContent(formattedText, TEXT_HTML_TYPE);

            mailSender.send(passwordMessageTemplate.getMimeMessage());

        } catch (MessagingException | IOException e) {

            throw ApplicationException.getBuilderFor(NotifierException.class)
                    .forReason(NotifierException.Reason.MESSAGE_SENDING_FAILED)
                    .withMessage(e.getMessage())
                    .build();
        }
    }

    /**
     * Fills html template placeholders with specified information.
     * As String.format() uses <code>%</code> symbol as placeholder, we need to shield unnecessary percent symbols.
     *
     * @param html   email html template
     * @param values to fill in
     * @return filled html template
     */
    private String formatEmailText(String html, Object... values) {
        html = html.replaceAll(SHIELD_PERCENT_REGEX, SHIELDED_PERCENT);
        return String.format(html, values).replace(SHIELDED_PERCENT, UNSHIELDED_PERCENT);
    }

    public JavaMailSender getMailSender() {
        return mailSender;
    }

    public void setMailSender(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    public MimeMessageHelper getPasswordMessageTemplate() {
        return passwordMessageTemplate;
    }

    public void setPasswordMessageTemplate(MimeMessageHelper passwordMessageTemplate) {
        this.passwordMessageTemplate = passwordMessageTemplate;
    }
}
