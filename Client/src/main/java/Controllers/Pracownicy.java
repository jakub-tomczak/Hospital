package Controllers;

import Relations.Sz_lekarze;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pracownicy {


    public TableView pielegniarkiTableView;
    public TableColumn firstNameNurse;
    public TableColumn lastNameNurse;
    public TableColumn typeNurse;
    public TableColumn placaNurse;


    @FXML private TableColumn<Sz_pracownicy, String> firstNameDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> lastNameDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> typeDoctor;
    @FXML private TableColumn<Sz_pracownicy, String> placaDoctor;
    @FXML private TextArea textArea;
    @FXML private TableView<Sz_pracownicy> lekarzeTableView;


    @FXML
    public void initialize() {

        lekarzeTableView.getItems().setAll(getEmployees());
       // lekarzeTableView.setPlaceholder(new Label("Brak danych w tabeli"));
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(typeDoctor);
        FilterSupport.addFilter(placaDoctor);
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
