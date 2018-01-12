package main.sample;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import main.Relations.Teams;

import java.sql.*;
import java.util.Properties;

public class Main extends Application {
    public static ObservableList<Teams> teamsData;
    private static Main instance;

    Connection connection = null;
    public static Main getInstance() {
        if(instance == null)
            return new Main();
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        Scene scene = new Scene(root, 1280 , 720);
        primaryStage.setScene(scene);
        scene.getStylesheets().add(Main.class.getResource("style.css").toExternalForm());
        primaryStage.show();
            primaryStage.setOnCloseRequest(
                    new EventHandler<WindowEvent>() {
                        @Override
                        public void handle(WindowEvent event) {
                            try
                            {
                             connection.close();
                            }catch(SQLException e) {
                                ;
                            }

                        }
                    }
            );

        ObservableList<Teams> teams;
        connection = connect();//connect();
        if (connection == null)
            return;

        try {

            String query = "select id_prac, rpad(nazwisko, 20), placa_pod " +
                    "from pracownicy";
            //listWorkers(conn, query);


            query = "select count(*) as \"ONE\", z.nazwa as \"TWO\" from pracownicy p, zespoly z " +
                    "where p.id_zesp = z.id_zesp " +
                    "group by z.nazwa";

            teamsData = exercise1(connection, query);

        } catch
                (SQLException ex) {
            System.out.println(
                    "Bład wykonania polecenia"
                            + ex.toString());
            throw ex;
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

        //connection.close();
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

    private ObservableList<Teams> exercise1(Connection conn, String query) throws SQLException {
        ObservableList<Teams> teams = FXCollections.observableArrayList();

        stmt = conn.createStatement();
        rs = stmt.executeQuery(
                query
        );
        while(rs.next()) {
            System.out.println(rs.getInt(1) + " w zespole " + rs.getString(2));
            teams.add(new Teams(rs.getInt("ONE"), rs.getString("TWO")));

        }
        rs.close();
        stmt.close();
        return teams;
    }


    public void addDoctor()
            throws SQLException
    {
        if(connection == null)
        {
            System.out.println("Connection is null");
        }
        String query = "{call dodajLekarza(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = connection.prepareCall(query);
        statement.setString(1, "józef");
        statement.setString(2, "będziechowski");
        statement.setInt(3, 1);
        statement.setDouble(4, 5699);
        statement.setString(5, "kardiochirurg naczyniowy");
        statement.setString(6, "profesor");
        statement.setInt(7, 1);
        statement.execute();
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
