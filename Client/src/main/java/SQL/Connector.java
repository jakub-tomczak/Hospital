package SQL;

import Utils.Constants;
import Utils.ExceptionHandler;
import jdk.nashorn.internal.codegen.CompilerConstants;
import oracle.jdbc.OracleConnection;

import java.sql.*;
import java.util.Properties;

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
            Properties properties = new Properties();
            properties.setProperty("user", Constants.DB_USERNAME);
            properties.setProperty("password", Constants.DB_PASSWORD);
            properties.setProperty(OracleConnection.CONNECTION_PROPERTY_THIN_NET_CONNECT_TIMEOUT, "1500");

            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    properties);
            System.out.println("Połączono z bazą danych");
            connectionEstablished = true;
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można się połączyć z bazą. " + e.getMessage());
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
