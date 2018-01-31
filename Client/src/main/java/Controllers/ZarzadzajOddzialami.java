package Controllers;

import Main.Main;
import Relations.Sz_lekarze;
import Relations.Sz_oddzialy;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.IDisplayedScreen;
import Utils.Validator;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.sql.PreparedStatement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ZarzadzajOddzialami implements IDisplayedScreen{
    private final String insertMode = "Tryb dodawania";
    private final String updateMode = "Tryb aktualizacji";
    private final String insertModeAcceptButtonText = "Dodaj";
    private final String updateModeAcceptButtonText = "Aktualizuj";

    private Pracownicy.FormMode formMode = Pracownicy.FormMode.insert;

    public TableColumn nameOddzial;
    public TableColumn bossOddzial;
    public TableColumn maxPeopleOddzial;
    public TableColumn removeOddzial;
    public TableColumn updateOddzial;
    public TableView oddzialyTableView;
    public Button formAcceptButton;
    public Label formModeLabel;
    public TextField nazwaOddzialInput;
    public TextField maxPeopleOddzialField;
    public ComboBox doctorsComboBox;
    private Sz_oddzialy oddzialToUpdate;

    List<Sz_pracownicy> lekarzeInHospital = new ArrayList<>();
    @FXML
    public void initialize() {
        //dodaje siebie do listy ekranów odświeżalnych
        Main.screensList.add(this);

        //----------------------------------------------------------------------------------------------
        //inicjalizacja lekarzy
        nameOddzial.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, String>("nazwa"));
        maxPeopleOddzial.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, Integer>("maksymalnaliczbapacjentow"));
        bossOddzial.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_oddzialy, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_oddzialy, String> p) {
                return new ReadOnlyStringWrapper(getBossNameFromOddzial(p.getValue()));
            }
        });

        //przycisk do usuwania
        removeOddzial.setSortable(false);
        removeOddzial.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_oddzialy, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_oddzialy, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        removeOddzial.setCellFactory(
                new Callback<TableColumn<Sz_oddzialy, Boolean>, TableCell<Sz_oddzialy, Boolean>>() {

                    @Override
                    public TableCell<Sz_oddzialy, Boolean> call(TableColumn<Sz_oddzialy, Boolean> p) {
                        return new ButtonFactory("Usuń", Pracownicy.ButtonAction.delete);
                    }
                });

        //przycisk do aktualizacji
        updateOddzial.setSortable(false);
        updateOddzial.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_oddzialy, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_oddzialy, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        updateOddzial.setCellFactory(
                new Callback<TableColumn<Sz_oddzialy, Boolean>, TableCell<Sz_oddzialy, Boolean>>() {

                    @Override
                    public TableCell<Sz_oddzialy, Boolean> call(TableColumn<Sz_oddzialy, Boolean> p) {
                        return new ButtonFactory("Modyfikuj", Pracownicy.ButtonAction.update);
                    }
                });


        FilterSupport.addFilter(nameOddzial);
        FilterSupport.addFilter(bossOddzial);

        refresh();

    }

    @Override
    public void refresh() {
        getOddzialy();
        getLekarze();
        clearFields(null);
    }

    private void getOddzialy() {
        QueriesManager queriesManager = new QueriesManager();
        List<Sz_oddzialy> oddzialy = queriesManager.getOddzialy();
        if(oddzialy != null)
        {
            oddzialyTableView.getItems().setAll(oddzialy);
        }
        oddzialyTableView.refresh();
    }

    private String getBossNameFromOddzial(Sz_oddzialy value) {
        if(value.getKierownikkliniki() == 0){
            return "brak";
        }
        for(Sz_pracownicy lekarz : lekarzeInHospital)
        {
            if(value.getKierownikkliniki() == lekarz.getPracownikid())
            {
                return lekarz.getNazwisko() + " " + lekarz.getImie();
            }
        }
        return "brak";
    }

    private void getLekarze()
    {
        List<Sz_pracownicy> lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getPracownicy(Constants.DOCTOR);
        
        List<String> lekarzeComboBox = new ArrayList<>();
        lekarzeComboBox.add("brak");
        if (lekarze != null) {
            for(Sz_pracownicy lekarz : lekarze)
            {
                lekarzeComboBox.add(lekarz.getNazwisko() + " " + lekarz.getImie());
            }
            lekarzeInHospital = lekarze;
        }

        doctorsComboBox.setItems(FXCollections.observableList(lekarzeComboBox));
    }

    private int getDoctorId()
    {
        String value = (String)doctorsComboBox.getValue();
        if(value == "Brak")
        {
            return 0;
        }
        for(Sz_pracownicy lekarz: lekarzeInHospital)
        {
            String lekarzVal = lekarz.getNazwisko() + " " + lekarz.getImie();
            if(lekarzVal.equals(value))
            {
                return lekarz.getPracownikid();
            }
        }
        return 0;
    }

    public void performAction(ActionEvent actionEvent) {
        if(!validateData())
        {
            return;
        }
        QueriesManager queriesManager = new QueriesManager();
        switch(formMode)
        {
            case update:
                oddzialToUpdate.setNazwa(nazwaOddzialInput.getText());
                oddzialToUpdate.setMaksymalnaliczbapacjentow(Integer.parseInt(maxPeopleOddzialField.getText()));
                oddzialToUpdate.setKierownikkliniki(getDoctorId());
                if(queriesManager.updateOddzial(oddzialToUpdate))
                {
                    Main.getInstance().refreshAll();
                }
                break;
            case insert:
                Sz_oddzialy oddzialToAdd = new Sz_oddzialy();
                oddzialToAdd.setNazwa(nazwaOddzialInput.getText());
                oddzialToAdd.setMaksymalnaliczbapacjentow(Integer.parseInt(maxPeopleOddzialField.getText()));
                oddzialToAdd.setKierownikkliniki(getDoctorId());
                if(queriesManager.addOddzial(oddzialToAdd))
                {
                    Main.getInstance().refreshAll();
                }
                break;
        }
    }

    public void clearFields(ActionEvent actionEvent) {
        //reset fields
        nazwaOddzialInput.setText("");
        maxPeopleOddzialField.setText("");

        //reset button and text
        formAcceptButton.setText(insertModeAcceptButtonText);
        formModeLabel.setText(insertMode);
        formModeLabel.setTextFill(Paint.valueOf("#15ff00"));    //green
        formMode = Pracownicy.FormMode.insert;

        clearValidationMarks();

        oddzialToUpdate = null;
    }

    private void clearValidationMarks() {
        nazwaOddzialInput.setStyle("-fx-background-color: #ffffff");
        maxPeopleOddzialField.setStyle("-fx-background-color: #ffffff");
    }


    public void setOddzialToUpdate(Sz_oddzialy oddzialToUpdate) {
        //wyczysc pola
        clearFields(null);

        //set form
        formMode = Pracownicy.FormMode.update;

        this.oddzialToUpdate = oddzialToUpdate;

        //set button and label text
        formAcceptButton.setText(updateModeAcceptButtonText);
        formModeLabel.setText(updateMode);
        formModeLabel.setTextFill(Paint.valueOf("#FF8B1E"));    //orange


        nazwaOddzialInput.setText(oddzialToUpdate.getNazwa());
        maxPeopleOddzialField.setText(String.format("%d", oddzialToUpdate.getMaksymalnaliczbapacjentow()));
        doctorsComboBox.setValue(getBossNameFromOddzial(oddzialToUpdate));
    }


    private boolean validateData()
    {
        boolean result = true;
        result &= Validator.validateIntegerField(maxPeopleOddzialField, 1, 200, "Podaj liczbę pacjentów w przedziale <1,200>.");
        result &= Validator.validateStringField(nazwaOddzialInput);
        return result;
    }

    private class ButtonFactory extends TableCell<Sz_oddzialy, Boolean> {

        Button cellButton = null;


        public ButtonFactory(String buttonName, Pracownicy.ButtonAction action) {
            cellButton = new Button(buttonName);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = getTableRow().getIndex();
                    Sz_oddzialy oddzial = (Sz_oddzialy) oddzialyTableView.getItems().get(index);
                    switch (action) {
                        case update:
                            setOddzialToUpdate(oddzial);
                            break;
                        case delete:
                            QueriesManager queriesManager = new QueriesManager();
                            if(queriesManager.deleteRecord("Sz_oddzialy", "id", oddzial.getId()))
                            {
                                Main.getInstance().refreshAll();
                            }
                            break;
                    }
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

}
