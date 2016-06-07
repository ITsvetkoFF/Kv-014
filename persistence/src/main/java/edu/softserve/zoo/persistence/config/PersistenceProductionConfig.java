package edu.softserve.zoo.persistence.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

import javax.sql.DataSource;

/**
 * This Configuration is loaded when the 'production' profile is active.
 * Initializes the PostgreSQL DataSource (establishes connection)
 * <p>
 * Created by Andrii Abramov on 6/7/16.
 */
@Configuration
@Profile("production")
@Import(PersistenceConfig.class)
@PropertySource("classpath:persistence-prod.properties")
public class PersistenceProductionConfig {

    @Autowired
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        dataSource.setDriverClassName(env.getProperty("db.driver"));
        return dataSource;
    }

}
