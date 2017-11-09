package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.sql.*;
import java.util.Properties;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();

        Connection conn = connect();
        if (conn == null)
            return;

        try {

            String query = "select id_prac, rpad(nazwisko, 20), placa_pod " +
                    "from pracownicy";
            //listWorkers(conn, query);

            query = "select count(*) from pracownicy";
            exercise1_0(conn, query);

            query = "select count(*), z.nazwa from pracownicy p, zespoly z " +
                    "where p.id_zesp = z.id_zesp " +
                    "group by z.nazwa";
            exercise1(conn, query);

        } catch
                (SQLException ex) {
            System.out.println(
                    "Bład wykonania polecenia"
                            + ex.toString());
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (SQLException e) {
/* kod obsługi */
                }
            }
            if (stmt != null) {
                try {
                    stmt.close();
                } catch
                        (SQLException e) {
/* kod obsługi */
                }
            }
        }

        conn.close();
    }

    Statement stmt = null;
    ResultSet rs = null;


    private void exercise1_0(Connection conn, String query) throws SQLException {
        stmt = conn.createStatement();
        rs = stmt.executeQuery(
                query
        );
        while
                (rs.next()) {
            System.out.println(rs.getInt(1) + " w zespole " + rs.getString(2));
        }


        rs.close();
        stmt.close();
    }

    private void exercise1(Connection conn, String query) throws SQLException {

        stmt = conn.createStatement();
        rs = stmt.executeQuery(
                query
        );
        while
                (rs.next()) {
            System.out.println(rs.getInt(1) + " w zespole " + rs.getString(2));
        }

        rs.close();
        stmt.close();
    }


    private void listWorkers(Connection conn, String query) throws SQLException {

        stmt = conn.createStatement();
        rs = stmt.executeQuery(
                query
        );
        while
                (rs.next()) {
            System.out.println(rs.getInt(1) +
                    " "
                    + rs.getString(2) +
                    " "
                    +
                    rs.getFloat(3));
        }


        rs.close();
        stmt.close();


    }


    private Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("oracle.jdbc.driver.OracleDriver");
        Properties connectionProps = new Properties();
        Connection con = null;
        connectionProps.put("user", "inf127083");
        connectionProps.put("password", "inf127083");
        try {
            con = DriverManager.getConnection(
                    "jdbc:oracle:thin:@admlab2.cs.put.poznan.pl:1521/dblab02_students.cs.put.poznan.pl",
                    "inf127083", "inf127083");
            System.out.println("Połączono z bazą danych");
            return con;
        } catch (SQLException e) {
            System.out.println("Błąd wykonania połączenia " + e.getMessage());
        }
        return null;

    }

    public static void main(String[] args) {
        launch(args);
    }
}
