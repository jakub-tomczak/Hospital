package Utils;

import Controllers.OknoBlad;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.sql.SQLException;

public class ExceptionHandler {
    public Alert alert;
    public static void displayException(Exception e)
    {
        displayException(e.getMessage());
    }
    public static void displayException(String message)
    {
        try
        {
            FXMLLoader loader = new FXMLLoader(ExceptionHandler.class.getClass().getResource("/views/OknoBlad.fxml"));
            Parent root = (Parent) loader.load();

            OknoBlad controller = (OknoBlad)loader.getController();
            if(controller != null) {
                controller.setErrorContent(message);
            }

            Stage modal = new Stage();
            modal.initModality(Modality.APPLICATION_MODAL);
            modal.setResizable(false);
            modal.setTitle("Błąd");
            Scene scene = new Scene(root, 600, 400);
            modal.setScene(scene);
            scene.getStylesheets().add(ExceptionHandler.class.getClass().getResource("/styles/mainscene_style.css").toString());
            modal.show();

        }catch(Exception e)
        {
            System.out.println(e.getStackTrace());
            System.out.println("Nastąpił błąd podczas wyświetlania okna");
            System.out.println(e.getMessage());
        }
    }
    public static void displaySQLException(SQLException sqlException)
    {
      //teraz wyswietl tak samo jak kazdy inny exception ale pozniej mozna dodac lapanie kodów sql zeby wiedziec co jest nie tak
        displayException(sqlException);
    }
    public static void getMessage(Exception e)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        if(e.getMessage().contains("ORA-00001")) {
            alert.setContentText("Istnieje już pacjent o podanym PESELU!");
        }
        else if (e.getMessage().contains("ORA-02292")){
            alert.setContentText("Podany rekord jest używany w innej tabeli!");
        }
        else
        {
            showMessage("Błąd");
        }
        alert.showAndWait();
    }

    public static void showMessage(String s)
    {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Komunikat");
        alert.setContentText(s);
        alert.setHeaderText(null);
        alert.showAndWait();
    }

}
