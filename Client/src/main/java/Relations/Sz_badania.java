package Relations;

public class Sz_badania {
  private String id;
  private String nazwa;
  private java.sql.Date datagodzinabadania;
  private String oddzialy_id;
  private String pacjenci_id;

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public String getNazwa() {
    return nazwa;
  }

  public void setNazwa(String nazwa) {
    this.nazwa = nazwa;
  }

  public java.sql.Date getDatagodzinabadania() {
    return datagodzinabadania;
  }

  public void setDatagodzinabadania(java.sql.Date datagodzinabadania) {
    this.datagodzinabadania = datagodzinabadania;
  }

  public String getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(String oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }

  public String getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(String pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }
}
