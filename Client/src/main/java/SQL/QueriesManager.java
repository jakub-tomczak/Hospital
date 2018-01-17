package SQL;

import Controllers.Operacje;
import Relations.Sz_lekarze;
import Relations.Sz_operacje;

import java.sql.CallableStatement;
import java.sql.Date;
import java.sql.SQLException;
import java.util.concurrent.*;

public class QueriesManager {
    private int maxPoolSize = 5;   //max number of threads
    private ExecutorService executor = null;

    public QueriesManager()
    {
        executor =  Executors.newFixedThreadPool(maxPoolSize);
    }

    public void addDoctor(Sz_lekarze lekarz, Command onQuryFinished) throws SQLException, ClassNotFoundException {
        //jednowatkowa wersja
        String query = "{call dodajLekarza(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = Connector.getInstance().getConnection().prepareCall(query);
        statement.setString(1, lekarz.getImie());
        statement.setString(2, lekarz.getNazwisko());
        statement.setInt(3, 1); //stanowisko, 1 = lekarz, 2 = pielegniarka
        statement.setDouble(4, lekarz.getPensja());
        statement.setString(5, lekarz.getSpecjalizacja());
        statement.setString(6, lekarz.getStopiennaukowy());
        statement.setInt(7, lekarz.getOddzialy_id());
        statement.execute();
        statement.close();
        System.out.println("Dodano lekarza");

        //wielowatkowa wersja
        /*
        executor.submit(() -> {
            try {
                TimeUnit.SECONDS.sleep(5);
                onQuryFinished.execute();
            }
            catch (InterruptedException e) {
                throw new IllegalStateException("task interrupted", e);
            }
        });

        System.out.println("Submitted.");
        */
    }

    public void adddOperation(Sz_operacje operacja, int lekarzID) throws SQLException {
        String query = "{call dodajOperacje(?, ?, ?, ?, ?)}";
        CallableStatement statement = Connector.getInstance().getConnection().prepareCall(query);
        statement.setString(1, operacja.getRodzajoperacji());
        statement.setString(2, operacja.getDatagodzinarozpoczecia());
        statement.setInt(3, operacja.getPacjenci_id());
        statement.setInt(4, operacja.getOddzialy_id());
        statement.setInt(5, lekarzID);
        statement.execute();
        statement.close();
        System.out.println("Dodano operacje");
    }

}
