package SQL;

import Utils.Constants;
import jdk.nashorn.internal.codegen.CompilerConstants;

import java.sql.*;

public class Connector {
    private static Connector instance;

    public static Connector getInstance() {
        if(instance == null)
        {
            instance = new Connector();
        }
        return instance;
    }

    private Connection connection = null;
    private boolean connectionEstablished = false;


    public void openConnection() throws ClassNotFoundException, SQLException {
        if(connection != null && !connection.isClosed())
        {
            return;
            //throw new RuntimeException("Połączenie jest już nawiązane.");
        }
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    Constants.DB_USERNAME, Constants.DB_PASSWORD);
            System.out.println("Połączono z bazą danych");
            connectionEstablished = true;
        } catch (SQLException e) {
            System.out.println("Błąd wykonania połączenia " + e.getMessage());
        }
    }

    public void closeConnection() throws SQLException {
        if(connection != null)
        {
            connection.close();
            connectionEstablished = false;
            System.out.println("Zamknięto połączenie z bazą danych.");
        }else
        {
            System.out.println("Połączenie z bazą danych nie istniało.");
        }

    }

    public StatementResult executeQuery(String query)
    {
        try {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery(query);
            return new StatementResult(statement, resultSet);
        } catch (SQLException e) {
            return null;
        }
    }

    public CallableStatement performCall(CallableStatement call)
    {
        boolean failure = false;
        try {
            call.execute();
            return call;
        } catch (SQLException e) {
            //try to close call
            try {
                call.close();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            return null;
        }
    }

    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }

    public Connection getConnection() {
        return connection;
    }
}
