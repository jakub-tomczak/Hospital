package SQL;

import Relations.Sz_lekarze;
import Relations.Sz_operacje;
import Relations.Sz_pacjenci;
import Utils.ExceptionHandler;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class QueriesManager {
    private int maxPoolSize = 5;   //max number of threads
    private ExecutorService executor = null;

    /*

            Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(
                    query
            );

            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_lekarze lekarz = new Sz_lekarze(
                        rs.getString(1), rs.getString(2), rs.getDouble(3),
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));

                lekarze.add(lekarz);
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z lekarzami");
        }finally {
            try
            {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }

     */

    public QueriesManager() {
        executor = Executors.newFixedThreadPool(maxPoolSize);
    }

    public void addDoctor(Sz_lekarze lekarz, Command onQuryFinished) {
        String query = "{call dodajLekarza(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = null;

        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, lekarz.getImie());
            statement.setString(2, lekarz.getNazwisko());
            statement.setInt(3, 1); //stanowisko, 1 = lekarz, 2 = pielegniarka
            statement.setDouble(4, lekarz.getPensja());
            statement.setString(5, lekarz.getSpecjalizacja());
            statement.setString(6, lekarz.getStopiennaukowy());
            statement.setInt(7, lekarz.getOddzialy_id());
            statement.execute();
        }  catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać lekarza");
        }catch(Exception e)
        {
            ExceptionHandler.displayException(e);
        }finally {
            try
            {
                if (statement != null) {
                    statement.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }

    }

    public void addOperation(Sz_operacje operacja, int lekarzID){
        String query = "{call dodajOperacje(?, ?, ?, ?, ?)}";
        CallableStatement statement = null;
        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, operacja.getRodzajoperacji());
            statement.setString(2, operacja.getDatagodzinarozpoczecia());
            statement.setInt(3, operacja.getPacjenci_id());
            statement.setInt(4, operacja.getOddzialy_id());
            statement.setInt(5, lekarzID);
            statement.execute();
        }  catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać operacji");
        }catch(Exception e)
        {
            ExceptionHandler.displayException(e);
        }finally {
            try
            {
                if (statement != null) {
                    statement.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void addPatient(Sz_pacjenci p){
        PreparedStatement stmt = null;
        try {
            String insertPatient = "insert into SZ_PACJENCI " +
                    "(id,imie,nazwisko,pesel,adres,miasto,kod) values " +
                    "(?,?,?,?,?,?,?)";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertPatient);
            stmt.setInt(1, p.getId());
            stmt.setString(2, p.getImie());
            stmt.setString(3, p.getNazwisko());
            stmt.setString(4, p.getPesel());
            stmt.setString(5, p.getAdres());
            stmt.setString(6, p.getMiasto());
            stmt.setString(7, p.getKod());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać pacjenta");
        }catch(Exception e)
        {
            ExceptionHandler.displayException(e);
        }finally {
            try
            {
                if (stmt != null) {
                    stmt.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public List<Sz_lekarze> getDoctors() {
        String query = "select p.imie, p.nazwisko, p.pensja, p.oddzialy_id as oddzial, l.specjalizacja, l.stopiennaukowy, l.pracownikid as id " +
                "from sz_pracownicy p join sz_lekarze l on p.PRACOWNIKID = l.PRACOWNIKID " +
                "where p.stanowisko = 1";

        List<Sz_lekarze> lekarze = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(
                    query
            );

            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_lekarze lekarz = new Sz_lekarze(
                        rs.getString(1), rs.getString(2), rs.getDouble(3),
                        rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));

                lekarze.add(lekarz);
            }
        } catch (SQLException e) {
            ExceptionHandler.displaySQLException(e);
        }catch(Exception e)
        {
            ExceptionHandler.displayException(e);
        }finally {
            try
            {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }
        return lekarze;
    }

    public List<String> getOddzialy() {
        List<String> oddzialy = new ArrayList<>();

        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = Connector.getInstance().getConnection().createStatement();
            rs = statement.executeQuery("select nazwa from sz_oddzialy");

            while (rs.next()) {
                oddzialy.add(rs.getString(1));
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z oddziałami");
        }finally {
            try
            {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            }catch(SQLException e)
            {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return oddzialy;
    }

}
