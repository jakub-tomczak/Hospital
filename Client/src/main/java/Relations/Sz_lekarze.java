package Relations;

import Utils.Constants;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sz_lekarze extends Sz_pracownicy {
  private StringProperty specjalizacja;
  private StringProperty stopiennaukowy;
  public Sz_lekarze()
  {
    setStanowisko(Constants.DOCTOR);
    specjalizacja = new SimpleStringProperty();
    stopiennaukowy = new SimpleStringProperty();
  }
  public Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
  {
    this();
    this.setImie(imie);
    this.setNazwisko(nazwisko);
    this.setPensja(pensja);
    this.setOddzialy_id(oddzialID);
    this.setSpecjalizacja(specjalizacja);
    this.setStopiennaukowy(stopiennaukowy);
    super.setPracownikid(ID);
  }

  public String getSpecjalizacja() {
    return specjalizacja.get();
  }

  public StringProperty specjalizacjaProperty() {
    return specjalizacja;
  }

  public void setSpecjalizacja(String specjalizacja) {
    this.specjalizacja.set(specjalizacja);
  }

  public String getStopiennaukowy() {
    return stopiennaukowy.get();
  }

  public StringProperty stopiennaukowyProperty() {
    return stopiennaukowy;
  }

  public void setStopiennaukowy(String stopiennaukowy) {
    this.stopiennaukowy.set(stopiennaukowy);
  }
}
