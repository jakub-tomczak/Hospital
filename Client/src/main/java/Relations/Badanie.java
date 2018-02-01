package Relations;

public class Badanie {
    private String id;
    private String nazwa;
    private String pesel;
    private String data;
    private String oddzial;
    private String lekarz;
    private String pacjentID;

    public Badanie(String id, String nazwa, String pesel, String data, String oddzial, String lekarz,String pacjentID) {
        this.id = id;
        this.nazwa = nazwa;
        this.pesel = pesel;
        this.data = data;
        this.oddzial = oddzial;
        this.lekarz = lekarz;
        this.pacjentID=pacjentID;
    }

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getOddzial() {
        return oddzial;
    }

    public void setOddzial(String oddzial) {
        this.oddzial = oddzial;
    }

    public String getLekarz() {
        return lekarz;
    }

    public void setLekarz(String lekarz) {
        this.lekarz = lekarz;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPacjentID() {
        return pacjentID;
    }

    public void setPacjentID(String pacjentID) {
        this.pacjentID = pacjentID;
    }
}
