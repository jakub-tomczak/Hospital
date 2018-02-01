package Controllers;

import Main.Main;
import Relations.*;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Callback;


import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class Operacje implements IDisplayedScreen {
    public TableColumn modifyOperation;
    public TableColumn deleteOperation;
    public TableColumn dateColumn;
    public TableColumn oddzialColumn;
    public TableColumn peselColumn;
    public TableColumn lastNameColumn;
    public TableColumn firstNameColumn;
    public TableView operationsTableView;
    public TableColumn nameOperationColumn;
    public TableColumn showDoctors;

    public VBox vBoxAvailableDoctors;

    public enum ButtonAction {
        delete,
        show,
        update
    }


    @FXML
    public void initialize() {
        Main.screensList.add(this);
        clearDoctorsList();
        nameOperationColumn.setCellValueFactory(new PropertyValueFactory<OperationTableView, String>("nazwaoperacji"));
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<OperationTableView, String>("imiePacjenta"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<OperationTableView, String>("nazwiskoPacjenta"));
        peselColumn.setCellValueFactory(new PropertyValueFactory<OperationTableView, Double>("pesel"));
        oddzialColumn.setCellValueFactory(new PropertyValueFactory<OperationTableView, Double>("oddzialName"));
        dateColumn.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<OperationTableView, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<OperationTableView, String> p) {
                return new ReadOnlyStringWrapper(getDate(p.getValue()));
            }
        });

        //przycisk do usuwania lekarza
        deleteOperation.setSortable(false);
        deleteOperation.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_pracownicy, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_pracownicy, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        deleteOperation.setCellFactory(
                new Callback<TableColumn<OperationTableView, Boolean>, TableCell<OperationTableView, Boolean>>() {

                    @Override
                    public TableCell<OperationTableView, Boolean> call(TableColumn<OperationTableView, Boolean> p) {
                        return new ButtonFactory("Usuń", ButtonAction.delete);
                    }
                });

        //przycisk do aktualizacji
        modifyOperation.setSortable(false);
        modifyOperation.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationTableView, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OperationTableView, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        modifyOperation.setCellFactory(
                new Callback<TableColumn<OperationTableView, Boolean>, TableCell<OperationTableView, Boolean>>() {

                    @Override
                    public TableCell<OperationTableView, Boolean> call(TableColumn<OperationTableView, Boolean> p) {
                        return new ButtonFactory("Modyfikuj", ButtonAction.update);
                    }
                });

        //przycisk do aktualizacji
        showDoctors.setSortable(false);
        showDoctors.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<OperationTableView, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<OperationTableView, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        showDoctors.setCellFactory(
                new Callback<TableColumn<OperationTableView, Boolean>, TableCell<OperationTableView, Boolean>>() {

                    @Override
                    public TableCell<OperationTableView, Boolean> call(TableColumn<OperationTableView, Boolean> p) {
                        return new ButtonFactory("Pokaż lekarzy", ButtonAction.show);
                    }
                });

        FilterSupport.addFilter(nameOperationColumn);
        FilterSupport.addFilter(oddzialColumn);
        FilterSupport.addFilter(peselColumn);
        FilterSupport.addFilter(lastNameColumn);
        FilterSupport.addFilter(firstNameColumn);
    }

    private String getDate(OperationTableView value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return value.getData().format(formatter);
    }

    @Override
    public void refresh() {
        clearDoctorsList();
        getOperationsData();
        operationsTableView.refresh();
    }

    private void getOperationsData() {
        List<OperationTableView> operationTableViews = new ArrayList<>();
        QueriesManager queriesManager = new QueriesManager();
        operationTableViews = queriesManager.getDataForOperations();
        if (operationTableViews == null) {
            ExceptionHandler.displayException("Brak danych o operacjach!");
            return;
        }
        operationsTableView.getItems().setAll(FXCollections.observableList(operationTableViews));


    }

    private void clearDoctorsList() {
        if (vBoxAvailableDoctors.getChildren().size() > 0)
            vBoxAvailableDoctors.getChildren().clear();
    }

    private void generateDoctorsList(OperationTableView operacja) {
        clearDoctorsList();

        QueriesManager queriesManager = new QueriesManager();
        List<Sz_pracownicy> lekarze = queriesManager.getDoctorsFromOperation(operacja.getOperacjaID());
        if (lekarze == null || lekarze.size() == 0) {
            vBoxAvailableDoctors.getChildren().add(new Label("Brak lekarzy przydzielonych do tej operacjii."));
            return;
        }
        vBoxAvailableDoctors.getChildren().add(new Label("Lekarze przydzieleni do tej operacji:"));
        for (Sz_pracownicy lekarz : lekarze) {
            Label doctor = new Label(lekarz.getImie() + " " + lekarz.getNazwisko());
            vBoxAvailableDoctors.getChildren().add(doctor);
        }
    }

    private void updateOperation(OperationTableView operation) {
        Sz_operacje operacja = new Sz_operacje();
        operacja.setPacjenci_id(operation.getPacjentID());
        operacja.setRodzajoperacji(operation.getNazwaoperacji());
        operacja.setDatagodzinarozpoczecia(operation.getData());
        operacja.setOddzialy_id(operation.getOddzial());
        operacja.setOperacjaid(operation.getOperacjaID());

        Sz_pacjenci pacjent = new Sz_pacjenci();
        pacjent.setId(operation.getPacjentID());
        pacjent.setImie(operation.getImiePacjenta());
        pacjent.setNazwisko(operation.getNazwiskoPacjenta());
        pacjent.setPesel(operation.getPesel());

        QueriesManager queriesManager = new QueriesManager();


        List<Sz_pracownicy> doctors = queriesManager.getDoctorsFromOperation(operation.getOperacjaID());
        if (doctors.size() == 0) {
            ExceptionHandler.displayException("Brak lekarzy pracujących przy tej operacji.");
            return;
        }
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
        controller.setFormToUpdate(doctors, pacjent, operacja);
        stage.setScene(new Scene(root));
        stage.setTitle("Dodaj badanie");
        stage.initModality(Modality.WINDOW_MODAL);
        stage.initOwner(
                operationsTableView.getScene().getWindow());
        stage.show();

    }

    private void delete_operation(OperationTableView operation) {
        QueriesManager queriesManager = new QueriesManager();
        if(queriesManager.deleteRecord("Sz_operacje", "operacjaid", operation.getOperacjaID()))
        {
            ExceptionHandler.displayException("Udało się usunąć operację");
            Main.getInstance().refreshAll();
        }
    }

    private class ButtonFactory extends TableCell<OperationTableView, Boolean> {

        Button cellButton = null;


        public ButtonFactory(String buttonName, ButtonAction action) {
            cellButton = new Button(buttonName);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = getTableRow().getIndex();
                    OperationTableView operation = (OperationTableView) operationsTableView.getItems().get(index);
                    if(operation == null)
                        return;
                    switch(action)
                    {
                        case delete:
                            delete_operation(operation);
                            break;
                        case show:
                            generateDoctorsList(operation);
                            break;
                        case update:
                            updateOperation(operation);
                            break;
                    }
                }
            });
        }


        //Display button if the row is not empty
        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }


}
