import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class XmlTestRunner extends ConnectionAndSelectTestProvider {


    private ClassPathXmlApplicationContext context;
    private Connection connection;


    @Before
    public void setUp() {
        context = new ClassPathXmlApplicationContext("persistence-test-ctx.xml");
        context.getEnvironment().setActiveProfiles("dev");
        context.refresh();

        final DataSource dataSource = (DataSource) context.getBean("dataSource");

        connection = super.getConnection(dataSource);

    }


    @Test
    public void testConnection() {
        notNull(connection, "Connection is not established.");
    }

    @Test
    public void testQuery() {
        boolean selectIsSuccess = super.testSelect(connection);

        isTrue(selectIsSuccess, "Result set is empty.");

    }

    @After
    public void tearDown() {
        super.closeConnection(connection);
        context.close();
    }

}
