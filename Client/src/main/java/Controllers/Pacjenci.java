package Controllers;

import Main.Main;
import Relations.Sz_pacjenci;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.IOException;
import java.util.List;

public class Pacjenci implements IDisplayedScreen {
    public Button addPatient;
    public TableColumn<Sz_pacjenci, String> miasto;
    public TableColumn<Sz_pacjenci, String> kod;
    public TableColumn<Sz_pacjenci, String> adres;
    public TableColumn<Sz_pacjenci, String> nazwisko;
    public TableColumn<Sz_pacjenci, String> imie;
    @FXML
    public TableColumn<Sz_pacjenci, String> pesel;
    @FXML
    public TableView<Sz_pacjenci> pacjenci;

    @FXML
    public void initialize() {
        Main.screensList.add(this);
        pesel.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("pesel"));
        imie.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("nazwisko"));
        adres.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("adres"));
        kod.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("kod"));
        miasto.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("miasto"));
        // Main.screensList.add(this);
        FilterSupport.addFilter(miasto);
        FilterSupport.addFilter(kod);
        FilterSupport.addFilter(adres);
        FilterSupport.addFilter(nazwisko);
        FilterSupport.addFilter(miasto);
        FilterSupport.addFilter(imie);
        FilterSupport.addFilter(pesel);


    }

    @FXML
    private void showModal(MouseEvent mouseEvent) {
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
                ((Node) mouseEvent.getSource()).getScene().getWindow());
        stage.show();
        stage.setOnHiding(new EventHandler<WindowEvent>() {

            @Override
            public void handle(WindowEvent event) {
                Platform.runLater(new Runnable() {

                    @Override
                    public void run() {
                        getPacjenci();
                    }
                });
            }
        });
    }

    @FXML
    public void showAddExamination(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if (patient != null) {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dodajBadanie_modal.fxml"));
            root = (Parent) loader.load();
            DodajBadanieModal controller = loader.getController();
            controller.setPacjent(patient);
            controller.getLekarze();
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj badanie");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) mouseEvent.getSource()).getScene().getWindow());
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getPacjenci();

                        }
                    });
                }
            });
        }
    }


    @FXML
    public void showUpdatePatient(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if (patient != null) {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/edytujPacjenta_modal.fxml"));
            root = (Parent) loader.load();
            EdytujPacjentaModal controller = loader.getController();
            controller.setPatient(patient);
            controller.showData();
            stage.setScene(new Scene(root));
            stage.setTitle("Edytuj pacjenta");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) mouseEvent.getSource()).getScene().getWindow());
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getPacjenci();

                        }
                    });
                }
            });
        }
    }

    @FXML
    public void showExaminations(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if (patient != null) {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/badaniaPacjenta_modal.fxml"));
            root = (Parent) loader.load();
            PokazBadaniaModal controller = loader.getController();
            controller.setPacjent(patient);
            controller.getLekarze();
            controller.getExaminations();
            stage.setScene(new Scene(root));
            stage.setTitle("Badania");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) mouseEvent.getSource()).getScene().getWindow());
            stage.show();
            stage.setOnHiding(new EventHandler<WindowEvent>() {

                @Override
                public void handle(WindowEvent event) {
                    Platform.runLater(new Runnable() {

                        @Override
                        public void run() {
                            getPacjenci();

                        }
                    });
                }
            });
        }
    }

    @FXML
    public void getPacjenci() {
        QueriesManager queriesManager = new QueriesManager();

        List<Sz_pacjenci> patients = queriesManager.getPacjenci();
        if (patients != null) {
            pacjenci.refresh();
            pacjenci.getItems().clear();
            pacjenci.getItems().addAll(patients);
        }
    }

    @FXML
    public void deletePatient() {
        QueriesManager queriesManager = new QueriesManager();
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if (patient != null) {
            queriesManager.deletePatient(patient);
        }
        getPacjenci();
    }

    @Override
    public void refresh() {
        getPacjenci();
    }

    public void addOperation(MouseEvent mouseEvent) {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if (patient != null) {
            Stage stage = new Stage();
            Parent root = null;
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/dodajOperacje_modal.fxml"));
            try {
                root = (Parent) loader.load();
            } catch (IOException e) {
                ExceptionHandler.displayException("Nie znaleziono widoku do dodawania operacji!");
                return;
            }
            DodajOperacjeModal controller = loader.getController();
            controller.setFormToInsert(patient);
            stage.setScene(new Scene(root));
            stage.setTitle("Dodaj badanie");
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(
                    ((Node) mouseEvent.getSource()).getScene().getWindow());
            stage.show();
        }

    }
}
