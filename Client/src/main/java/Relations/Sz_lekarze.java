package Relations;

public class Sz_lekarze extends Sz_pracownicy {
  private String specjalizacja;
  private String stopiennaukowy;
  private int pracownikid;
  public Sz_lekarze()
  {
    setStanowisko(1);
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
    return pracownikid;
  }

  public void setPracownikid(int pracownikid) {
    this.pracownikid = pracownikid;
  }
}
