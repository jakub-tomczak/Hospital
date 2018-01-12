package Relations;

public class Sz_operacje {
  private String operacjaid;
  private String rodzajoperacji;
  private java.sql.Date datagodzinarozpoczecia;
  private String pacjenci_id;
  private String oddzialy_id;

  public String getOperacjaid() {
    return operacjaid;
  }

  public void setOperacjaid(String operacjaid) {
    this.operacjaid = operacjaid;
  }

  public String getRodzajoperacji() {
    return rodzajoperacji;
  }

  public void setRodzajoperacji(String rodzajoperacji) {
    this.rodzajoperacji = rodzajoperacji;
  }

  public java.sql.Date getDatagodzinarozpoczecia() {
    return datagodzinarozpoczecia;
  }

  public void setDatagodzinarozpoczecia(java.sql.Date datagodzinarozpoczecia) {
    this.datagodzinarozpoczecia = datagodzinarozpoczecia;
  }

  public String getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(String pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }

  public String getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(String oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }
}
