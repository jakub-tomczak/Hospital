package Relations;

public class Sz_pielegniarki extends Sz_pracownicy {
  private int pracownikid;

  public Sz_pielegniarki()
  {
    setStanowisko(2);
  }
  public int getPracownikid() {
    return pracownikid;
  }

  public void setPracownikid(int pracownikid) {
    this.pracownikid = pracownikid;
  }
}
