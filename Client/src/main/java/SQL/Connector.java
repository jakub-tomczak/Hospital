package SQL;

import Utils.Constants;

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

    Connection connection = null;
    boolean connectionEstablished = false;


    public void openConnection() throws ClassNotFoundException {
        if(connection != null)
        {
            throw new RuntimeException("Połączenie jest już nawiązane.");
        }
        Class.forName("oracle.jdbc.driver.OracleDriver");
        try {
            connection = DriverManager.getConnection(
                    "jdbc:oracle:thin:@admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    Constants.DB_PASSWORD, Constants.DB_PASSWORD);
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
        }

        System.out.println("Połączenie z bazą danych nie istniało.");
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



    public boolean isConnectionEstablished() {
        return connectionEstablished;
    }
}
