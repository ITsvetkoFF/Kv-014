import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class AbstractConnectionAndSelectTestProvider {

    protected Connection getConnection(DataSource dataSource) {

        if (dataSource == null) {
            return null;
        }

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            closeConnection(connection);
        }
        return connection;

    }

    protected boolean testSelect(Connection connection) {

        if (connection == null) {
            return false;
        }

        boolean result = false;
        Statement selectStatement = null;
        ResultSet resultSet = null;
        try {
            selectStatement = connection.createStatement();
            final String query = "SELECT COUNT(1)FROM ZOO.CLASSES";
            resultSet = selectStatement.executeQuery(query);
            result = resultSet.next();
        } catch (SQLException e) {
            System.out.println(e);
        } finally {
            try {
                if (selectStatement != null) {
                    selectStatement.close();
                }
                if (resultSet != null) {
                    resultSet.close();
                }
            } catch (SQLException e1) {
                System.out.println(e1);
            }
        }

        return result;

    }

    protected void closeConnection(Connection connection) {

        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                System.out.println(e);
            }
        }

    }

}
