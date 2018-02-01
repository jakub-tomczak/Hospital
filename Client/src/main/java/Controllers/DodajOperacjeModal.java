package Controllers;

import Main.Main;
import Relations.Sz_oddzialy;
import Relations.Sz_operacje;
import Relations.Sz_pacjenci;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import Utils.Validator;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DodajOperacjeModal implements IDisplayedScreen {
    private final String insertMode = "Tryb dodawania";
    private final String updateMode = "Tryb aktualizacji";
    private final String insertModeAcceptButtonText = "Dodaj";
    private final String updateModeAcceptButtonText = "Aktualizuj";
    public VBox vBoxAvailableDoctors;
    public ComboBox oddzialyComboBox;
    public TextField lastNameInput;
    public TextField operationNameInput;
    public TextField firstNameInput;
    public DatePicker dateInput;
    public TextField hoursInput;
    public TextField minutesInput;
    public Button performActionButton;
    public TextField PESELinput;
    private Sz_pacjenci pacjent;
    Pracownicy.FormMode formMode = Pracownicy.FormMode.insert;
    List<Sz_pracownicy> doctorsForOperation = new ArrayList<>();
    List<Sz_pracownicy> doctorsInHospital = new ArrayList<>();
    List<Sz_oddzialy> availableOddzialy = new ArrayList<>();
    Map<Sz_pracownicy,CheckBox> checkBoxSz_pracownicyMap = new HashMap<>();

    //used when updating
    Sz_operacje operationBeingUpdated = null;
    List<Sz_pracownicy> doctorsInOperationBeforeUpdate = new ArrayList<>();
    List<Sz_pracownicy> doctorsDroppedFromOperation = new ArrayList<>();
    List<Sz_pracownicy> doctorsAddedToOperation = new ArrayList<>();


    @FXML
    public void initialize()
    {
        dateInput.setDayCellFactory(picker -> new DateCell() {
            @Override
            public void updateItem(LocalDate date, boolean empty) {
                super.updateItem(date, empty);
                setDisable(empty || date.isBefore(LocalDate.now()));
            }
        });
        dateInput.setValue(LocalDate.now());
        refresh();
    }


    @Override
    public void refresh() {
        clearDoctorsForOperation();
        generateDoctorsList();
        getOddzialy();

        //unlock button if was locked - should not happen
        //performActionButton.setDisable(false);    //useless
    }
    public void setFormToUpdate(List<Sz_pracownicy> doctorsInOperation, Sz_pacjenci patient, Sz_operacje operation)
    {
        formMode = Pracownicy.FormMode.update;
        operationBeingUpdated = operation;
        performActionButton.setText(updateModeAcceptButtonText);
        doctorsInOperationBeforeUpdate = doctorsInOperation;
        if(!setDoctors(doctorsInOperation))
        {
            performActionButton.setDisable(true);
            return;
        }
        setPatientDataIntoForm(patient);
        setOperationPropertiesInForm(operation);
        setOddzialyValueInComboBox(operation);
    }

    private void setOddzialyValueInComboBox(Sz_operacje operation) {
        for(Sz_oddzialy oddzialy : availableOddzialy)
        {
            if(oddzialy.getId() == operation.getOddzialy_id())
            {
                oddzialyComboBox.setValue(oddzialy.getNazwa());
                return;
            }
        }
        ExceptionHandler.displayException("Nie udało się przyporządkować oddziału do edytowanej operacji!");
        performActionButton.setDisable(true);
    }

    private int countSelectedDoctors()
    {
        int checked = 0;
        for(CheckBox checkBox : checkBoxSz_pracownicyMap.values())
        {
            if(checkBox.isSelected())
            {
                checked += 1;
            }
        }
        return checked;
    }


    private void setOperationPropertiesInForm(Sz_operacje operation) {
        if(operation == null)
        {
            ExceptionHandler.displayException("Operacja nie istnieje!");
            performActionButton.setDisable(true);
            return;
        }
        operationNameInput.setText(operation.getRodzajoperacji());
        dateInput.setValue(operation.getDatagodzinarozpoczecia().toLocalDate());
        hoursInput.setText(String.format("%d", operation.getDatagodzinarozpoczecia().getHour()));
        minutesInput.setText(String.format("%d", operation.getDatagodzinarozpoczecia().getMinute()));
    }

    //sets doctors when updating
    private boolean setDoctors(List<Sz_pracownicy> doctorsInOperation) {
        if(doctorsInOperation == null || doctorsInOperation.size() == 0)
        {
            ExceptionHandler.displayException("Brak lekarzy");
            return false;
        }
        for(Sz_pracownicy pracownik : doctorsInOperation)
        {
            if(checkBoxSz_pracownicyMap.containsKey(pracownik))
            {
                checkBoxSz_pracownicyMap.get(pracownik).setSelected(true);
            }
        }
        return true;
    }

    private void setPatientDataIntoForm(Sz_pacjenci patient)
    {
        this.pacjent = patient;
        performActionButton.setText(insertModeAcceptButtonText);
        firstNameInput.setText(patient.getImie());
        lastNameInput.setText(patient.getNazwisko());
        PESELinput.setText(patient.getPesel());
    }

    public void setFormToInsert(Sz_pacjenci patient)
    {
        formMode = Pracownicy.FormMode.insert;
        operationBeingUpdated = null;

        setPatientDataIntoForm(patient);
    }

    private void clearDoctorsForOperation()
    {
        doctorsForOperation = new ArrayList<>();
    }

    private void getOddzialy() {
        QueriesManager queriesManager = new QueriesManager();
        availableOddzialy = queriesManager.getOddzialy();
        if (availableOddzialy != null) {
            List<String> nazwyOddzialow = new ArrayList<>();
            for (Sz_oddzialy oddzial : availableOddzialy) {
                nazwyOddzialow.add(oddzial.getNazwa());
            }
            oddzialyComboBox.setItems(FXCollections.observableList(nazwyOddzialow));
        }
        if(availableOddzialy.size() == 0)
        {
            ExceptionHandler.displayException("Dodaj oddział, aby móc dodawać operacje!");
            performActionButton.setDisable(true);
        }
    }
    private int getOddzialId()
    {
        for(Sz_oddzialy oddzial : availableOddzialy)
        {
            if(oddzial.getNazwa().equals(oddzialyComboBox.getValue()))
            {
                return oddzial.getId();
            }
        }
        return 0;
    }

    private void resetCheckboxesSelection()
    {
        if(checkBoxSz_pracownicyMap == null)
        {
            return;
        }
        for(CheckBox checkBox : checkBoxSz_pracownicyMap.values())
        {
            checkBox.setSelected(false);
        }
    }

    private void generateDoctorsList() {
        getLekarze();

        if(doctorsInHospital == null || doctorsInHospital.size() == 0)
        {
            vBoxAvailableDoctors.getChildren().add(new Label("Proszę dodać lekarza do systemu"));

            ExceptionHandler.displayException("Brak lekarzy do wykonania operacji!");
            performActionButton.setDisable(true);
            return;
        }

        for(Sz_pracownicy lekarz : doctorsInHospital)
        {
            //create checkbox
            CheckBox doctorCheckbox = new CheckBox(lekarz.getNazwisko() + " " + lekarz.getImie());

            checkBoxSz_pracownicyMap.put(lekarz, doctorCheckbox);
            vBoxAvailableDoctors.getChildren().add(doctorCheckbox);
            doctorCheckbox.selectedProperty().addListener(new ChangeListener<Boolean>() {
                @Override
                public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                    checkDoctor(lekarz, newValue);
                }
            });
        }
    }

    //used with checkbox
    private void checkDoctor(Sz_pracownicy lekarz, Boolean newValue) {
        if(formMode == Pracownicy.FormMode.update)
        {
            //check if doctor should be addedd or dropped from operation

            //adding and removing from dropped list
            if(doctorsInOperationBeforeUpdate.contains(lekarz) && !newValue)
            {
                doctorsDroppedFromOperation.add(lekarz);
            }
            if(doctorsDroppedFromOperation.contains(lekarz) && newValue)
            {
                doctorsDroppedFromOperation.remove(lekarz);
            }

            //adding and removing to add list
            if(!doctorsInOperationBeforeUpdate.contains(lekarz) && newValue)
            {
                doctorsAddedToOperation.add(lekarz);
            }
            if(doctorsAddedToOperation.contains(lekarz) && !newValue)
            {
                doctorsAddedToOperation.remove(lekarz);
            }

        }else
        {
            if(newValue)
            {
                //add
                doctorsForOperation.add(lekarz);
            }
            else
            {
                //remove
                if(doctorsForOperation.contains(lekarz))
                {
                    doctorsForOperation.remove(lekarz);
                }
            }

        }

    }

    //list of available doctors
    private void getLekarze()
    {
        List<Sz_pracownicy> lekarze = new ArrayList<>();

        doctorsInHospital = (new QueriesManager()).getPracownicy(Constants.DOCTOR);
    }

    public void performAction(ActionEvent actionEvent) {
        switch(formMode)
        {
            case insert:
                System.out.println("Dodawanie operacji");
                insert();
                break;
            case update:
                System.out.println("Aktualizacja operacji");
                update();
                break;
        }
    }



    private boolean validateData()
    {
        boolean result = true;
        result &= Validator.validateStringField(operationNameInput);
        if(!result)
        {
            ExceptionHandler.displayException("Wpisz poprawną nazwę operacji.");
        }
        result &= Validator.validateIntegerField(hoursInput, 0, 23, "Jako godzinę podaj liczbę całkowitą w przedziale <00;23>.");
        result &= Validator.validateIntegerField(minutesInput, 0, 59, "Jako minuty podaj liczbę całkowitą w przedziale <00;59>.");


        if(dateInput.getValue() == null)
        {
            ExceptionHandler.displayException("Proszę wybrać poprawną datę.");
            result = false;
        }

        if(countSelectedDoctors() < 1)
        {
            ExceptionHandler.displayException("Proszę zaznaczyć co najmniej jednego lekarza do operacji.");
            result = false;
        }
        if(oddzialyComboBox.getValue() == null)
        {
            ExceptionHandler.displayException("Proszę wybrać oddział!");
            result = false;
        }
        return result;
    }

    private void insert() {
        if(!validateData())
        {
            return;
        }


        Sz_operacje operacja = new Sz_operacje();
        //patient
        operacja.setPacjenci_id(pacjent.getId());

        operacja.setOddzialy_id(getOddzialId());
        operacja.setRodzajoperacji(operationNameInput.getText());


        int hour = Integer.parseInt(hoursInput.getText());
        int minutes = Integer.parseInt(minutesInput.getText());
        LocalTime time = LocalTime.of(hour, minutes);
        LocalDateTime dateTime = LocalDateTime.of(dateInput.getValue(), time);
        operacja.setDatagodzinarozpoczecia(dateTime);


        QueriesManager queriesManager = new QueriesManager();

        boolean globalSuccess = true;
        //switch off autocommit
        queriesManager.setAutoCommitMode(false);


        //wypełnianie relacji sz_wykonywaneoperacje
        int operacja_id = queriesManager.addOperation(operacja);
        globalSuccess &= operacja_id != -1;

        //dodawanie lekarzy
        for(Sz_pracownicy lekarz : doctorsForOperation)
        {
            queriesManager.dodajUsunLekarzOperacja(lekarz.getPracownikid(), operacja_id, false);
        }


        System.out.println("Inserting state = " + globalSuccess);
        if(!queriesManager.commitOrRollback(globalSuccess)) //commit on success, rollback on failure
        {
            //failure when setting commit/rollback
            ExceptionHandler.displayException("Nie udało się zatwierdzić zmian!");
        }

        //restore autocommit mode
        queriesManager.setAutoCommitMode(true);

        if(globalSuccess)
        {
            ExceptionHandler.displayException("Udało się zaktualizować operację!");
            Main.getInstance().refreshAll();
            closeWindow();
        }
    }

    private void update() {
        if(formMode != Pracownicy.FormMode.update)
            return;
        if(operationBeingUpdated == null)
        {
            ExceptionHandler.displayException("Brak wybranej operacji do aktualizacji");
        }

        if(!validateData())
        {
            return;
        }

        //walidacja się powiodła
        operationBeingUpdated.setOddzialy_id(getOddzialId());
        operationBeingUpdated.setRodzajoperacji(operationNameInput.getText());


        int hour = Integer.parseInt(hoursInput.getText());
        int minutes = Integer.parseInt(minutesInput.getText());
        LocalTime time = LocalTime.of(hour, minutes);
        LocalDateTime dateTime = LocalDateTime.of(dateInput.getValue(), time);
        operationBeingUpdated.setDatagodzinarozpoczecia(dateTime);


        QueriesManager queriesManager = new QueriesManager();

        boolean globalSuccess = true;
        //switch off autocommit
        queriesManager.setAutoCommitMode(false);


        //wypełnianie relacji sz_wykonywaneoperacje
        //remove relations from dropped list
        for(Sz_pracownicy lekarz : doctorsDroppedFromOperation)
        {
            System.out.println("Removing " + lekarz.getNazwisko());
            globalSuccess &= queriesManager.dodajUsunLekarzOperacja(lekarz.getPracownikid(), operationBeingUpdated.getOperacjaid(), true);
        }

        //add relations from add list
        for(Sz_pracownicy lekarz : doctorsAddedToOperation)
        {
            System.out.println("Adding " + lekarz.getNazwisko());
            globalSuccess &= queriesManager.dodajUsunLekarzOperacja(lekarz.getPracownikid(), operationBeingUpdated.getOperacjaid(), false);
        }

        //wypełnianie relacji pacjenta
        //brak bo update

        //wypełnianie relacji operacje
        globalSuccess &= queriesManager.updateOperation(operationBeingUpdated);

        System.out.println("Updating state = " + globalSuccess);
        if(!queriesManager.commitOrRollback(globalSuccess)) //commit on success, rollback on failure
        {
            //failure when setting commit/rollback
            ExceptionHandler.displayException("Nie udało się zatwierdzić zmian!");
        }

        //restore autocommit mode
        queriesManager.setAutoCommitMode(true);

        if(globalSuccess)
        {
            ExceptionHandler.displayException("Udało się zaktualizować operację!");
            Main.getInstance().refreshAll();
            closeWindow();
        }
    }

    private void closeWindow() {
        Stage stage = (Stage)performActionButton.getScene().getWindow();
        if(stage != null)
        {
            stage.close();
        }
    }
}
