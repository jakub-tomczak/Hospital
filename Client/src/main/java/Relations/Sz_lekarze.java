package Relations;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sz_lekarze extends Sz_pracownicy {
  private StringProperty specjalizacja;
  private StringProperty stopiennaukowy;
  private IntegerProperty pracownikid;
  public Sz_lekarze()
  {
    setStanowisko(1);
    specjalizacja = new SimpleStringProperty();
    stopiennaukowy = new SimpleStringProperty();
    pracownikid = new SimpleIntegerProperty();
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
    this.pracownikid.setValue(ID);
  }

  public int getPracownikid() {
    return super.getPracownikid();
  }

  public void setPracownikid(int pracownikid) {
    super.setPracownikid(pracownikid) ;
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
