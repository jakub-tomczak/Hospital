package Relations;

public class Sz_leczenia {
  private int pacjenci_id;
  private int leczenieid;
  private String datarozpoznania;
  private String rozpoznanie;

  public Sz_leczenia(int pacjenci_id, int leczenieid, String datarozpoznania, String rozpoznanie) {
    this.pacjenci_id = pacjenci_id;
    this.leczenieid = leczenieid;
    this.datarozpoznania = datarozpoznania;
    this.rozpoznanie = rozpoznanie;
  }

  public String getRozpoznanie() {
    return rozpoznanie;
  }

  public void setRozpoznanie(String rozpoznanie) {
    this.rozpoznanie = rozpoznanie;
  }

  public String getDatarozpoznania() {
    return datarozpoznania;
  }

  public void setDatarozpoznania(String datarozpoznania) {
    this.datarozpoznania = datarozpoznania;
  }

  public int getLeczenieid() {
    return leczenieid;
  }

  public void setLeczenieid(int leczenieid) {
    this.leczenieid = leczenieid;
  }

  public int getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(int pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }
}

