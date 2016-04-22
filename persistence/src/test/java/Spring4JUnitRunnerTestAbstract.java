import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.sql.DataSource;
import java.sql.Connection;

import static org.springframework.util.Assert.isTrue;
import static org.springframework.util.Assert.notNull;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath*:persistence-test-ctx.xml")
@ActiveProfiles("dev")
public class Spring4JUnitRunnerTestAbstract extends AbstractConnectionAndSelectTestProvider {

    @Autowired
    private DataSource dataSource;

    private Connection connection;


    @Before
    public void setUp() {

        connection = super.getConnection(dataSource);

    }


    @Test
    public void testConnection() {

        notNull(connection, "Connection is not established.");

    }

    @Test
    public void testQuery() {

        final boolean selectIsSuccess = super.testSelect(connection);
        isTrue(selectIsSuccess, "Result set is empty.");

    }

    @After
    public void tearDown() {

        super.closeConnection(connection);

    }

}
