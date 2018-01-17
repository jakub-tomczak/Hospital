package Relations;

public class Sz_operacje {
  private String operacjaid;
  private String rodzajoperacji;
  private String datagodzinarozpoczecia;
  private int pacjenci_id;
  private int oddzialy_id;

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

  public String getDatagodzinarozpoczecia() {
    return datagodzinarozpoczecia;
  }

  public void setDatagodzinarozpoczecia(String datagodzinarozpoczecia) {
    this.datagodzinarozpoczecia = datagodzinarozpoczecia;
  }

  public int getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(int pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }

  public int getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(int oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }
}
