package Controllers;

import Relations.Sz_lekarze;
import SQL.Command;
import SQL.QueriesManager;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

import java.sql.SQLException;

public class Pracownicy {


    public TextArea textArea;

    public void addStuff(MouseEvent mouseEvent) {
        System.out.println("Sending query!");

        QueriesManager queriesManager = new QueriesManager();
        Sz_lekarze lekarz = new Sz_lekarze();
        lekarz.setImie("Jan");
        lekarz.setNazwisko("Kowalski");
        try {
            queriesManager.addDoctor(lekarz, null);
        } catch (SQLException e) {
            System.out.println("Nie udało sie dodać lekarza. " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("class not found Nie udało sie dodać lekarza. " + e.getMessage());

        }
    }

    private void changeAreaText(String text)
    {
        System.out.println("Changed text field text!");
        textArea.setText("Nowy tekst " + text);
    }
}
