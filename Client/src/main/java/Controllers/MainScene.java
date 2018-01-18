package Controllers;

import Utils.ExceptionHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import Relations.Teams;
import java.sql.SQLException;
import Main.Main;

public class MainScene {
    public TableView table;
    public Button populateData;
    public TableColumn C2;
    public TableColumn C1;


    @FXML
    private void initialize()
    {
        table.getColumns().clear();
        table.getColumns().addAll(C1, C2);
        C1.setCellValueFactory(
                new PropertyValueFactory<Teams,String>("Name")
        );
        C2.setCellValueFactory(
                new PropertyValueFactory<Teams,Integer>("Number")
        );
    }
    @FXML
    public void buttonMouseClicked()
    {
        if( Main.teamsData != null)
        {
            table.setItems(Main.teamsData);

            table.refresh();

        }
    }
}
