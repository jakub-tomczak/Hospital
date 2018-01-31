package Relations;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class Sz_oddzialy {
  private IntegerProperty id;
  private IntegerProperty kierownikkliniki;
  private StringProperty nazwa;
  private IntegerProperty maksymalnaliczbapacjentow;

  public Sz_oddzialy()
  {
    this.id = new SimpleIntegerProperty();
    this.kierownikkliniki = new SimpleIntegerProperty();
    this.nazwa = new SimpleStringProperty();
    this.maksymalnaliczbapacjentow = new SimpleIntegerProperty();

  }

  public Sz_oddzialy(int id, int kierownikkliniki, String nazwa, int maksymalnaliczbapacjentow) {
    this();
    this.setId(id);
    this.setKierownikkliniki(kierownikkliniki);
    this.setNazwa(nazwa);
    this.setMaksymalnaliczbapacjentow(maksymalnaliczbapacjentow);
  }

  public int getId() {
    return id.get();
  }

  public IntegerProperty idProperty() {
    return id;
  }

  public void setId(int id) {
    this.id.set(id);
  }

  public int getKierownikkliniki() {
    return kierownikkliniki.get();
  }

  public IntegerProperty kierownikklinikiProperty() {
    return kierownikkliniki;
  }

  public void setKierownikkliniki(int kierownikkliniki) {
    this.kierownikkliniki.set(kierownikkliniki);
  }

  public String getNazwa() {
    return nazwa.get();
  }

  public StringProperty nazwaProperty() {
    return nazwa;
  }

  public void setNazwa(String nazwa) {
    this.nazwa.set(nazwa);
  }

  public int getMaksymalnaliczbapacjentow() {
    return maksymalnaliczbapacjentow.get();
  }

  public IntegerProperty maksymalnaliczbapacjentowProperty() {
    return maksymalnaliczbapacjentow;
  }

  public void setMaksymalnaliczbapacjentow(int maksymalnaliczbapacjentow) {
    this.maksymalnaliczbapacjentow.set(maksymalnaliczbapacjentow);
  }
}
