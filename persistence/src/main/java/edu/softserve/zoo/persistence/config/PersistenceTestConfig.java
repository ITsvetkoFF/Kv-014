package edu.softserve.zoo.persistence.config;

import org.springframework.context.annotation.*;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabase;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * This Configuration is loaded when the 'test' profile is active.
 * Initializes the HSQLDB DataSource (creates schema, populates database)
 * <p>
 * Created by Andrii Abramov on 6/7/16.
 */
@Configuration
@Profile("test")
@Import(PersistenceConfig.class)
@PropertySource("classpath:persistence-hsqldb.properties")
public class PersistenceTestConfig {

    @Resource
    private Environment env;

    @Bean
    public DataSource dataSource() {
        final String schemaScript = env.getProperty("db.schema_script");
        final String populateScript = env.getProperty("db.populate_script");
        System.err.println(schemaScript);
        System.err.println(populateScript);
        final EmbeddedDatabaseBuilder builder = new EmbeddedDatabaseBuilder();
        final EmbeddedDatabase db = builder
                .setType(EmbeddedDatabaseType.HSQL)
                .addScripts(schemaScript)
                .addScripts(populateScript)
                .build();
        return db;
    }

}
