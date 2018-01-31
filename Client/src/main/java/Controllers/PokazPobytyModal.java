package Controllers;

import Relations.Sz_badania;
import Relations.Sz_oddzialy;
import Relations.Sz_pacjenci;
import Relations.Sz_pobyty;
import SQL.QueriesManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

import java.util.List;

public class PokazPobytyModal {
    private Sz_pacjenci patient;
    public TableView pobytyTableView;
    public TableColumn<Sz_pobyty,String> dateIN;
    public TableColumn<Sz_pobyty,String> dateOUT;
    public TableColumn ward;
    private List<Sz_oddzialy> wards;
    private List<Sz_pobyty> pobyty;

    @FXML
    public void initialize()
    {
        dateIN.setCellValueFactory(new PropertyValueFactory<Sz_pobyty, String>("dataprzyjecia"));
        dateOUT.setCellValueFactory(new PropertyValueFactory<Sz_pobyty, String>("datawypisania"));
        ward.setCellValueFactory(new Callback<TableColumn.CellDataFeatures<Sz_pobyty, String>, ObservableValue<String>>() {
            public ObservableValue<String> call(TableColumn.CellDataFeatures<Sz_pobyty, String> p) {
                return new ReadOnlyStringWrapper(getOddzialIdFromComboBox(p.getValue()));
            }
        });
    }

    public void setPatient(Sz_pacjenci patient)
    {
        this.patient=patient;
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

}
