package Controllers;

import Main.Main;
import Relations.Sz_lekarze;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import Utils.Validator;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.property.SimpleBooleanProperty;
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

public class Pracownicy implements IDisplayedScreen {
    private final String insertMode = "Tryb dodawania";
    private final String updateMode = "Tryb aktualizacji";
    private final String insertModeAcceptButtonText = "Dodaj";
    private  final String updateModeAcceptButtonText = "Aktualizuj";

    public Button formAcceptButton;
    private FormMode formMode = FormMode.insert;


    public TableView pielegniarkiTableView;
    public TableColumn firstNameNurse;
    public TableColumn lastNameNurse;
    public TableColumn typeNurse;
    public TableColumn placaNurse;
    public ToggleGroup typPracownika;
    public RadioButton typPracownikaPielegniarka;
    public TextField firstNameTextInput;
    public TextField lastNameTextInput;
    public TextField salaryTextInput;
    public TextField specializationTextInput;
    public TextField degreeTextInput;
    public ComboBox oddzialyComboBox;
    public TableColumn usunDoctor;
    public Label formModeLabel;


    @FXML
    private TableColumn<Sz_pracownicy, String> firstNameDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, String> lastNameDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, String> typeDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, Double> placaDoctor;
    @FXML
    private TextArea textArea;
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

        // lekarzeTableView.setPlaceholder(new Label("Brak danych w tabeli"));
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(typeDoctor);


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
        QueriesManager queriesManager = new QueriesManager();

        //update oddzialy
        List<String> listaOddzialow = queriesManager.getOddzialy();
        if(listaOddzialow != null)
        {
            oddzialyComboBox.setItems(FXCollections.observableArrayList(listaOddzialow));
        }


        getLekarze();
        //clear form because data could change
        clearFormFields(null);
    }

    @FXML
    private void getLekarze() {
        List<Sz_lekarze> lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getDoctors();

        if (lekarze != null && lekarze.size() > 0)
        {
            lekarzeTableView.getItems().setAll(lekarze);

        }
        lekarzeTableView.refresh();

    }

    public void addStuff(MouseEvent mouseEvent) {
        System.out.println("Sending query!");

        QueriesManager queriesManager = new QueriesManager();

        Sz_lekarze doctor = new Sz_lekarze();
        doctor.setImie("Jan");
        doctor.setNazwisko("Kowalski");

        queriesManager.addDoctor(doctor, null);

    }

    private void changeAreaText(String text) {
        System.out.println("Changed text field text!");
        textArea.setText("Nowy tekst " + text);
    }
    private void fillFormToUpdate(Sz_pracownicy workerToUpdate)
    {
        //global flag
        formMode = FormMode.update;
        //button text
        formAcceptButton.setText(updateModeAcceptButtonText);
        formModeLabel.setText(updateMode);
        formModeLabel.setTextFill(Paint.valueOf("#FF8B1E"));    //orange

        typPracownikaPielegniarka.setSelected(workerToUpdate.getStanowisko() == 2);

        firstNameTextInput.setText(workerToUpdate.getImie());
        lastNameTextInput.setText(workerToUpdate.getNazwisko());
        salaryTextInput.setText(String.format("%d", workerToUpdate.getPensja()));

        if(workerToUpdate.getStanowisko() == 1)
        {
            Sz_lekarze doctor = (Sz_lekarze)workerToUpdate;
            specializationTextInput.setText(doctor.getSpecjalizacja());
            degreeTextInput.setText(doctor.getStopiennaukowy());
        }
    }
    Sz_pracownicy itemToUpdate = null;
    private boolean validateForm()
    {
        boolean result = true;
        if(formMode == FormMode.update && itemToUpdate != null && itemToUpdate.getStanowisko() == 1 ||
                formMode == FormMode.insert && !typPracownikaPielegniarka.isSelected())
        {
            //check for doctor validation
            result &= Validator.validateNumberField(salaryTextInput);
            result &= Validator.validateStringField(firstNameTextInput);
            result &= Validator.validateStringField(lastNameTextInput);
            result &= Validator.validateStringField(specializationTextInput);
            result &= Validator.validateStringField(degreeTextInput);
            return  result;
        }
        else
        {
            //check for nurse validation
            result &= Validator.validateNumberField(salaryTextInput);
            result &= Validator.validateStringField(firstNameTextInput);
            result &= Validator.validateStringField(lastNameTextInput);
            return  result;
        }
    }

    private void clearValidationMarks()
    {
        lastNameTextInput.setStyle("-fx-background-color: #ffffff");
        salaryTextInput.setStyle("-fx-background-color: #ffffff");
        specializationTextInput.setStyle("-fx-background-color: #ffffff");
        degreeTextInput.setStyle("-fx-background-color: #ffffff");
    }

    public void acceptForm(ActionEvent actionEvent) {
        if(!validateForm())
        {
            ExceptionHandler.displayException("Proszę uzupełnić pola wypełnione na czerwono!");
        }
        switch (formMode)
        {
            case insert:
                ExceptionHandler.displayException("insert");
                break;
            case update:
                ExceptionHandler.displayException("update");
                break;
        }
    }


    public enum FormMode
    {
        insert,
        update
    }
    public enum ButtonAction
    {
        update,
        delete
    }

    private class ButtonFactory extends TableCell< Sz_pracownicy, Boolean> {

        Button cellButton = null;


        public ButtonFactory(String buttonName, ButtonAction action) {
            cellButton = new Button(buttonName);
            cellButton.setOnAction(new EventHandler<ActionEvent>(){

                @Override
                public void handle(ActionEvent t) {
                    int index = getTableRow().getIndex();
                    Sz_pracownicy worker = (Sz_lekarze) lekarzeTableView.getItems().get(index);
                    QueriesManager query = new QueriesManager();

                    switch(action)
                    {
                        case update:
                            fillFormToUpdate(worker);
                            break;
                        case delete:
                            if(query.deleteWorker(worker.getPracownikid(), worker.getStanowisko()))
                            {
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
            if(!empty){
                setGraphic(cellButton);
            }
        }
    }

}
