package Relations;

public class Sz_leczenia {
  private java.sql.Date datarozpoczecia;
  private java.sql.Date datazakonczenia;
  private String dawka;
  private String pacjenci_id;
  private String choroby_id;
  private String leczenieid;

  public java.sql.Date getDatarozpoczecia() {
    return datarozpoczecia;
  }

  public void setDatarozpoczecia(java.sql.Date datarozpoczecia) {
    this.datarozpoczecia = datarozpoczecia;
  }

  public java.sql.Date getDatazakonczenia() {
    return datazakonczenia;
  }

  public void setDatazakonczenia(java.sql.Date datazakonczenia) {
    this.datazakonczenia = datazakonczenia;
  }

  public String getDawka() {
    return dawka;
  }

  public void setDawka(String dawka) {
    this.dawka = dawka;
  }

  public String getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(String pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }

  public String getChoroby_id() {
    return choroby_id;
  }

  public void setChoroby_id(String choroby_id) {
    this.choroby_id = choroby_id;
  }

  public String getLeczenieid() {
    return leczenieid;
  }

  public void setLeczenieid(String leczenieid) {
    this.leczenieid = leczenieid;
  }
}
