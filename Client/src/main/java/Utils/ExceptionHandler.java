package Utils;

import Controllers.OknoBlad;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ExceptionHandler {
    public static void displayException(Exception e)
    {
        System.out.println(e.getMessage());
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
            System.out.println("Nastąpił błąd podczas wyświetlania okna");
        }
    }
}
