package Controllers;

import Relations.*;
import SQL.QueriesManager;
import Utils.Constants;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.awt.event.MouseEvent;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PokazBadaniaModal {
    public TableView badaniaTableView;
    public TableColumn<Sz_badania,String> name;
    public TableColumn<Sz_badania,String> data;
    public TableColumn<Sz_badania,String> id;
    private   List<Sz_badania> badania;
    public TableColumn doctor;
    @FXML
    private TableColumn ward;
    private List<Sz_oddzialy>wards;
    private  List<Sz_pracownicy> lekarze;
    private Sz_pacjenci pacjent;
    public void setPacjent(Sz_pacjenci pacjent) {
        this.pacjent = pacjent;
    }

    @FXML
    public void initialize()
    {
        id.setCellValueFactory(new PropertyValueFactory<Sz_badania, String>("id"));
        name.setCellValueFactory(new PropertyValueFactory<Sz_badania, String>("nazwa"));
        data.setCellValueFactory(new PropertyValueFactory<Sz_badania, String>("datagodzinabadania"));
        doctor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_badania, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_badania, String> p) {
                return new ReadOnlyStringWrapper(getImieLekarza(p.getValue()));
            }
        });

        ward.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_badania, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_badania, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });
    }

    private String getOddzialIdFromComboBox(Sz_badania badanie) {
        for (Sz_oddzialy oddzial : wards) {
            if (oddzial.getId() == badanie.getOddzialy_id()) {
                return oddzial.getNazwa();
            }
        }
        return "";
    }

    private String getImieLekarza(Sz_badania badanie) {
        for (Sz_pracownicy pracownik : lekarze) {
            if (pracownik.getPracownikid() == badanie.getPracownik_id()) {
                return pracownik.getImie()+" "+pracownik.getNazwisko();
            }
        }
        return "";
    }


    @FXML
    public void getLekarze() {
        lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getPracownicy(Constants.DOCTOR);

    }

    @FXML
    public void getExaminations()
    {
        QueriesManager queriesManager = new QueriesManager();
        wards = (new QueriesManager()).getOddzialy();
        badania = queriesManager.getPatientExamination(pacjent);
        if(badania != null)
        {

            badaniaTableView.getItems().clear();
            badaniaTableView.getItems().addAll(badania);
        }
    }



    public void showEdit() {
        Sz_badania badanie = (Sz_badania) badaniaTableView.getSelectionModel().getSelectedItem();
        if(badanie!=null)
        {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/edytujBadanie_modal.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }
            EdytujBadanieModal controller = loader.getController();
            controller.setPacjent(pacjent);
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
                            getExaminations();

                        }
                    });
                }
            });
        }
    }

    public void  deleteExamination()
    {
        Sz_badania badanie = (Sz_badania) badaniaTableView.getSelectionModel().getSelectedItem();

        badanie.setId((int)((Sz_badania) badaniaTableView.getSelectionModel().getSelectedItem()).getId());
        QueriesManager queriesManager = new QueriesManager();
        queriesManager.deleteExamination(badanie);
        getExaminations();
    }


}
