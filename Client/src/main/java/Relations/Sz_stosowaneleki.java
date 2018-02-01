package Relations;

public class Sz_stosowaneleki {
  private int id;
  private String numerSerii;
  private int lekID;
  private int leczenieID;
  private String dawkal;
  private String data;


  public Sz_stosowaneleki(int id, String numerSerii, int lekID, int leczenieID, String dawkal,String data) {
    this.id = id;
    this.numerSerii = numerSerii;
    this.lekID = lekID;
    this.leczenieID = leczenieID;
    this.dawkal = dawkal;
    this.data=data;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNumerSerii() {
    return numerSerii;
  }

  public void setNumerSerii(String numerSerii) {
    this.numerSerii = numerSerii;
  }

  public int getLekID() {
    return lekID;
  }

  public void setLekID(int lekID) {
    this.lekID = lekID;
  }

  public int getLeczenieID() {
    return leczenieID;
  }

  public void setLeczenieID(int leczenieID) {
    this.leczenieID = leczenieID;
  }

  public String getDawkal() {
    return dawkal;
  }

  public void setDawkal(String dawkal) {
    this.dawkal = dawkal;
  }

  public String getData() {
    return data;
  }

  public void setData(String data) {
    this.data = data;
  }
}
