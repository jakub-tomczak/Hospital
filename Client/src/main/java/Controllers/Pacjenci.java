package Controllers;

import Utils.ExceptionHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class Pacjenci {
    public Button addPatient;

    @FXML
    private void showModal(MouseEvent mouseEvent)
    {
        Stage stage = new Stage();
        Parent root = null;
        try {
            root = FXMLLoader.load(
                    DodajPacjentaModal.class.getResource("/views/dodajPacjenta_modal.fxml"));
        } catch (IOException e) {
            ExceptionHandler.displayException(e);
        }
        stage.setScene(new Scene(root));
        stage.setTitle("Dodaj pacjenta");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                ((Node)mouseEvent.getSource()).getScene().getWindow() );
        stage.show();
    }


}
