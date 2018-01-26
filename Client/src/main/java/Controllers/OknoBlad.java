package Controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

public class OknoBlad {
    @FXML private Label errorContent;


    public void closeWindow(MouseEvent mouseEvent) {
        Stage thisStage = (Stage)errorContent.getScene().getWindow();
        if(thisStage != null)
        {
            thisStage.close();
        }
    }

    public void setErrorContent(String errorContent) {
        this.errorContent.setText(errorContent);
    }
}
