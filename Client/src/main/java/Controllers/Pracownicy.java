package Controllers;

import Main.Main;
import Relations.Sz_lekarze;
import Relations.Sz_oddzialy;
import Relations.Sz_pielegniarki;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import Utils.Validator;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import com.sun.javafx.collections.MappingChange;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

public class Pracownicy implements IDisplayedScreen {
    private final String insertMode = "Tryb dodawania";
    private final String updateMode = "Tryb aktualizacji";
    private final String insertModeAcceptButtonText = "Dodaj";
    private final String updateModeAcceptButtonText = "Aktualizuj";
    public RadioButton typPracownikaLekarz;
    public TableColumn oddzialDoctor;
    Sz_pracownicy itemToUpdate = null;
    private FormMode formMode = FormMode.insert;
    List<Sz_oddzialy> oddzialy = new ArrayList<>();

    public Button formAcceptButton;
    public ComboBox oddzialyComboBox;
    public Label formModeLabel;

    public TableView pielegniarkiTableView;
    public TableColumn firstNameNurse;
    public TableColumn lastNameNurse;
    public TableColumn typeNurse;
    public TableColumn placaNurse;
    public TableColumn usunDoctor;
    public TableColumn aktualizujDoctor;

    public ToggleGroup typPracownika;
    public RadioButton typPracownikaPielegniarka;
    public TextField firstNameTextInput;
    public TextField lastNameTextInput;
    public TextField salaryTextInput;
    public TextField specializationTextInput;
    public TextField degreeTextInput;


    @FXML
    private TableColumn<Sz_pracownicy, String> firstNameDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, String> lastNameDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, Double> placaDoctor;
    @FXML
    private TableView<Sz_pracownicy> lekarzeTableView;
    @FXML
    private GridPane lekarzDodatkoweDane;

