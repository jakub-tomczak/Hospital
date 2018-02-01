package Relations;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class Sz_operacje {
  private int operacjaid;
  private String rodzajoperacji;
  private LocalDateTime datagodzinarozpoczecia;
  private int pacjenci_id;
  private int oddzialy_id;

  public int getOperacjaid() {
    return operacjaid;
  }

  public void setOperacjaid(int operacjaid) {
    this.operacjaid = operacjaid;
  }

  public String getRodzajoperacji() {
    return rodzajoperacji;
  }

  public void setRodzajoperacji(String rodzajoperacji) {
    this.rodzajoperacji = rodzajoperacji;
  }

  public LocalDateTime getDatagodzinarozpoczecia() {
    return datagodzinarozpoczecia;
  }

  public void setDatagodzinarozpoczecia(LocalDateTime datagodzinarozpoczecia) {
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
