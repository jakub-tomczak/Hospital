package Relations;

public class Sz_pobyty {
  private int pacjenci_id;
  private String dataprzyjecia;
  private String datawypisania;
  private int oddzialy_id;
  private  int ID;


  public  Sz_pobyty(int ID,int pacjenci_id,int oddzialy_id,String dataprzyjecia,String  datawypisania ) {
    this.ID = ID;
    this.pacjenci_id = pacjenci_id;
    this.oddzialy_id = oddzialy_id;
    this.dataprzyjecia = dataprzyjecia;
    this.datawypisania = datawypisania;
  }

  public int getPacjenci_id() {
    return pacjenci_id;
  }

  public void setPacjenci_id(int pacjenci_id) {
    this.pacjenci_id = pacjenci_id;
  }

  public String getDataprzyjecia() {
    return dataprzyjecia;
  }

  public void setDataprzyjecia(String dataprzyjecia) {
    this.dataprzyjecia = dataprzyjecia;
  }

  public String getDatawypisania() {
    return datawypisania;
  }

  public void setDatawypisania(String datawypisania) {
    this.datawypisania = datawypisania;
  }

  public int getOddzialy_id() {
    return oddzialy_id;
  }

  public void setOddzialy_id(int oddzialy_id) {
    this.oddzialy_id = oddzialy_id;
  }

  public int getID() {
    return ID;
  }

  public void setID(int ID) {
    this.ID = ID;
  }
}
