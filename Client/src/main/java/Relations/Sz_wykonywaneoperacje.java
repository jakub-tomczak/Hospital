package Relations;

public class Sz_wykonywaneoperacje {
  private int lekarze_pracownikid;
  private int operacje_operacjaid;

  public Sz_wykonywaneoperacje(int pracownikID, int operacjaID)
  {
    this.lekarze_pracownikid = pracownikID;
    this.operacje_operacjaid = operacjaID;
  }

  public int getLekarze_pracownikid() {
    return lekarze_pracownikid;
  }

  public void setLekarze_pracownikid(int lekarze_pracownikid) {
    this.lekarze_pracownikid = lekarze_pracownikid;
  }

  public int getOperacje_operacjaid() {
    return operacje_operacjaid;
  }

  public void setOperacje_operacjaid(int operacje_operacjaid) {
    this.operacje_operacjaid = operacje_operacjaid;
  }
}
