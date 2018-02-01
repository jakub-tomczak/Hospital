package Controllers;

import Main.Main;
import Relations.*;
import SQL.QueriesManager;
import Utils.ExceptionHandler;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class PokazPobytyModal {
    public ComboBox day;
    public ComboBox month;
    public ComboBox year;
    public ComboBox hour;
    public ComboBox minute;
    public ComboBox day2;
    public ComboBox month2;
    public ComboBox year2;
    public ComboBox hour2;
    public ComboBox minute2;
    public ComboBox wardBox;
    public Button updateButton;
    public Button deleteButton;
    private Sz_pacjenci patient;
    public TableView pobytyTableView;
    public TableColumn<Sz_pobyty,String> dateIN;
    public TableColumn<Sz_pobyty,String> dateOUT;
    public TableColumn ward;
    private List<Sz_oddzialy> wards;
    private List<Sz_pobyty> pobyty;
    private int pobytID;

    @FXML
    public void initialize()
    {
        fillDateComboBoxes();
        day.getSelectionModel().select(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
        month.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
        hour.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        minute.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
        year.getSelectionModel().select(Calendar.getInstance().get(Calendar.YEAR)-1900);
        day2.getSelectionModel().select(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
        month2.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
        hour2.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
        minute2.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
        year2.getSelectionModel().select(Calendar.getInstance().get(Calendar.YEAR)-1900);
        dateIN.setCellValueFactory(new PropertyValueFactory<Sz_pobyty, String>("dataprzyjecia"));
        dateOUT.setCellValueFactory(new PropertyValueFactory<Sz_pobyty, String>("datawypisania"));
        updateButton.disableProperty().bind(
                Bindings.not(pobytyTableView.getSelectionModel().selectedItemProperty().isNotNull())
                        .or((wardBox.getSelectionModel().selectedItemProperty().isNull())
                        ));
        deleteButton.setDisable(true);
        ward.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_pobyty, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_pobyty, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });

        pobytyTableView.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<Sz_pobyty>() {
            // Here's the key part. See how I specify that the
            // parameters are of type student. Now you can use the
            // instance methods from Student.
            @Override
            public void changed(ObservableValue<? extends Sz_pobyty> observable,Sz_pobyty oldValue, Sz_pobyty newValue){
                if(newValue!=null){
                    System.out.println(newValue.getOddzialy_id() + " "+ newValue.getDataprzyjecia()+" "+newValue.getDatawypisania());
                    if(newValue.getDatawypisania()==null)
                    {
                        day2.getSelectionModel().select(null);
                        month2.getSelectionModel().select(null);
                        year2.getSelectionModel().select(null);
                        hour2.getSelectionModel().select(null);;
                        minute2.getSelectionModel().select(null);
                        deleteButton.setDisable(true);
                        enableCombo(true);
                    }
                    else
                    {
                        enableCombo(false);
                        day2.getSelectionModel().select(Calendar.getInstance().get(Calendar.DAY_OF_MONTH)-1);
                        month2.getSelectionModel().select(Calendar.getInstance().get(Calendar.MONTH));
                        hour2.getSelectionModel().select(Calendar.getInstance().get(Calendar.HOUR_OF_DAY));
                        minute2.getSelectionModel().select(Calendar.getInstance().get(Calendar.MINUTE));
                        year2.getSelectionModel().select(Calendar.getInstance().get(Calendar.YEAR)-1900);
                        deleteButton.setDisable(false);

                    }
                    pobytID=newValue.getID();
                }
                //you can add any other value from Student class via getter(getAdr,getMail,...)

            }
        });

    }

    public void enableCombo(boolean p)
    {
        day2.setDisable(p);
        month2.setDisable(p);
        year2.setDisable(p);
        hour2.setDisable(p);
        minute2.setDisable(p);
    }


    public void setPatient(Sz_pacjenci patient)
    {
        this.patient=patient;
    }

    public void getOddzialy() {
        QueriesManager queriesManager = new QueriesManager();
        wards = queriesManager.getOddzialy();
        if (wards != null) {
            List<String> nazwyOddzialow = new ArrayList<>();
            for (Sz_oddzialy oddzial : wards) {
                nazwyOddzialow.add(oddzial.getNazwa());
            }
            wardBox.setItems(FXCollections.observableList(nazwyOddzialow));
        }
    }

    private int getOddzialIdFromComboBox() {

        int oddzialId = -1;
        //combobox with oddzialy validation
        if (wardBox.getItems().size() == 0) {
            ExceptionHandler.showMessage("Dodaj najpierw oddział!");
            return oddzialId;
        }
        if (wardBox.getValue() == null) {
            return oddzialId; //-1
        }
        String oddzialToFind = wardBox.getValue().toString();
        for (Sz_oddzialy oddzial : wards) {
            if (oddzial.getNazwa().equals(oddzialToFind)) {
                oddzialId = oddzial.getId();
                break;
            }
        }
        return oddzialId;
    }

    private String getOddzialIdFromComboBox(Sz_pobyty pobyt) {
        for (Sz_oddzialy oddzial : wards) {
            if (oddzial.getId() == pobyt.getOddzialy_id()) {
                return oddzial.getNazwa();
            }
        }
        return "";
    }

    @FXML
    public void getPobyty()
    {
        QueriesManager queriesManager = new QueriesManager();
        wards = (new QueriesManager()).getOddzialy();
         pobyty = queriesManager.getPobytyPacjenta(patient);
        if(pobyty != null)
        {
            pobytyTableView.getItems().clear();
            pobytyTableView.getItems().addAll(pobyty);
        }
    }
    
    public void updatePobyty() throws ParseException {
        if(day2.isDisabled()==false)
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            if(sdf.parse(formattedDate2()).after(sdf.parse(formattedDate())))
            {
                Sz_pobyty pobyt = new Sz_pobyty(pobytID,patient.getId(),getOddzialIdFromComboBox(),formattedDate(),formattedDate2());
                QueriesManager queriesManager = new QueriesManager();
                    queriesManager.updatePobyt(pobyt);
                Main.getInstance().refreshAll();

            }
            else
            {
                ExceptionHandler.showMessage("Zła kolejność dat!");
            }
        }
        else
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
            Sz_pobyty pobyt = new Sz_pobyty(pobytID,patient.getId(),getOddzialIdFromComboBox(),formattedDate(),null);
            QueriesManager queriesManager = new QueriesManager();
            queriesManager.updatePobyt(pobyt);
            Main.getInstance().refreshAll();
        }
        getPobyty();
    }

    public void deletePobyty()
    {
        Sz_pobyty pobyt = new Sz_pobyty(pobytID,patient.getId(),getOddzialIdFromComboBox(),formattedDate(),null);
        QueriesManager queriesManager = new QueriesManager();
        queriesManager.deletePobyt(pobyt);
        Main.getInstance().refreshAll();

        getPobyty();
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

    public String formattedDate2()
    {
        String date="";
        date+=year2.getSelectionModel().getSelectedItem().toString();
        date+='/'+month2.getSelectionModel().getSelectedItem().toString();
        date+='/'+day2.getSelectionModel().getSelectedItem().toString()+' ';
        date+=hour2.getSelectionModel().getSelectedItem().toString()+':';
        date+=minute2.getSelectionModel().getSelectedItem().toString()+":00";
        return  date;
    }

    public void fillDateComboBoxes()
    {
        for(int i=1;i<32;i++)
        {
            day.getItems().add(i);
            day2.getItems().add(i);
        }
        for(int i=1;i<13;i++)
        {
            month.getItems().add(i);
            month2.getItems().add(i);
        }
        for(int i=0;i<24;i++)
        {
            hour.getItems().add(i);
            hour2.getItems().add(i);
        }
        for(int i=0;i<60;i++)
        {
            minute.getItems().add(i);
            minute2.getItems().add(i);
        }
        for(int i=1900;i<2050;i++)
        {
            year.getItems().add(i);
            year2.getItems().add(i);
        }
    }
}
