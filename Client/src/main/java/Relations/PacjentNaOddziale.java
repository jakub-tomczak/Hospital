package Relations;

public class PacjentNaOddziale {
    private int id;
    private int pobytID;
    private int oddzialID;
    private String imie;
    private String nazwisko;
    private String pesel;
    private String oddzial;
    private String dataWpisania;

    public PacjentNaOddziale(int id, int pobytID,int oddzialID, String imie, String nazwisko, String pesel, String oddzial, String dataWpisania) {
        this.id = id;
        this.pobytID = pobytID;
        this.imie = imie;
        this.nazwisko = nazwisko;
        this.pesel = pesel;
        this.oddzial = oddzial;
        this.dataWpisania = dataWpisania;
        this.oddzialID=oddzialID;
    }

    public String getDataWpisania() {
        return dataWpisania;
    }

    public void setDataWpisania(String dataWpisania) {
        this.dataWpisania = dataWpisania;
    }

    public String getOddzial() {
        return oddzial;
    }

    public void setOddzial(String oddzial) {
        this.oddzial = oddzial;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }

    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    public int getPobytID() {
        return pobytID;
    }

    public void setPobytID(int pobytID) {
        this.pobytID = pobytID;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOddzialID() {
        return oddzialID;
    }

    public void setOddzialID(int oddzialID) {
        this.oddzialID = oddzialID;
    }
}
