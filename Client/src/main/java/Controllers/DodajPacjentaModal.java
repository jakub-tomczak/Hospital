package Controllers;

import Relations.Sz_pacjenci;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import Utils.Validator;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.SQLException;

public class DodajPacjentaModal {
    public Button dodaj;
    public TextField kod;
    public TextField nazwisko;
    public TextField imie;
    public TextField pesel;
    public TextField adres;
    public TextField miasto;
    public Alert alert;

    @FXML
    public void initialize() {
        alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Błąd");
        alert.setHeaderText(null);
        dodaj.disableProperty().bind(
                Bindings.isEmpty(kod.textProperty())
                        .or(Bindings.isEmpty(nazwisko.textProperty()))
                        .or(Bindings.isEmpty(imie.textProperty()))
                        .or(Bindings.isEmpty(pesel.textProperty()))
                        .or(Bindings.isEmpty(adres.textProperty()))
                        .or(Bindings.isEmpty(miasto.textProperty()))
        );
    }

    public boolean validateInput() {
        //PESEL
        boolean result = true;
        result &= Validator.validateStringField(imie);

        result &= Validator.validateStringField(nazwisko);
        result &= Validator.validateStringField(adres);
        result &= Validator.validateStringField(miasto);
        boolean isNumeric = pesel.getText().chars().allMatch(Character::isDigit);
        boolean is11Digits = pesel.getText().length() == 11;
        if(!isNumeric || !is11Digits)
        {
            pesel.setStyle("-fx-background-color: #cd3402");
        }else
        {
            pesel.setStyle("-fx-background-color: #ffffff");
        }
        //kod
        boolean format = kod.getText().matches("\\d{2}+[-]+\\d{3}");
        if(!format)
            kod.setStyle("-fx-background-color: #cd3402");
        else
            kod.setStyle("-fx-background-color: #ffffff");


        if (isNumeric && is11Digits && format && result)
            return true;
        return false;
    }
    @FXML
    public void addPatient() {
        if (validateInput()) {

            Sz_pacjenci patient = new Sz_pacjenci(imie.getText(), nazwisko.getText(), pesel.getText(), adres.getText(), miasto.getText(), kod.getText(),0);
            QueriesManager query = new QueriesManager();
            query.addPatient(patient);

            Stage stage = (Stage) pesel.getScene().getWindow();
            if(stage != null)
            {
                stage.close();
            }
        } else {
            alert.setContentText("Błędny format danych");
            alert.showAndWait();
        }
    }
}
