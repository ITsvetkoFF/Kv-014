package edu.softserve.zoo.service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Set of Common Service Configurations in Application Context.
 *
 * @author Andrii Abramov on 6/13/16.
 */
@Configuration
@ComponentScan({"edu.softserve.zoo.service.impl", "edu.softserve.zoo.persistence", "edu.softserve.zoo.service.security"})
public class ServiceConfig {
    @Bean
    public PasswordEncoder bcryptEncoder() {
        return new BCryptPasswordEncoder();
    }
}
