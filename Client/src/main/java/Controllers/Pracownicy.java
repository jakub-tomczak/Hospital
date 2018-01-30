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
import javafx.scene.control.cell.PropertyValueFactory;
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


        // lekarzeTableView.setPlaceholder(new Label("Brak danych w tabeli"));
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(typeDoctor);


        //radio button toggle
        typPracownika.selectedToggleProperty().addListener((observable, oldValue, newValue) -> lekarzDodatkoweDane.setVisible(newValue != typPracownikaPielegniarka));
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
    }

    @FXML
    private void getLekarze() {
        List<Sz_lekarze> lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getDoctors();
        if (lekarze != null && lekarze.size() > 0)
            lekarzeTableView.getItems().setAll(lekarze);

        System.out.println("Pobrano " + lekarze.size() + " lekarzy.");
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

        queriesManager.addDoctor(doctor, null);

    }

    private void changeAreaText(String text) {
        System.out.println("Changed text field text!");
        textArea.setText("Nowy tekst " + text);
    }


}
