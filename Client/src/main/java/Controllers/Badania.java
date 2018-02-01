package Controllers;


import Main.Main;
import Relations.Badanie;
import Relations.PacjentNaOddziale;
import Relations.Sz_badania;
import Relations.Sz_pacjenci;
import SQL.QueriesManager;
import Utils.IDisplayedScreen;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.script.Bindings;
import java.io.IOException;
import java.util.List;

public class Badania implements IDisplayedScreen{


    public TableView<Badanie> badaniaT;
    public TableColumn<Badanie,String> id;
    public TableColumn<Badanie,String> name;
    public TableColumn<Badanie,String> pesel;
    public TableColumn<Badanie,String> data;
    public TableColumn<Badanie,String> ward;
    public TableColumn<Badanie,String> doctor;
    public Button updateE;
    public Button deleteE;

    @FXML
    public void initialize()
    {
        Main.screensList.add(this);
        id.setCellValueFactory(new PropertyValueFactory<Badanie, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Badanie, String>("nazwa"));
        pesel.setCellValueFactory(new PropertyValueFactory<Badanie, String>("pesel"));
        data.setCellValueFactory(new PropertyValueFactory<Badanie,String>("data"));
        ward.setCellValueFactory(new PropertyValueFactory<Badanie,String>("oddzial"));
        doctor.setCellValueFactory(new PropertyValueFactory<Badanie,String>("lekarz"));
        updateE.disableProperty().bind(
                javafx.beans.binding.Bindings.not(badaniaT.getSelectionModel().selectedItemProperty().isNotNull())
        );
        deleteE.disableProperty().bind(
                javafx.beans.binding.Bindings.not(badaniaT.getSelectionModel().selectedItemProperty().isNotNull())
        );
    }

    @Override
    public void refresh()
    {
        getBadania();
    }

    public void getBadania()
    {
        QueriesManager queriesManager = new QueriesManager();
        List<Badanie> badania = queriesManager.getAllExaminations();
        if(badania != null)
        {
            badaniaT.getItems().clear();
            badaniaT.getItems().addAll(badania);
        }


    }

    @FXML
    public void deleteBadanie()
    {
        Sz_badania badanie = new Sz_badania(null,null,0,0,0,0);
            badanie.setId(Integer.valueOf(badaniaT.getSelectionModel().getSelectedItem().getId()));
            QueriesManager queriesManager = new QueriesManager();
            queriesManager.deleteExamination(badanie);
            getBadania();
    }
    @FXML
    public void showEdit() {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/edytujBadanie_modal.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EdytujBadanieModal controller = loader.getController();
            int pacjentID =  Integer.valueOf(badaniaT.getSelectionModel().getSelectedItem().getPacjentID());
            Sz_pacjenci pacjent = new Sz_pacjenci(null,null,null,null,null,null,pacjentID);
            controller.setPacjent(pacjent);
            Sz_badania badanie = new QueriesManager().getBadanie((Integer.valueOf(badaniaT.getSelectionModel().getSelectedItem().getId())));
            controller.setBadanie(badanie.getId());
            controller.getLekarze();
            stage.setScene(new Scene(root));
            stage.setTitle("Badania");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getBadania();

                        }
                    });
                }
            });
        }
}
