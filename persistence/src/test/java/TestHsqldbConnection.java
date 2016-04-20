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

    @Test
    public void textConnection() throws SQLException {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("persistence-test-ctx.xml");
        context.getEnvironment().setActiveProfiles("dev");
        context.refresh();

        final DataSource dataSource = (DataSource) context.getBean("dataSource");
        notNull(dataSource, "DataSource Bean is not loaded.");

        final Connection connection = dataSource.getConnection();
        notNull(connection, "Connection is not established.");

        final Statement selectStatement = connection.createStatement();

        final ResultSet rs = selectStatement.executeQuery("SELECT * FROM ZOO.CLASSES");

        isTrue(rs.next(), "Result set is emmpty.");

        rs.close();
        selectStatement.close();
        connection.close();
        connection.close();


    }

}
