package Controllers;

import Main.Main;
import Relations.*;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableObjectValue;
import javafx.beans.value.ObservableStringValue;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.stream.IntStream;

public class DodajBadanieModal{
    public ComboBox day;
    public ComboBox month;
    public ComboBox year;
    public ComboBox hour;
    public ComboBox minute;
    public Button dodaj;
    public TextField nazwa;
    private Sz_pacjenci pacjent;
    @FXML
    private TableColumn<Sz_pracownicy, String> firstNameDoctor;
    @FXML
    private TableColumn<Sz_pracownicy, String> lastNameDoctor;
    @FXML
    private TableColumn ward;
    @FXML
    private TableColumn<Sz_lekarze,String> specialty;
    private List<Sz_oddzialy> wards;
    @FXML
    private TableView<Sz_pracownicy> lekarzeTableView;
    public void setPacjent(Sz_pacjenci pacjent)
    {
        this.pacjent=pacjent;
    }
    @FXML
    public void initialize()
    {
      fillDateComboBoxes();
      day.getSelectionModel().select(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
      month.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
      hour.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
      minute.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
      year.getSelectionModel().select(Calendar.getInstance().get(Calendar.YEAR)-1900);
        firstNameDoctor.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, String>("imie"));
        lastNameDoctor.setCellValueFactory(new PropertyValueFactory<Sz_pracownicy, String>("nazwisko"));
        specialty.setCellValueFactory(new PropertyValueFactory<Sz_lekarze, String>("specjalizacja"));
        ward.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_pracownicy, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_pracownicy, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });
        FilterSupport.addFilter(firstNameDoctor);
        FilterSupport.addFilter(lastNameDoctor);
        FilterSupport.addFilter(ward);
        FilterSupport.addFilter(specialty);
        dodaj.disableProperty().bind(
                Bindings.isEmpty(nazwa.textProperty())
                        .or((lekarzeTableView.getSelectionModel().selectedItemProperty().isNull())
        ));
    }

    public void fillDateComboBoxes()
    {
        for(int i=1;i<32;i++)
        {
            day.getItems().add(i);
        }
        for(int i=1;i<13;i++)
        {
            month.getItems().add(i);
        }
        for(int i=0;i<24;i++)
        {
            hour.getItems().add(i);
        }
        for(int i=0;i<60;i++)
        {
            minute.getItems().add(i);
        }
        for(int i=1900;i<2050;i++)
        {
            year.getItems().add(i);
        }
    }


    @FXML
    public void getLekarze() {
        List<Sz_pracownicy> lekarze = new ArrayList<>();

        lekarze = (new QueriesManager()).getPracownicy(Constants.DOCTOR);

        wards = (new QueriesManager()).getOddzialy();
        if(lekarze!=null) {
            lekarzeTableView.getItems().clear();
            for (Sz_pracownicy lekarz : lekarze) {
                String oddzial = getOddzialIdFromComboBox(lekarz);
                lekarzeTableView.getItems().add(lekarz);
            }
        }

        lekarzeTableView.refresh();
    }

    private String getOddzialIdFromComboBox(Sz_pracownicy pracownik) {
        for (Sz_oddzialy oddzial : wards) {
            if (oddzial.getId() == pracownik.getOddzialy_id()) {
                return oddzial.getNazwa();
            }
        }
        return "";
    }

    @FXML
    public  void addExamination()
    {
        Sz_pracownicy lekarz = lekarzeTableView.getSelectionModel().getSelectedItem();
        if(lekarz!=null)
        {
            String date = formattedDate();
            Sz_badania badanie = new Sz_badania(nazwa.getText(),formattedDate(),lekarz.getOddzialy_id(),pacjent.getId(),lekarz.getPracownikid(),0);
            new QueriesManager().addExamination(badanie);
            Main.getInstance().refreshAll();
        }
        ExceptionHandler.showMessage("Badanie dodane pomyślnie");
    }
    public String formattedDate()
    {
        String date="";
        date+=year.getSelectionModel().getSelectedItem().toString();
        date+='/'+month.getSelectionModel().getSelectedItem().toString();
        date+='/'+day.getSelectionModel().getSelectedItem().toString()+' ';
        date+=hour.getSelectionModel().getSelectedItem().toString()+':';
        date+=minute.getSelectionModel().getSelectedItem().toString()+":00";
        return  date;
    }
}
