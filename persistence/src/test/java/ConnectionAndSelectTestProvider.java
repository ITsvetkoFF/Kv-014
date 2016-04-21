import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public abstract class ConnectionAndSelectTestProvider {

    protected Connection getConnection(DataSource dataSource) {

        if (dataSource == null)
            return null;

        Connection connection = null;
        try {
            connection = dataSource.getConnection();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();
            closeConnection(connection);
        }
        return connection;

    }

    protected boolean testSelect(Connection connection) {

        if (connection == null)
            return false;

        boolean result = false;
        Statement selectStatement = null;
        ResultSet rs = null;
        try {
            selectStatement = connection.createStatement();
            rs = selectStatement.executeQuery("SELECT * FROM ZOO.CLASSES");
            result = rs.next();
        } catch (SQLException e) {
            System.out.println(e);
            e.printStackTrace();

        } finally {
            try {
                if (selectStatement != null) {
                    selectStatement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e1) {
                System.out.println(e1);
                e1.printStackTrace();
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
                e.printStackTrace();
            }
        }
    }

}
