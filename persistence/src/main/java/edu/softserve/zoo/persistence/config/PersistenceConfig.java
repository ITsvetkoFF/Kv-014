package edu.softserve.zoo.persistence.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Base {@link Configuration} class for Persistence layer.
 * Forces Component scan in {@code edu.softserve.zoo.persistence} package.
 * Loads properties from {@code "classpath:persistence.properties"}
 *
 * @author Andrii Abramov on 6/15/16.
 */
@Configuration
@ComponentScan("edu.softserve.zoo.persistence")
@PropertySource("classpath:persistence.properties")
public class PersistenceConfig {
}
