package Controllers;

import Main.Main;
import Relations.Sz_operacje;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import oracle.sql.DATE;
import Controllers.DodajOperacjeModal;

import javax.rmi.CORBA.Util;
import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;

public class Operacje {
    @FXML private void showModal(MouseEvent mouseEvent)

    {
        Stage dialog = new Stage();

        dialog.initOwner(Main.getInstance().getMainWindowStage());
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.showAndWait();
    }
    @FXML private void showModal1(MouseEvent mouseEvent)
    {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    DodajOperacjeModal.class.getResource("/views/dodajOperacje_modal.fxml"));
        } catch (IOException e) {
            ExceptionHandler.displayException(e);
        }
        stage.setScene(new Scene(root));
        stage.setTitle("My modal window");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)mouseEvent.getSource()).getScene().getWindow() );
        stage.show();
    }



    @FXML private void addOperation(MouseEvent mouseEvent) {
        Sz_operacje operacja = new Sz_operacje();
        operacja.setDatagodzinarozpoczecia("2017-01-21 12:00:00");
        operacja.setRodzajoperacji("wycięcie wyrostka robaczkowego");
        operacja.setOddzialy_id(1);
        operacja.setPacjenci_id(3);

        QueriesManager query = new QueriesManager();
        try {
            query.adddOperation(operacja, 1);
        } catch (SQLException e) {
            ExceptionHandler.displayException(e);
        }
    }
}
