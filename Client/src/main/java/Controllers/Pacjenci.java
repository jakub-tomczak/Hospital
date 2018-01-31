package Controllers;

import Main.Main;
import Relations.*;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.FlowPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Callback;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Pacjenci implements  IDisplayedScreen   {
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
    public ComboBox oddzialyBox;
    public Button dodajNaOddzial;
    public Button historyButton;
    public TableColumn<PacjentNaOddziale,String> oddzialO;
    public TableColumn<PacjentNaOddziale,String> peselO;
    public TableColumn<PacjentNaOddziale,String>  imieO;
    public TableColumn<PacjentNaOddziale,String>  nazwiskoO;
    public TableColumn<PacjentNaOddziale,String> dataPrzyjeciaO;
    public TableView pacjenciNaOddziale;
    public TabPane tabPatients;
    public Button wypisz;
    private List<Sz_oddzialy> oddzialy;
    private List<Sz_oddzialy> wards;
    private List<Sz_pobyty> pobyty;
    private int pobytID;

    @FXML
    public void initialize()
    {
        Main.screensList.add(this);
        pesel.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("pesel"));
        imie.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("imie"));
        nazwisko.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("nazwisko"));
        adres.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("adres"));
        kod.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("kod"));
        miasto.setCellValueFactory(new PropertyValueFactory<Sz_pacjenci, String>("miasto"));
        peselO.setCellValueFactory(new PropertyValueFactory<PacjentNaOddziale, String>("pesel"));
        imieO.setCellValueFactory(new PropertyValueFactory<PacjentNaOddziale, String>("imie"));
        nazwiskoO.setCellValueFactory(new PropertyValueFactory<PacjentNaOddziale, String>("nazwisko"));
        dataPrzyjeciaO.setCellValueFactory(new PropertyValueFactory<PacjentNaOddziale,String>("dataWpisania"));
        oddzialO.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<PacjentNaOddziale, String>, ObservableValue<String>>() {
        public ObservableValue<String> call(TableColumn.CellDataFeatures<PacjentNaOddziale, String> p) {
            return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
        }
    });

       //
        dodajNaOddzial.disableProperty().bind(
                Bindings.not(pacjenci.getSelectionModel().selectedItemProperty().isNotNull())
                        .or(oddzialyBox.getSelectionModel().selectedItemProperty().isNull())
                        );
        wypisz.disableProperty().bind(
                Bindings.not(pacjenciNaOddziale.getSelectionModel().selectedItemProperty().isNotNull())
        );
        tabPatients.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Object>() {
            // Here's the key part. See how I specify that the
            // parameters are of type student. Now you can use the
            // instance methods from Student.
            @Override
            public void changed(ObservableValue<? extends Object> observable,Object oldValue, Object newValue){
                if(tabPatients.getSelectionModel().getSelectedIndex()==1)
                {
                    pacjenciNaOddziale.getSelectionModel().clearSelection();
                }
                //you can add any other value from Student class via getter(getAdr,getMail,...)

            }
        });
        FilterSupport.addFilter(miasto);
        FilterSupport.addFilter(kod);
        FilterSupport.addFilter(adres);
        FilterSupport.addFilter(nazwisko);
        FilterSupport.addFilter(miasto);
        FilterSupport.addFilter(imie);
        FilterSupport.addFilter(pesel);
        FilterSupport.addFilter(imieO);
        FilterSupport.addFilter(nazwiskoO);
        FilterSupport.addFilter(peselO);
        FilterSupport.addFilter(dataPrzyjeciaO);
        FilterSupport.addFilter(oddzialO);
    }
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
        if(patient==null) {
            PacjentNaOddziale pacjentNaOddziale = (PacjentNaOddziale) pacjenciNaOddziale.getSelectionModel().getSelectedItem();
            if (pacjentNaOddziale == null) {
                return;
            } else {
                QueriesManager queriesManager = new QueriesManager();
                patient = queriesManager.getPacjent(pacjentNaOddziale.getId());
            }
        }
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
                ((Node)mouseEvent.getSource()).getScene().getWindow() );
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
    public void showUpdatePatient(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if(patient==null) {
            PacjentNaOddziale pacjentNaOddziale = (PacjentNaOddziale) pacjenciNaOddziale.getSelectionModel().getSelectedItem();
            if (pacjentNaOddziale == null) {
                return;
            } else {
                QueriesManager queriesManager = new QueriesManager();
                patient = queriesManager.getPacjent(pacjentNaOddziale.getId());
            }
        }
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
                    ((Node)mouseEvent.getSource()).getScene().getWindow() );
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
    public void showExaminations(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if(patient==null) {
            PacjentNaOddziale pacjentNaOddziale = (PacjentNaOddziale) pacjenciNaOddziale.getSelectionModel().getSelectedItem();
            if (pacjentNaOddziale == null) {
                return;
            } else {
                QueriesManager queriesManager = new QueriesManager();
                patient = queriesManager.getPacjent(pacjentNaOddziale.getId());
            }
        }
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
                    ((Node)mouseEvent.getSource()).getScene().getWindow() );
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
    public void showPobyty(MouseEvent mouseEvent) throws IOException {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if(patient==null) {
            PacjentNaOddziale pacjentNaOddziale = (PacjentNaOddziale) pacjenciNaOddziale.getSelectionModel().getSelectedItem();
            if (pacjentNaOddziale == null) {
                return;
            } else {
                QueriesManager queriesManager = new QueriesManager();
                patient = queriesManager.getPacjent(pacjentNaOddziale.getId());
            }
        }
                Stage stage = new Stage();
                Parent root = null;
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/pobytyPacjenta_modal.fxml"));
                root = (Parent) loader.load();
                PokazPobytyModal controller = loader.getController();
                controller.setPatient(patient);
                controller.getPobyty();
                controller.getOddzialy();
                stage.setScene(new Scene(root));
                stage.setTitle("Pobyty");
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

    private String getOddzialIdFromComboBox(PacjentNaOddziale pacjentNaOddziale) {
        for (Sz_oddzialy oddzial : oddzialy) {
            if (oddzial.getId() == pacjentNaOddziale.getOddzialID()) {
                return oddzial.getNazwa();
            }
        }
        return "";
    }
    @FXML
    public  void getPacjenci()
    {
        QueriesManager queriesManager = new QueriesManager();

        List<Sz_pacjenci> patients = queriesManager.getPacjenci();
        if(patients != null)
        {
            pacjenci.refresh();
            pacjenci.getItems().clear();
            pacjenci.getItems().addAll(patients);
        }
    }

    @FXML
    public  void getPacjenciNaOddziale()
    {
        QueriesManager queriesManager = new QueriesManager();

        List<PacjentNaOddziale> patients = queriesManager.getPacjenciNaOddziale();
        if(patients != null)
        {
            pacjenciNaOddziale.getItems().clear();
            pacjenciNaOddziale.getItems().addAll(patients);
        }
    }

    @FXML
    public void deletePatient()
    {
        QueriesManager queriesManager = new QueriesManager();
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        if(patient!=null) {
            queriesManager.deletePatient(patient);
        }
      getPacjenci();
    }

    private void getOddzialy() {
        QueriesManager queriesManager = new QueriesManager();
        oddzialy = queriesManager.getOddzialy();
        if (oddzialy != null) {
            List<String> nazwyOddzialow = new ArrayList<>();
            for (Sz_oddzialy oddzial : oddzialy) {
                nazwyOddzialow.add(oddzial.getNazwa());
            }
            oddzialyBox.setItems(FXCollections.observableList(nazwyOddzialow));
        }
    }

    @FXML
    public void addToWard()
    {
        Sz_pacjenci patient = pacjenci.getSelectionModel().getSelectedItem();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        Sz_pobyty pobyt = new Sz_pobyty(0,patient.getId(),getOddzialIdFromComboBox(),sdf.format(cal.getTime()),null);
        QueriesManager queriesManager = new QueriesManager();
        if(queriesManager.ifOnWard(patient)==true)
        {
            ExceptionHandler.showMessage("Pacjent już znajduje się na oddziale!");
        }
        else
        {
            queriesManager.addPobyt(pobyt);
            ExceptionHandler.showMessage("Pomyślnie dodano na oddział.");
        }
        getPacjenciNaOddziale();

    }

    @FXML
    public void wypisz()
    {
        QueriesManager queriesManager = new QueriesManager();
        PacjentNaOddziale pacjentNaOddziale = (PacjentNaOddziale) pacjenciNaOddziale.getSelectionModel().getSelectedItem();
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        pacjentNaOddziale.setDataWpisania(sdf.format(cal.getTime()));
        queriesManager.wypisz(pacjentNaOddziale);
        getPacjenciNaOddziale();
    }

    @Override
    public void refresh() {

        getOddzialy();
        getPacjenci();
        getPacjenciNaOddziale();
    }

    private int getOddzialIdFromComboBox() {

        int oddzialId = -1;
        //combobox with oddzialy validation
        if (oddzialyBox.getItems().size() == 0) {
            ExceptionHandler.displayException("Dodaj najpierw oddział!");
            return oddzialId;
        }
        if (oddzialyBox.getValue() == null) {
            ExceptionHandler.displayException("Wybierz oddział");
            return oddzialId; //-1
        }
        String oddzialToFind = oddzialyBox.getValue().toString();
        for (Sz_oddzialy oddzial : oddzialy) {
            if (oddzial.getNazwa().equals(oddzialToFind)) {
                oddzialId = oddzial.getId();
                break;
            }
        }
        return oddzialId;
    }
}
