package Relations;

public class Sz_badania {
  private String id;
  private String nazwa;
  private String datagodzinabadania;
  private int oddzialy_id;
  private int pacjenci_id;

  public Sz_badania(String nazwa, String datagodzinabadania, int oddzialy_id, int pacjenci_id)
  {
    this.nazwa=nazwa;
    this.datagodzinabadania = datagodzinabadania;
    this.oddzialy_id=oddzialy_id;
    this.pacjenci_id =pacjenci_id;
  }


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

  public String getDatagodzinabadania() {
    return datagodzinabadania;
  }

  public void setDatagodzinabadania(String datagodzinabadania) {
    this.datagodzinabadania = datagodzinabadania;
  }

  public int getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(int oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }

  public int getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(int pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }
}
