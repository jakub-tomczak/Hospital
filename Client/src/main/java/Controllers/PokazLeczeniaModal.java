package Controllers;

import Relations.*;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import Utils.IDisplayedScreen;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PokazLeczeniaModal implements IDisplayedScreen {
    public TableView<Sz_leczenia> leczenia;
    public TextArea rozpoznanie;
    public Button dodaj;
    public TableView<Sz_stosowaneleki> leki;
    public TableColumn<Sz_leczenia,String> dataRozpoznania;
    public TableColumn<Sz_stosowaneleki,String > nrSerii;
    public TableColumn<Sz_stosowaneleki,String> dataPodania;
    public TableColumn<Sz_stosowaneleki,String> dawka;
    public TableColumn nazwaLeku;
    public Button usun;
    public Button addMedicine;
    public Button deleteMedicine;
    public ComboBox lekCombo;
    public TextField dawkaT;
    public TextField seriaT;
    public Button updateLeczenie;
    private Sz_pacjenci pacjent;
    private List<Sz_leki> lekiList;
    private Sz_leczenia pomLeczenie;


    @FXML
    public void initialize()
    {
        dataRozpoznania.setCellValueFactory(new PropertyValueFactory<Sz_leczenia,String>("datarozpoznania"));
        nrSerii.setCellValueFactory(new PropertyValueFactory<Sz_stosowaneleki,String>("numerSerii"));
        dataPodania.setCellValueFactory(new PropertyValueFactory<Sz_stosowaneleki,String>("data"));
        dawka.setCellValueFactory(new PropertyValueFactory<Sz_stosowaneleki,String>("dawkal"));
        nazwaLeku.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_stosowaneleki, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_stosowaneleki, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });

        usun.disableProperty().bind(
                Bindings.not(leczenia.getSelectionModel().selectedItemProperty().isNotNull())
        );
        updateLeczenie.disableProperty().bind(
                Bindings.not(leczenia.getSelectionModel().selectedItemProperty().isNotNull())
        );

        deleteMedicine.disableProperty().bind(
                Bindings.not(leki.getSelectionModel().selectedItemProperty().isNotNull()
        ));
        addMedicine.disableProperty().bind(
                Bindings.not(leczenia.getSelectionModel().selectedItemProperty().isNotNull()).or(
                Bindings.isEmpty(dawkaT.textProperty())).or(
                Bindings.isEmpty(seriaT.textProperty()))
        );
        leczenia.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                rozpoznanie.setText(newSelection.getRozpoznanie());
                getStosowaneLeki(newSelection);
                pomLeczenie=newSelection;
            }
        });
        refresh();
       // deleteButton.setDisable(true);
    }

    private String getOddzialIdFromComboBox(Sz_stosowaneleki lekS) {
        for (Sz_leki lek : lekiList) {
            if (Integer.valueOf(lek.getId()) == lekS.getLekID()) {
                return lek.getNazwaHandlowa();
            }
        }
        return "";
    }


    public void getLeki()
    {
        lekiList = new QueriesManager().getLeki();
        if (lekiList != null) {
            List<String> nazwyLekow = new ArrayList<>();
            for (Sz_leki lek : lekiList) {
                nazwyLekow.add(lek.getNazwaHandlowa());
            }
            lekCombo.setItems(FXCollections.observableList(nazwyLekow));
        }
    }
    public void getLeczenia()
    {
        List<Sz_leczenia> leczenia = new QueriesManager().getLeczenia(pacjent);
        if(leczenia.size()>0)
        {
            this.leczenia.getItems().clear();
            this.leczenia.getItems().addAll(leczenia);
        }

    }

    public void addLeczenie()
    {
        if(rozpoznanie.getText().trim().equals(""))
        {
            ExceptionHandler.showMessage("Wypełnij rozpoznanie!");
        }
        else {
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

            Sz_leczenia leczenie = new Sz_leczenia(pacjent.getId(), 0, (sdf.format(cal.getTime())), rozpoznanie.getText());
            new QueriesManager().insertLeczenia(leczenie);
        }
        refresh();
    }

    public void deleteLeczenie()
    {
        Sz_leczenia leczenie = leczenia.getSelectionModel().getSelectedItem();
        new QueriesManager().deleteLeczenia(leczenie);
        refresh();
    }

    public void setPacjent(Sz_pacjenci pacjent)
    {
        this.pacjent=pacjent;
    }

    public void addLek()
    {
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        int leczenieID =   leczenia.getSelectionModel().getSelectedItem().getLeczenieid();
        pomLeczenie = leczenia.getSelectionModel().getSelectedItem();
        Sz_stosowaneleki stosowaneleki = new Sz_stosowaneleki(0,seriaT.getText(),getLekIdFromComboBox(),leczenieID
              ,dawkaT.getText(),sdf.format(cal.getTime()));
        new QueriesManager().addStosowanyLek(stosowaneleki);
        refresh();
        getStosowaneLeki(pomLeczenie);
    }

    public void deleteLek()
    {
        new QueriesManager().deleteStosowanyLek(leki.getSelectionModel().getSelectedItem());
        refresh();
        getStosowaneLeki(pomLeczenie);

    }

    private int getLekIdFromComboBox() {

        int lekID = -1;
        //combobox with oddzialy validation
        if (lekCombo.getItems().size() == 0) {
            ExceptionHandler.displayException("Dodaj najpierw oddział!");
            return lekID;
        }
        if (lekCombo.getValue() == null) {
            ExceptionHandler.displayException("Wybierz oddział");
            return lekID; //-1
        }
        String oddzialToFind = lekCombo.getValue().toString();
        for (Sz_leki lek : lekiList) {
            if (lek.getNazwaHandlowa().equals(oddzialToFind)) {
                lekID = Integer.valueOf(lek.getId());
                break;
            }
        }
        return lekID;
    }

    public void getStosowaneLeki(Sz_leczenia leczenie)
    {
     //   System.out.println(leczenie.getLeczenieid());
        List<Sz_stosowaneleki> leki = new QueriesManager().getStosowaneLeki(leczenie);
        if(leki.size()>0)
        {
            this.leki.getItems().clear();
            this.leki.getItems().addAll(leki);
        }
        else{
            this.leki.getItems().clear();
        }

    }
    @Override
    public void refresh() {
        getLeczenia();
    }

//    public void updateLeczenie() {
//        if(rozpoznanie.getText().trim().equals(""))
//        {
//            ExceptionHandler.showMessage("Wypełnij rozpoznanie!");
//        }
//        else {
//            Sz_leczenia leczenie = new Sz_leczenia(pacjent.getId(), leczenia.getSelectionModel().getSelectedItem().getLeczenieid(), null, rozpoznanie.getText());
//            new QueriesManager().updateLeczenia(leczenie);
//        }
//        refresh();
//    }
}

