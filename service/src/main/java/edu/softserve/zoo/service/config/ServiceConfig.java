package edu.softserve.zoo.service.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * Set of Common Service Configurations in Application Context.
 *
 * @author Andrii Abramov on 6/13/16.
 */
@Configuration
@ComponentScan({"edu.softserve.zoo.service.impl", "edu.softserve.zoo.persistence"})
public class ServiceConfig {
}
