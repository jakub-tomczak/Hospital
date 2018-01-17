package Controllers;

import Relations.Sz_lekarze;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Pracownicy {


    @FXML private TableColumn<Sz_pracownicy, String> firstName;
    @FXML private TableColumn<Sz_pracownicy, String> lastName;
    @FXML private TableColumn<Sz_pracownicy, String> type;
    @FXML private TableColumn<Sz_pracownicy, String> place;
    @FXML private TextArea textArea;
    @FXML private TableView<Sz_pracownicy> pracownicyTableView;


    @FXML
    public void initialize() {

        pracownicyTableView.getItems().setAll(getEmployees());
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
