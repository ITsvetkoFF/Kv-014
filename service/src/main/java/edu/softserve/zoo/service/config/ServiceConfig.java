package edu.softserve.zoo.service.config;

import edu.softserve.zoo.service.notifier.EmailEmployeeNotifier;
import edu.softserve.zoo.service.notifier.EmployeeNotifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.FileCopyUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.util.Properties;

/**
 * Set of Common Service Configurations in Application Context.
 *
 * @author Andrii Abramov on 6/13/16.
 */
@Configuration
@PropertySource({"classpath:email.properties","classpath:service-security.properties"})
@ComponentScan({"edu.softserve.zoo.service.impl", "edu.softserve.zoo.persistence", "edu.softserve.zoo.service.security"})
public class ServiceConfig {

    @javax.annotation.Resource
    private Environment env;

    @Value("classpath:password-email-template.html")
    private Resource passwordEmailResource;

    @Bean
    public EmployeeNotifier emailEmployeeNotifier() throws IOException, MessagingException {
        EmailEmployeeNotifier een = new EmailEmployeeNotifier();
        een.setPasswordMessageTemplate(passwordEmailMessageTemplate());
        een.setMailSender(javaMailSender());
        return een;
    }

    @Bean
    public static PropertySourcesPlaceholderConfigurer placeholderConfigurer() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        Properties props = new Properties();
        props.put("mail.smtp.auth", env.getProperty("email.server.require_auth"));
        props.put("mail.smtp.starttls.enable", env.getProperty("email.server.use_tls"));
        javaMailSender.setJavaMailProperties(props);

        javaMailSender.setHost(env.getProperty("email.server.address"));
        javaMailSender.setPort(Integer.parseInt(env.getProperty("email.server.port")));
        javaMailSender.setUsername(env.getProperty("email.server.username"));
        javaMailSender.setPassword(env.getProperty("email.server.password"));
        return javaMailSender;
    }

    @Bean
    public MimeMessageHelper passwordEmailMessageTemplate() throws IOException, MessagingException {
        MimeMessageHelper mmh = emailMessageTemplate();
        mmh.getMimeMessage().setContent(passwordHtmlTemplate(), "text/html; charset=utf-8");
        mmh.setSubject("Your account in Zoo Manager");
        return mmh;
    }

    @Bean
    public MimeMessageHelper emailMessageTemplate() throws IOException, MessagingException {

        MimeMessage mimeMessage = javaMailSender().createMimeMessage();

        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setFrom("zoo.manager.ua@gmail.com");
        return mimeMessageHelper;
    }

    @Bean
    public String passwordHtmlTemplate() throws IOException {
        return new String(FileCopyUtils.copyToByteArray(passwordEmailResource.getInputStream()));
    }

    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }

}
