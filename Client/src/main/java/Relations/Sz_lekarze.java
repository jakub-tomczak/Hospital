package Relations;

public class Sz_lekarze extends Sz_pracownicy {
  private String specjalizacja;
  private String stopiennaukowy;
  private int pracownikid;
  public Sz_lekarze()
  {
    setStanowisko(1);
  }
  public Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
  {
    this();
    this.setImie(imie);
    this.setNazwisko(nazwisko);
    this.setPensja(pensja);
    this.setOddzialy_id(oddzialID);
    this.specjalizacja = specjalizacja;
    this.stopiennaukowy = stopiennaukowy;
    this.pracownikid = pracownikid;
  }
  public String getSpecjalizacja() {
    return specjalizacja;
  }

  public void setSpecjalizacja(String specjalizacja) {
    this.specjalizacja = specjalizacja;
  }

  public String getStopiennaukowy() {
    return stopiennaukowy;
  }

  public void setStopiennaukowy(String stopiennaukowy) {
    this.stopiennaukowy = stopiennaukowy;
  }

  public int getPracownikid() {
    return super.getPracownikid();
  }

  public void setPracownikid(int pracownikid) {
    super.setPracownikid(pracownikid) ;
  }
}
