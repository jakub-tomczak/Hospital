package Controllers;

import Main.Main;
import Relations.Sz_lekarze;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pracownicy implements IDisplayedScreen {


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


    @FXML private TableColumn<Sz_pracownicy, String> firstNameDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> lastNameDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> typeDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> placaDoctor;
    @FXML private TextArea textArea;
    @FXML private TableView<Sz_pracownicy> lekarzeTableView;
    @FXML private GridPane lekarzDodatkoweDane;

    @FXML
    public void initialize() {
        //dodaje siebie do listy ekranów odświeżalnych
        Main.screensList.add(this);

        lekarzeTableView.getItems().setAll(getEmployees());
       // lekarzeTableView.setPlaceholder(new Label("Brak danych w tabeli"));
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(typeDoctor);
        FilterSupport.addFilter(placaDoctor);

        typPracownika.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                lekarzDodatkoweDane.setVisible(newValue != typPracownikaPielegniarka);
            }
        });
    }

    public void refresh()
    {
        QueriesManager queriesManager = new QueriesManager();

        //update oddzialy
        List<String> listaOddzialow = null;
        try
        {
            listaOddzialow = queriesManager.getOddzialy();
            oddzialyComboBox.setItems(FXCollections.observableArrayList(listaOddzialow));
        }catch(SQLException e)
        {
            ExceptionHandler.displayException("Nie udało się pobrac najnowszej listy oddziałów!");
        }
    }

    @FXML
    private void getLekarze() {
        List<Sz_lekarze> lekarze = new ArrayList<>();
        try {
            lekarze = (new QueriesManager()).getDoctors();
        } catch (SQLException e) {
            ExceptionHandler.displayException(e);
        }

        System.out.println("Pobrano " + lekarze.size() +" lekarzy.");
    }

    private List<Sz_pracownicy> getEmployees() {
        List<Sz_pracownicy> employees = new ArrayList<>();
        return employees;
    }

    public void addStuff(MouseEvent mouseEvent) {
        System.out.println("Sending query!");

        QueriesManager queriesManager = new QueriesManager();

        Sz_lekarze doctor = new Sz_lekarze();
        doctor.setImie("Jan");
        doctor.setNazwisko("Kowalski");
        try {
            queriesManager.addDoctor(doctor, null);
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało sie dodać lekarza. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            ExceptionHandler.displayException("class not found Nie udało sie dodać lekarza. " + e.getMessage());

        }
    }

    private void changeAreaText(String text)
    {
        System.out.println("Changed text field text!");
        textArea.setText("Nowy tekst " + text);
    }


}
