package Relations;

public class Sz_pracownicy {
  private String imie;
  private String nazwisko;
  private int stanowisko;
  private double pensja;
  private int oddzialy_id;
  private int pracownikid;

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

  public int getStanowisko() {
    return stanowisko;
  }

  public void setStanowisko(int stanowisko) {
    this.stanowisko = stanowisko;
  }

  public double getPensja() {
    return pensja;
  }

  public void setPensja(double pensja) {
    this.pensja = pensja;
  }

  public int getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(int oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }

  public int getPracownikid() {
    return pracownikid;
  }

  public void setPracownikid(int pracownikid) {
    this.pracownikid = pracownikid;
  }
}
