package main.Relations;

public class Sz_pobyty {
  private String pacjenci_id;
  private java.sql.Date dataprzyjecia;
  private java.sql.Date datawypisania;
  private String oddzialy_id;

  public String getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(String pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }

  public java.sql.Date getDataprzyjecia() {
    return dataprzyjecia;
  }

  public void setDataprzyjecia(java.sql.Date dataprzyjecia) {
    this.dataprzyjecia = dataprzyjecia;
  }

  public java.sql.Date getDatawypisania() {
    return datawypisania;
  }

  public void setDatawypisania(java.sql.Date datawypisania) {
    this.datawypisania = datawypisania;
  }

  public String getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(String oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }
}
