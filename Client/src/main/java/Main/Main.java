package Main;
import SQL.Connector;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import Relations.Teams;
import java.sql.*;

public class Main extends Application {
    public static ObservableList<Teams> teamsData;
    private static Main instance;

    private Stage mainWindowStage;

    Connection connection = null;
    public static Main getInstance() {
        if(instance == null)
            return new Main();
        return instance;
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        mainWindowStage = primaryStage;
        instance = this;
        Parent root = FXMLLoader.load(getClass().getResource("/views/MainScene.fxml"));
        primaryStage.setTitle("Hospital");
        Scene scene = new Scene(root, 1280 , 720);
        primaryStage.setScene(scene);
        //scene.getStylesheets().add(getClass().getResource("/styles/mainscene_style.css").toString());
        primaryStage.show();
        primaryStage.setOnCloseRequest(
                (WindowEvent event) ->
                {
                    onApplicationClosing();
                }
            );
        Connector.getInstance().openConnection();
    }

    private void onApplicationClosing() {
        System.out.println("Zamykanie aplikacji...");

        try {
            Connector.getInstance().closeConnection();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się zamknąć połączenia z bazą!");

        }
    }


    private Connection connect() throws ClassNotFoundException, SQLException {

        return null;

    }

    public static void main(String[] args) {
        launch(args);
    }

    public Stage getMainWindowStage() {
        return mainWindowStage;
    }
}
