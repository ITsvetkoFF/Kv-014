import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

public class TestHsqldbConnection {

    private ClassPathXmlApplicationContext context;
    private Connection connection;


    @Before
    public void setUp() throws SQLException {
        context = new ClassPathXmlApplicationContext("persistence-test-ctx.xml");
        context.getEnvironment().setActiveProfiles("dev");
        context.refresh();

        final DataSource dataSource = (DataSource) context.getBean("dataSource");

        connection = dataSource.getConnection();

    }


    @Test
    public void testConnection() throws SQLException {
        notNull(connection, "Connection is not established.");

        final Statement selectStatement = connection.createStatement();
        final ResultSet rs = selectStatement.executeQuery("SELECT * FROM ZOO.CLASSES");

        isTrue(rs.next(), "Result set is empty.");

        rs.close();
        selectStatement.close();
    }

    @After
    public void tearDown() throws SQLException {
        connection.close();
        context.close();
    }

}
