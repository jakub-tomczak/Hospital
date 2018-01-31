package Relations;

import Utils.Constants;

public class Sz_pielegniarki extends Sz_pracownicy {

  public Sz_pielegniarki()
  {
    setStanowisko(Constants.NURSE);
  }

  public Sz_pielegniarki(String imie, String nazwisko, double pensja,  int oddzialID, int ID)
  {
    this();
    this.setImie(imie);
    this.setNazwisko(nazwisko);
    this.setPensja(pensja);
    this.setOddzialy_id(oddzialID);
    this.setPracownikid(ID);
  }
}
