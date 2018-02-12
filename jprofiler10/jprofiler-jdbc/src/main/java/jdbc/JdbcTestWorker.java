package jdbc;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.concurrent.Callable;

import javax.sql.DataSource;

public class JdbcTestWorker implements Callable<Integer> {

    private DataSource dataSource;
    private boolean leakConnection;
    private volatile boolean terminate;

    public JdbcTestWorker(DataSource dataSource, boolean leakConnection) {
        this.dataSource = dataSource;
        this.leakConnection = leakConnection;
    }

    @Override
    public Integer call() throws Exception {
        int count = 0;
        try {
            boolean gc = false;
            while (!Thread.interrupted() && !terminate) {
                if (gc) {
                    System.gc();
                    gc = false;
                }
                Connection connection = dataSource.getConnection();
                testStatementsPath1(connection);
                testStatementsPath2(connection);
                if (!leakConnection || count % 2 != 0) { // leak one connection approx. every 10 seconds
                    try {
                        connection.close();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } else {
                    gc = true;
                }
                count++;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return count;
    }

    private void testStatementsPath1(Connection connection) throws SQLException {
        testStatement(connection);
        testPreparedStatement(connection);
    }

    private void testStatementsPath2(Connection connection) throws SQLException {
        testStatement(connection);
        testPreparedStatement(connection);
    }

    private void testPreparedStatement(Connection connection) throws SQLException {
        PreparedStatement statement3 = connection.prepareStatement("UPDATE customer SET city=? WHERE city=? AND SLEEP(1)");
        PreparedStatement statement2 = connection.prepareStatement("UPDATE customer SET city=? WHERE city=? AND SLEEP(5)");
        PreparedStatement statement = connection.prepareStatement("SELECT * FROM customer WHERE city=?  AND SLEEP(1)");
        statement.setString(1, "Olten");
        statement.executeQuery();
        statement.setString(1, "New York");
        statement.executeQuery();

        statement2.setString(1, "New York 2");
        statement2.setString(2, "New York");
        statement2.execute();
        statement2.executeUpdate();

        statement3.setString(1, "New York 2");
        statement3.setString(2, "New York");
        statement3.addBatch();
        statement3.executeBatch();
    }

    private void testStatement(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();
        statement.executeQuery("SELECT * FROM customer WHERE city='New York' OR SLEEP(10)");
        statement.executeQuery("SELECT * FROM customer WHERE city='New York2' OR SLEEP(10)");
        statement.close();
    }

    public void terminate() {
        terminate = true;
    }

}
