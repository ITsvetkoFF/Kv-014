package edu.softserve.zoo.web.test.config;

import edu.softserve.zoo.config.WebConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Set of specific configurations for web test context.
 *
 * @author Andrii Abramov on 6/23/16.
 */
@Configuration
@Import(WebConfig.class)
@ComponentScan("edu.softserve.zoo.web.test.controller.endpoints.checking.checker")
public class WebTestConfig {

}
