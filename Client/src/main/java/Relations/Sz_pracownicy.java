package Relations;

import javafx.beans.property.*;

public class Sz_pracownicy {
  private StringProperty imie;
  private StringProperty nazwisko;
  private StringProperty stanowiskoString;
  private int stanowisko;
  private DoubleProperty pensja;
  private IntegerProperty oddzialy_id;
  private IntegerProperty pracownikid;

  public Sz_pracownicy()
  {
    imie = new SimpleStringProperty();
    nazwisko = new SimpleStringProperty();
    stanowiskoString = new SimpleStringProperty();
    pensja = new SimpleDoubleProperty();
    oddzialy_id = new SimpleIntegerProperty();
    pracownikid = new SimpleIntegerProperty();
  }
  public int getStanowisko() {
    return stanowisko;
  }

  public void setStanowisko(int stanowisko) {
    this.stanowisko = stanowisko;
  }

  public String getImie() {
    return imie.get();
  }

  public StringProperty imieProperty() {
    return imie;
  }

  public void setImie(String imie) {
    this.imie.setValue(imie);
  }

  public String getNazwisko() {
    return nazwisko.get();
  }

  public StringProperty nazwiskoProperty() {
    return nazwisko;
  }

  public void setNazwisko(String nazwisko) {
    this.nazwisko.set(nazwisko);
  }

  public String getStanowiskoString() {
    return stanowiskoString.get();
  }

  public StringProperty stanowiskoStringProperty() {
    if (this.stanowisko == 1) {
      this.stanowiskoString.setValue("Lekarz");
    } else if (this.stanowisko == 2) {
      this.stanowiskoString.setValue("Pielęgniarka");
    } else {
      this.stanowiskoString.setValue("Inne");
    }
    return stanowiskoString;
  }


  public double getPensja() {
    return pensja.get();
  }

  public DoubleProperty pensjaProperty() {
    return pensja;
  }

  public void setPensja(double pensja) {
    this.pensja.set(pensja);
  }

  public int getOddzialy_id() {
    return oddzialy_id.get();
  }

  public IntegerProperty oddzialy_idProperty() {
    return oddzialy_id;
  }

  public void setOddzialy_id(int oddzialy_id) {
    this.oddzialy_id.set(oddzialy_id);
  }

  public int getPracownikid() {
    return pracownikid.get();
  }

  public IntegerProperty pracownikidProperty() {
    return pracownikid;
  }

  public void setPracownikid(int pracownikid) {
    this.pracownikid.set(pracownikid);
  }
}
