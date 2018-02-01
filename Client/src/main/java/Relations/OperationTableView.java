package Relations;

import java.time.LocalDateTime;

public class OperationTableView {
    private String nazwaoperacji;
    private String imiePacjenta;
    private String nazwiskoPacjenta;
    private String pesel;
    private int oddzial;
    private int operacjaID;
    private int pacjentID;
    private LocalDateTime data;
    private String oddzialName;

    public OperationTableView(int operacjaID, String imie, String nazwisko, String pesel,  LocalDateTime data, String nazwa, int oddzial, int pacjentID, String oddzialName)
    {
        this.operacjaID = operacjaID;
        this.imiePacjenta = imie;
        this.nazwiskoPacjenta = nazwisko;
        this.pesel = pesel;
        this.oddzial = oddzial;
        this.data = data;
        this.nazwaoperacji = nazwa;
        this.pacjentID = pacjentID;
        this.oddzialName = oddzialName;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }

    public int getOddzial() {
        return oddzial;
    }

    public void setOddzial(int oddzial) {
        this.oddzial = oddzial;
    }

    public String getPesel() {
        return pesel;
    }

    public void setPesel(String pesel) {
        this.pesel = pesel;
    }

    public String getNazwiskoPacjenta() {
        return nazwiskoPacjenta;
    }

    public void setNazwiskoPacjenta(String nazwiskoPacjenta) {
        this.nazwiskoPacjenta = nazwiskoPacjenta;
    }

    public String getImiePacjenta() {
        return imiePacjenta;
    }

    public void setImiePacjenta(String imiePacjenta) {
        this.imiePacjenta = imiePacjenta;
    }

    public String getNazwaoperacji() {
        return nazwaoperacji;
    }

    public void setNazwaoperacji(String nazwaoperacji) {
        this.nazwaoperacji = nazwaoperacji;
    }

    public int getOperacjaID() {
        return operacjaID;
    }

    public void setOperacjaID(int operacjaID) {
        this.operacjaID = operacjaID;
    }

    public int getPacjentID() {
        return pacjentID;
    }

    public void setPacjentID(int pacjentID) {
        this.pacjentID = pacjentID;
    }

    public String getOddzialName() {
        return oddzialName;
    }

    public void setOddzialName(String oddzialName) {
        this.oddzialName = oddzialName;
    }
}
