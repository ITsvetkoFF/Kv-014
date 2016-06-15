package edu.softserve.zoo.persistence.config;

import edu.softserve.zoo.util.AppProfiles;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Spring configurations for {@link DataSource}.
 *
 * @author Andrii Abramov on 6/15/16.
 */
@Configuration
@PropertySource("classpath:persistence.properties")
public class DataSourceConfig {

    @Resource
    private Environment env;

    @Bean
    @Profile(AppProfiles.PRODUCTION)
    public DataSource productionDataSource() {
        final DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(env.getProperty("db.prod.url"));
        dataSource.setUsername(env.getProperty("db.prod.username"));
        dataSource.setPassword(env.getProperty("db.prod.password"));
        dataSource.setDriverClassName(env.getProperty("db.prod.driver"));
        return dataSource;
    }

    @Bean
    @Profile(AppProfiles.TEST)
    public DataSource testDataSource() {
        final String schemaScript = env.getProperty("db.schema");
        final String populateScript = env.getProperty("db.data");

        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        return builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts(schemaScript)
                .addScripts(populateScript)
                .build();
    }
}
