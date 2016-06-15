package edu.softserve.zoo.persistence.config;

import org.hibernate.SessionFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate5.HibernateTransactionManager;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.io.IOException;
import java.util.Properties;

import static org.hibernate.cfg.AvailableSettings.*;

/**
 * Hibernate specific configurations.
 *
 * @author Andrii Abramov on 6/15/16.
 */
@Configuration
@EnableTransactionManagement
public class HibernateConfig {

    @Resource
    private Environment env;

    @Bean
    public SessionFactory sessionFactory(DataSource dataSource) throws IOException {
        LocalSessionFactoryBean bean = new LocalSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setHibernateProperties(hibernateProperties());
        bean.setPackagesToScan(env.getProperty("entities.package.base"));
        bean.afterPropertiesSet();
        return bean.getObject();
    }

    @Bean
    public PlatformTransactionManager transactionManager(SessionFactory sessionFactory) {
        return new HibernateTransactionManager(sessionFactory);
    }

    private Properties hibernateProperties() {
        final Properties properties = new Properties();
        properties.put(SHOW_SQL, env.getProperty("db.prod.show_sql"));
        properties.put(FORMAT_SQL, env.getProperty("db.prod.format_sql"));
        properties.put(DIALECT, env.getProperty("db.prod.dialect"));
        properties.put(DEFAULT_SCHEMA, env.getProperty("db.default_schema"));
        return properties;
    }
}
