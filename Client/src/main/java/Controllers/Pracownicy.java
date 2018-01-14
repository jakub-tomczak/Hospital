package Controllers;

import SQL.Command;
import SQL.QueriesManager;
import javafx.scene.control.TextArea;
import javafx.scene.input.MouseEvent;

public class Pracownicy {


    public TextArea textArea;

    public void addStuff(MouseEvent mouseEvent) {
        System.out.println("Sending query!");

        QueriesManager queriesManager = new QueriesManager();
        queriesManager.addDoctor("nowy doktor", "jego nazwisko", () -> {
            changeAreaText("Elo");
            return false;
        });
    }

    private void changeAreaText(String text)
    {
        System.out.println("Changed text field text!");
        textArea.setText("Nowy tekst " + text);
    }
}
