package Controllers;

import Main.Main;
import Relations.Sz_leki;
import Relations.Sz_oddzialy;
import Relations.Sz_pracownicy;
import SQL.QueriesManager;
import Utils.Constants;
import Utils.IDisplayedScreen;
import Utils.Validator;
import com.github.kaiwinter.jfx.tablecolumn.filter.FilterSupport;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.paint.Paint;
import javafx.util.Callback;

import javax.script.Bindings;
import java.util.ArrayList;
import java.util.List;

public class ZarzadzajLekami implements IDisplayedScreen{
    private final String insertMode = "Tryb dodawania";
    private final String updateMode = "Tryb aktualizacji";
    private final String insertModeAcceptButtonText = "Dodaj";
    private final String updateModeAcceptButtonText = "Aktualizuj";
    public Button formAcceptButton;
    public Label formModeLabel;
    public TableColumn removeLek;
    public TableColumn updateLek;
    private Pracownicy.FormMode formMode = Pracownicy.FormMode.insert;
    public TextField nazwaMiedzynarodowaT;
    public TextField nazwaHandlowaT;
    public TableView<Sz_leki> lekiTableView;
    public TableColumn nazwaMiedzynarodowa;
    public TableColumn ID;
    public TableColumn nazwaHandlowa;
    private Sz_leki lekToUpdate;
    @FXML
    public void initialize() {
       // dodaje siebie do listy ekranów odświeżalnych
        Main.screensList.add(this);
        ID.setCellValueFactory(new PropertyValueFactory<Sz_leki, String>("id"));
        nazwaMiedzynarodowa.setCellValueFactory(new PropertyValueFactory<Sz_leki, String>("nazwaMiedzynarodowa"));
        nazwaHandlowa.setCellValueFactory(new PropertyValueFactory<Sz_leki, String>("nazwaHandlowa"));
        formAcceptButton.disableProperty().bind(
                javafx.beans.binding.Bindings.isEmpty(nazwaMiedzynarodowaT.textProperty())
                        .or( javafx.beans.binding.Bindings.isEmpty(nazwaHandlowaT.textProperty())
                        ));
        removeLek.setSortable(false);
        removeLek.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_leki, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_leki, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        removeLek.setCellFactory(
                new Callback<TableColumn<Sz_leki, Boolean>, TableCell<Sz_leki, Boolean>>() {

                    @Override
                    public TableCell<Sz_leki, Boolean> call(TableColumn<Sz_leki, Boolean> p) {
                        return new ZarzadzajLekami.ButtonFactory("Usuń", Pracownicy.ButtonAction.delete);
                    }
                });

        //przycisk do aktualizacji
        updateLek.setSortable(false);
        updateLek.setCellValueFactory(
                new Callback<TableColumn.CellDataFeatures<Sz_leki, Boolean>,
                        ObservableValue<Boolean>>() {

                    @Override
                    public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Sz_leki, Boolean> p) {
                        return new SimpleBooleanProperty(p.getValue() != null);
                    }
                });

        updateLek.setCellFactory(
                new Callback<TableColumn<Sz_leki, Boolean>, TableCell<Sz_leki, Boolean>>() {

                    @Override
                    public TableCell<Sz_leki, Boolean> call(TableColumn<Sz_leki, Boolean> p) {
                        return new ZarzadzajLekami.ButtonFactory("Modyfikuj", Pracownicy.ButtonAction.update);
                    }
                });
        FilterSupport.addFilter(nazwaMiedzynarodowa);
        FilterSupport.addFilter(nazwaHandlowa);
        refresh();
    }

    @Override
    public void refresh() {
        getLeki();
        clearFields(null);
    }

    private void getLeki() {
        QueriesManager queriesManager = new QueriesManager();
        List<Sz_leki> leki = queriesManager.getLeki();
        if(leki != null)
        {
            lekiTableView.getItems().clear();
            lekiTableView.getItems().setAll(leki);
        }

    }


    public void performAction(ActionEvent actionEvent) {
        if(!validateData())
        {
            return;
        }
        QueriesManager queriesManager = new QueriesManager();
        switch(formMode)
        {
            case update:
                lekToUpdate.setNazwaHandlowa(nazwaHandlowaT.getText());
                lekToUpdate.setNazwaMiedzynarodowa(nazwaMiedzynarodowaT.getText());
                queriesManager.updateLeki(lekToUpdate);
                break;
            case insert:
                Sz_leki lekiToAdd = new Sz_leki("0",nazwaMiedzynarodowaT.getText(),nazwaHandlowaT.getText());

               queriesManager.insertLeki(lekiToAdd);

                break;
        }
        Main.getInstance().refreshAll();
    }

    public void clearFields(ActionEvent actionEvent) {
        nazwaMiedzynarodowaT.setText("");
        nazwaHandlowaT.setText("");

        //reset button and text
        formAcceptButton.setText(insertModeAcceptButtonText);
        formModeLabel.setText(insertMode);
        formModeLabel.setTextFill(Paint.valueOf("#15ff00"));    //green
        formMode = Pracownicy.FormMode.insert;

        clearValidationMarks();

        lekToUpdate=null;


    }      //reset fields


    private void clearValidationMarks() {
        nazwaMiedzynarodowaT.setStyle("-fx-background-color: #ffffff");
        nazwaHandlowaT.setStyle("-fx-background-color: #ffffff");
    }

    private boolean validateData()
    {
        boolean result = true;
        result &= Validator.validateStringField(nazwaHandlowaT);
        result &= Validator.validateStringField(nazwaMiedzynarodowaT);
        return result;
    }

    public void setLekToUpdate(Sz_leki lekToUpdate) {
        //wyczysc pola
        clearFields(null);

        //set form
        formMode = Pracownicy.FormMode.update;

        this.lekToUpdate = lekToUpdate;

        //set button and label text
        formAcceptButton.setText(updateModeAcceptButtonText);
        formModeLabel.setText(updateMode);
        formModeLabel.setTextFill(Paint.valueOf("#FF8B1E"));    //orange


        nazwaMiedzynarodowaT.setText(lekToUpdate.getNazwaMiedzynarodowa());
        nazwaHandlowaT.setText(lekToUpdate.getNazwaHandlowa());
    }


    private class ButtonFactory extends TableCell<Sz_leki, Boolean> {

        Button cellButton = null;

        public ButtonFactory(String buttonName, Pracownicy.ButtonAction action) {
            cellButton = new Button(buttonName);
            cellButton.setOnAction(new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent t) {
                    int index = getTableRow().getIndex();
                    Sz_leki lek =  lekiTableView.getItems().get(index);
                    switch (action) {
                        case update:
                            setLekToUpdate(lek);
                            break;
                        case delete:
                            QueriesManager queriesManager = new QueriesManager();
                            queriesManager.deleteLeki(lek);
                            break;
                    }
                  refresh();
                }
            });
        }

        @Override
        protected void updateItem(Boolean t, boolean empty) {
            super.updateItem(t, empty);
            if (!empty) {
                setGraphic(cellButton);
            }
        }
    }

}


