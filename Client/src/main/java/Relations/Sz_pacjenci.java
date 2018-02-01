package Relations;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class Sz_pacjenci {
  private int id;
  private String imie;
  private String nazwisko;
  private String pesel;
  private String adres;
  private String miasto;
  private String kod;
  private static AtomicLong idCounter = new AtomicLong();
  public Sz_pacjenci()
  {

  }

  public  Sz_pacjenci(String imie,String nazwisko,String pesel,String adres,String miasto,String kod,int id)
  {
    this();
    this.imie = imie;
    this.nazwisko = nazwisko;
    this.pesel = pesel;
    this.adres = adres;
    this.miasto = miasto;
    this.kod = kod;
    this.id = id;

  }


  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getImie() {
    return imie;
  }

  public void setImie(String imie) {
    this.imie = imie;
  }

  public String getNazwisko() {
    return nazwisko;
  }

  public void setNazwisko(String nazwisko) {
    this.nazwisko = nazwisko;
  }

  public String getPesel() {
    return pesel;
  }

  public void setPesel(String pesel) {
    this.pesel = pesel;
  }


  public String getAdres() {
    return adres;
  }

  public void setAdres(String adres) {
    this.adres = adres;
  }

  public String getMiasto() {
    return miasto;
  }

  public void setMiasto(String miasto) {
    this.miasto = miasto;
  }

  public String getKod() {
    return kod;
  }

  public void setKod(String kod) {
    this.kod = kod;
  }

}
