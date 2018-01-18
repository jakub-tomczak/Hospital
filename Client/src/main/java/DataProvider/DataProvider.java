package DataProvider;

import Relations.Sz_lekarze;
import Relations.Sz_pielegniarki;

import java.util.ArrayList;
import java.util.List;

public class DataProvider {
    private static DataProvider instance;

    public static DataProvider getInstance() {
        if(instance == null)
        {
            instance =  new DataProvider();
        }
        return instance;
    }

    public DataProvider()
    {
        lekarze = new ArrayList<>();
        pielegniarki = new ArrayList<>();
    }

    private List<Sz_lekarze> lekarze;
    private List<Sz_pielegniarki> pielegniarki;

    public List<Sz_lekarze> getLekarze() {
        return lekarze;
    }

    public void updateLekarze(List<Sz_lekarze> lekarze) {
        this.lekarze = lekarze;
    }

    public List<Sz_pielegniarki> getPielegniarki() {
        return pielegniarki;
    }

    public void updatePielegniarki(List<Sz_pielegniarki> pielegniarki) {
        this.pielegniarki = pielegniarki;
    }
}
