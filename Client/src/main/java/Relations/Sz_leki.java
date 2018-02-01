package Relations;

public class Sz_leki {
  private String id;
  private String nazwaMiedzynarodowa;
  private String nazwaHandlowa;

  public Sz_leki(String id,String nazwaMiedzynarodowa,String nazwaHandlowa)
  {
    this.id=id;
    this.nazwaMiedzynarodowa=nazwaMiedzynarodowa;
    this.nazwaHandlowa = nazwaHandlowa;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }


  public String getNazwaMiedzynarodowa() {
    return nazwaMiedzynarodowa;
  }

  public void setNazwaMiedzynarodowa(String nazwaMiedzynarodowa) {
    this.nazwaMiedzynarodowa = nazwaMiedzynarodowa;
  }

  public String getNazwaHandlowa() {
    return nazwaHandlowa;
  }

  public void setNazwaHandlowa(String nazwaHandlowa) {
    this.nazwaHandlowa = nazwaHandlowa;
  }
}
