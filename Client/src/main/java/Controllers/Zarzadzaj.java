package Controllers;

import Utils.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Zarzadzaj {
    public void manageOddzialy(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    DodajPacjentaModal.class.getResource("/views/zarzadzajOddzialami.fxml"));
        } catch (IOException e) {
            ExceptionHandler.displayException(e);
        }
        stage.setScene(new Scene(root, 1024, 600));
        stage.setTitle("Zarządzaj oddziałami");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }

    public void manageLeki(ActionEvent actionEvent) {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    DodajPacjentaModal.class.getResource("/views/Leki.fxml"));
        } catch (IOException e) {
            ExceptionHandler.displayException(e);
        }
        stage.setScene(new Scene(root, 1024, 600));
        stage.setTitle("Zarządzaj lekami");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)actionEvent.getSource()).getScene().getWindow() );
        stage.show();
    }
}