    @FXML
    public void initialize() {
        //dodaje siebie do listy ekranów odświeżalnych
        Main.screensList.add(this);

        //lekarze
        firstNameDoctor.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, String>("imie"));
        lastNameDoctor.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, String>("nazwisko"));
        placaDoctor.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, Double>("pensja"));
        //oddzialDoctor.setCellValueFactory(param -> new ReadOnlyStringWrapper("asd"));
        oddzialDoctor.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_pracownicy, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_pracownicy, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });

        //przycisk do usuwania lekarza
        usunDoctor.setSortable(false);
        usunDoctor.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_pracownicy, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_pracownicy, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        usunDoctor.setCellFactory(
                new Callback<TableColumn<Sz_pracownicy, Boolean>, TableCell<Sz_pracownicy, Boolean>>() {

                    @Override
                    public TableCell<Sz_pracownicy, Boolean> call(TableColumn<Sz_pracownicy, Boolean> p) {
                        return new ButtonFactory("Usuń", ButtonAction.delete);
                    }
                });

        //przycisk do aktualizacji lekarza
        aktualizujDoctor.setSortable(false);
        aktualizujDoctor.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_pracownicy, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_pracownicy, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        aktualizujDoctor.setCellFactory(
                new Callback<TableColumn<Sz_pracownicy, Boolean>, TableCell<Sz_pracownicy, Boolean>>() {

                    @Override
                    public TableCell<Sz_pracownicy, Boolean> call(TableColumn<Sz_pracownicy, Boolean> p) {
                        return new ButtonFactory("Modyfikuj", ButtonAction.update);
                    }
                });


        // lekarzeTableView.setPlaceholder(new Label("Brak danych w tabeli"));
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(oddzialDoctor);


        //radio button toggle
        typPracownika.selectedToggleProperty().addListener((observable, oldValue, newValue) -> lekarzDodatkoweDane.setVisible(newValue != typPracownikaPielegniarka));
    }

    public void clearFormFields(ActionEvent actionEvent) {
        //global flag
        formMode = FormMode.insert;

        //insert mode stuff
        formModeLabel.setText(insertMode);
        formModeLabel.setTextFill(Paint.valueOf("#15ff00"));    //green

        //set button text
        formAcceptButton.setText(insertModeAcceptButtonText);

        //radiobutton stuff
        typPracownikaPielegniarka.setSelected(true);

        //disable choosing when in update mode
        typPracownikaPielegniarka.setDisable(formMode == FormMode.update);
        typPracownikaLekarz.setDisable(formMode == FormMode.update);

        //clear fields
        firstNameTextInput.setText("");
        lastNameTextInput.setText("");
        salaryTextInput.setText("");
        specializationTextInput.setText("");
        degreeTextInput.setText("");

        //clear errors in fields
        clearValidationMarks();
    }

    public void refresh() {

        //update oddzialy
        getOddzialy();

        getLekarze();

        //clear form because data could change
        clearFormFields(null);
    }

    private void getOddzialy() {
        QueriesManager queriesManager = new QueriesManager();
        oddzialy = queriesManager.getOddzialy();
        if (oddzialy != null) {
            List<String> nazwyOddzialow = new ArrayList<>();
            for (Sz_oddzialy oddzial : oddzialy) {
                nazwyOddzialow.add(oddzial.getNazwa());
            }
            oddzialyComboBox.setItems(FXCollections.observableList(nazwyOddzialow));
        }
    }

    @FXML
    private void getLekarze() {
        List<Sz_lekarze> lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getDoctors();

        if (lekarze != null) {
            lekarzeTableView.getItems().setAll(lekarze);

        }
        lekarzeTableView.refresh();
    }

    private Sz_pracownicy getWorkerFromForm() {
        Sz_pracownicy pracownik = null;
        switch (formMode) {
            case insert:
                //if inserting -> detect type using isSelected property in pielegniarka radio button
                pracownik = typPracownikaPielegniarka.isSelected() ? new Sz_pielegniarki() : new Sz_lekarze();
                break;
            case update:
                //if updating -> get type from global itemToUpdate
                pracownik = itemToUpdate;
                break;
        }
        if (pracownik == null) {
            return null;
        }

        if (!validateForm()) {
            return null;
        }

        //if validation succeded then assign values from fields
        pracownik.setImie(firstNameTextInput.getText());
        pracownik.setNazwisko(lastNameTextInput.getText());
        pracownik.setPensja(Double.parseDouble(salaryTextInput.getText()));
        pracownik.setOddzialy_id(getOddzialIdFromComboBox());
        if (pracownik.getStanowisko() == Constants.DOCTOR) {
            ((Sz_lekarze) pracownik).setSpecjalizacja(specializationTextInput.getText());
            ((Sz_lekarze) pracownik).setStopiennaukowy(degreeTextInput.getText());
        }
        return pracownik;
    }

    //method invoked after pressing update button
    private void fillFormToUpdate(Sz_pracownicy workerToUpdate) {
        clearValidationMarks();
        itemToUpdate = workerToUpdate;
        //global flag
        formMode = FormMode.update;
        //button text
        formAcceptButton.setText(updateModeAcceptButtonText);
        formModeLabel.setText(updateMode);
        formModeLabel.setTextFill(Paint.valueOf("#FF8B1E"));    //orange

        typPracownikaPielegniarka.setSelected(workerToUpdate.getStanowisko() == Constants.NURSE);
        typPracownikaLekarz.setSelected(workerToUpdate.getStanowisko() == Constants.DOCTOR);

        //disable changing selection of radio buttons when in update mode
        typPracownikaPielegniarka.setDisable(formMode == FormMode.update);
        typPracownikaLekarz.setDisable(formMode == FormMode.update);

        firstNameTextInput.setText(workerToUpdate.getImie());
        lastNameTextInput.setText(workerToUpdate.getNazwisko());

        salaryTextInput.setText(String.format(Locale.US, "%.2f", workerToUpdate.getPensja()));

        //set oddzial name in combobox
        setOddzialComboBox(workerToUpdate.getOddzialy_id());

        if (workerToUpdate.getStanowisko() == 1) {
            Sz_lekarze doctor = (Sz_lekarze) workerToUpdate;
            specializationTextInput.setText(doctor.getSpecjalizacja());
            degreeTextInput.setText(doctor.getStopiennaukowy());
        }
    }

    private int getOddzialIdFromComboBox() {

        int oddzialId = -1;
        //combobox with oddzialy validation
        if (oddzialyComboBox.getItems().size() == 0) {
            ExceptionHandler.displayException("Dodaj najpierw oddział!");
            return oddzialId;
        }
        if (oddzialyComboBox.getValue() == null) {
            ExceptionHandler.displayException("Wybierz oddział");
            return oddzialId; //-1
        }
        String oddzialToFind = oddzialyComboBox.getValue().toString();
        for (Sz_oddzialy oddzial : oddzialy) {
            if (oddzial.getNazwa().equals(oddzialToFind)) {
                oddzialId = oddzial.getId();
                break;
            }
        }
        return oddzialId;
    }

    private String getOddzialIdFromComboBox(Sz_pracownicy pracownik) {
        for (Sz_oddzialy oddzial : oddzialy) {
            if (oddzial.getId() == pracownik.getOddzialy_id()) {
                return oddzial.getNazwa();
            }
        }
        return "";
    }

    private void setOddzialComboBox(int id) {
        boolean isset = false;
        for (Sz_oddzialy oddzial : oddzialy) {
            if (oddzial.getId() == id) {
                oddzialyComboBox.setValue(oddzial.getNazwa());
                isset = true;
            }
        }
        if (!isset) {
            ExceptionHandler.displayException("Pracownik ma przypisany niepoprawny oddział!");
        }
    }


    private boolean validateForm() {

        int oddzialId = getOddzialIdFromComboBox();
        if (oddzialId < 0) {
            return false;
        }

        boolean result = true;
        if (formMode == FormMode.update && itemToUpdate != null && itemToUpdate.getStanowisko() == Constants.DOCTOR ||
                formMode == FormMode.insert && !typPracownikaPielegniarka.isSelected()) {
            //check for doctor validation
            result &= Validator.validateNumberField(salaryTextInput);
            result &= Validator.validateStringField(firstNameTextInput);
            result &= Validator.validateStringField(lastNameTextInput);
            result &= Validator.validateStringField(specializationTextInput);
            result &= Validator.validateStringField(degreeTextInput);
            return result;
        } else {
            //check for nurse validation
            result &= Validator.validateNumberField(salaryTextInput);
            result &= Validator.validateStringField(firstNameTextInput);
            result &= Validator.validateStringField(lastNameTextInput);
            return result;
        }
    }

    private void clearValidationMarks() {
        lastNameTextInput.setStyle("-fx-background-color: #ffffff");
        salaryTextInput.setStyle("-fx-background-color: #ffffff");
        specializationTextInput.setStyle("-fx-background-color: #ffffff");
        degreeTextInput.setStyle("-fx-background-color: #ffffff");
    }

    public void acceptForm(ActionEvent actionEvent) {
        Sz_pracownicy pracownik = getWorkerFromForm();
        if (pracownik == null)
            return;

        QueriesManager queriesManager = new QueriesManager();
        switch (formMode) {
            case insert:
                if (pracownik.getStanowisko() == Constants.DOCTOR) {
                    queriesManager.addDoctor((Sz_lekarze) pracownik);
                } else {
                    queriesManager.addNurse((Sz_pielegniarki) pracownik);
                }
                break;
            case update:
                if (pracownik.getStanowisko() == Constants.DOCTOR) {
                    queriesManager.updateDoctor((Sz_lekarze) pracownik);
                } else {
                    queriesManager.updateNurse((Sz_pielegniarki) pracownik);
                }
                break;
        }
        refresh();
    }


    public enum FormMode {
        insert,
        update
    }

    public enum ButtonAction {
        update,
        delete
    }

    private class ButtonFactory extends TableCell<Sz_pracownicy, Boolean> {

        Button cellButton = null;


        public ButtonFactory(String buttonName, ButtonAction action) {
            cellButton = new Button(buttonName);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = getTableRow().getIndex();
                    Sz_pracownicy worker = (Sz_lekarze) lekarzeTableView.getItems().get(index);
                    QueriesManager query = new QueriesManager();

                    switch (action) {
                        case update:
                            fillFormToUpdate(worker);
                            break;
                        case delete:
                            if (query.deleteWorker(worker.getPracownikid(), worker.getStanowisko())) {
                                refresh();
                            }
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
