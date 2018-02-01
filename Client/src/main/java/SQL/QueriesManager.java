package SQL;

import Relations.*;
import Utils.Constants;
import Utils.ExceptionHandler;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class QueriesManager {
    public boolean setAutoCommitMode(boolean turnOn) {
        try {
            Connector.getInstance().getConnection().setAutoCommit(turnOn);
            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean commitOrRollback(boolean commit) {
        try {
            if (commit) {
                Connector.getInstance().getConnection().commit();
                System.out.println("Commited changes");
            } else {
                Connector.getInstance().getConnection().rollback();
                System.out.println("Rollback changes");
            }
            return true;
        } catch (SQLException e) {
            return false;
        } catch (Exception e) {
            return false;
        }
    }
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

    public void addOperation(Sz_operacje operacja, int lekarzID) {
        String query = "{call dodajOperacje(?, ?, ?, ?, ?)}";
        CallableStatement statement = null;
        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, operacja.getRodzajoperacji());
            statement.setString(2, operacja.getDatagodzinarozpoczecia().toString());
            statement.setInt(3, operacja.getPacjenci_id());
            statement.setInt(4, operacja.getOddzialy_id());
            statement.setInt(5, lekarzID);
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać operacji");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }


    public void addPatient(Sz_pacjenci p) {
        PreparedStatement stmt = null;
        try {
            String insertPatient = "insert into SZ_PACJENCI " +
                    "(id,imie,nazwisko,pesel,adres,miasto,kod) values " +
                    "(?,?,?,?,?,?,?)";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertPatient);
            stmt.setInt(1, 0);
            stmt.setString(2, p.getImie());
            stmt.setString(3, p.getNazwisko());
            stmt.setString(4, p.getPesel());
            stmt.setString(5, p.getAdres());
            stmt.setString(6, p.getMiasto());
            stmt.setString(7, p.getKod());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Dodano pacjenta");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void updatePatient(Sz_pacjenci p) {
        PreparedStatement stmt = null;
        try {
            String updatePatient = "update SZ_PACJENCI SET imie = ?, " +
                    "nazwisko = ?, pesel = ?, adres = ?, miasto = ?, kod = ? " +
                    "WHERE ID = ?  ";
            stmt = Connector.getInstance().getConnection().prepareStatement(updatePatient);
            stmt.setString(1, p.getImie());
            stmt.setString(2, p.getNazwisko());
            stmt.setString(3, p.getPesel());
            stmt.setString(4, p.getAdres());
            stmt.setString(5, p.getMiasto());
            stmt.setString(6, p.getKod());
            stmt.setInt(7, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public List<Sz_pacjenci> getPacjenci() {
        List<Sz_pacjenci> pacjenci = new ArrayList<>();
        Statement statement = null;
        ResultSet rs = null;

        try {
            statement = Connector.getInstance().getConnection().createStatement();
            rs = statement.executeQuery("select * from sz_pacjenci");

            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_pacjenci pacjent = new Sz_pacjenci(
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(1));

                pacjenci.add(pacjent);
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return pacjenci;
    }

    public void deletePatient(Sz_pacjenci p) {
        PreparedStatement stmt = null;
        try {
            String updatePatient = "delete from SZ_PACJENCI where id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(updatePatient);
            stmt.setInt(1, p.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            //  ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }


    public List<Sz_oddzialy> getOddzialy() {
        String query = "select * from SZ_ODDZIALY";

        List<Sz_oddzialy> oddzialy = new ArrayList<>();

        Statement stmt = null;
        ResultSet rs = null;

        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(query);

            while (rs.next()) {
                Sz_oddzialy oddzial = new Sz_oddzialy(
                        rs.getInt(1), rs.getInt(2), rs.getString(3), rs.getInt(4));

                oddzialy.add(oddzial);
            }
        } catch (SQLException e) {
            ExceptionHandler.displaySQLException(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }
        return oddzialy;
    }


    public boolean deleteWorker(int id, int stanowisko) {
        String query = "{call USUNPRACOWNIKA(?, ?)}";
        CallableStatement statement = null;
        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setInt(1, id);
            statement.setInt(2, stanowisko);
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się usunac pracownika!");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }

        return true;

    }

    public boolean deleteRecord(String database, String idName, int id) {
        Statement statement = null;
        String query = "";
        int result = 0;
        try {
            query = String.format("delete from %s where %s = %d", database, idName, id);
            statement = Connector.getInstance().getConnection().createStatement();

            result = statement.executeUpdate(query);
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można usunać danych z tabeli " + database.substring(3) + " . \nPowód : " + e.getMessage().substring(11));
            return false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                System.out.println("Zamknieto kursor przy usuwaniu danych");
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }

        if (result < 1) {
            ExceptionHandler.displayException("Nie usunięto żadnego rekordu");
            return false;
        }
        return true;
    }


    public List<Sz_pracownicy> getPracownicy(int stanowisko) {
        String query = "";
        if (stanowisko == Constants.DOCTOR) {
            query = "select p.imie, p.nazwisko, p.pensja, p.oddzialy_id as oddzial, l.specjalizacja, l.stopiennaukowy, l.pracownikid as id " +
                    "from sz_pracownicy p join sz_lekarze l on p.PRACOWNIKID = l.PRACOWNIKID " +
                    "where p.stanowisko = ?";

        } else {
            query = "select imie, nazwisko, pensja, oddzialy_id, PRACOWNIKID from SZ_PRACOWNICY" +
                    " where stanowisko = ?";

        }

        List<Sz_pracownicy> pracownicy = new ArrayList<>();

        PreparedStatement statement = null;
        ResultSet rs = null;

        try {
            statement = Connector.getInstance().getConnection().prepareStatement(query);
            statement.setInt(1, stanowisko);

            rs = statement.executeQuery();

            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                if (stanowisko == Constants.DOCTOR) {
                    Sz_lekarze lekarz = new Sz_lekarze(
                            rs.getString(1), rs.getString(2), rs.getDouble(3),
                            rs.getInt(4), rs.getString(5), rs.getString(6), rs.getInt(7));

                    pracownicy.add(lekarz);
                } else {
                    Sz_pielegniarki pielegniarka = new Sz_pielegniarki(
                            rs.getString(1), rs.getString(2), rs.getDouble(3),
                            rs.getInt(4), rs.getInt(5));

                    pracownicy.add(pielegniarka);
                }

            }
        } catch (SQLException e) {
            ExceptionHandler.displaySQLException(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }
        return pracownicy;

    }

    public void addDoctor(Sz_lekarze lekarz) {
        String query = "{call dodajLekarza(?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = null;

        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, lekarz.getImie());
            statement.setString(2, lekarz.getNazwisko());
            statement.setInt(3, Constants.DOCTOR);
            statement.setDouble(4, lekarz.getPensja());
            statement.setString(5, lekarz.getSpecjalizacja());
            statement.setString(6, lekarz.getStopiennaukowy());
            statement.setInt(7, lekarz.getOddzialy_id());
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać lekarza");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }

    }


    public void updateDoctor(Sz_lekarze lekarz) {
        //aktualizujLekarza
        String query = "{call aktualizujLekarza(?, ?, ?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = null;

        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, lekarz.getImie());
            statement.setString(2, lekarz.getNazwisko());
            statement.setInt(3, Constants.DOCTOR);
            statement.setDouble(4, lekarz.getPensja());
            statement.setString(5, lekarz.getSpecjalizacja());
            statement.setString(6, lekarz.getStopiennaukowy());
            statement.setInt(7, lekarz.getOddzialy_id());
            statement.setInt(8, lekarz.getPracownikid());
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się zaktualizować lekarza");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void addNurse(Sz_pielegniarki pielegniarka) {
        String query = "{call dodajPielegniarke(?, ?, ?, ?, ?)}";
        CallableStatement statement = null;

        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, pielegniarka.getImie());
            statement.setString(2, pielegniarka.getNazwisko());
            statement.setInt(3, Constants.NURSE);
            statement.setDouble(4, pielegniarka.getPensja());
            statement.setInt(5, pielegniarka.getOddzialy_id());
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać pielegniarki");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }


    public void updateNurse(Sz_pielegniarki pielegniarki) {
        //aktualizujLekarza
        String query = "{call aktualizujPielegniarke(?, ?, ?, ?, ?, ?)}";
        CallableStatement statement = null;

        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setString(1, pielegniarki.getImie());
            statement.setString(2, pielegniarki.getNazwisko());
            statement.setInt(3, Constants.DOCTOR);
            statement.setDouble(4, pielegniarki.getPensja());
            statement.setInt(5, pielegniarki.getOddzialy_id());
            statement.setInt(6, pielegniarki.getPracownikid());
            statement.execute();
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się zaktualizować pielęgniarki");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void addExamination(Sz_badania badanie) {
        PreparedStatement stmt = null;
        try {
            String insertExamination = "insert into SZ_BADANIA " +
                    "(id,nazwa,datagodzinabadania,sz_oddzialy_id,sz_pacjenci_id,sz_pracownicy_pracownikid) values " +
                    "(?,?,TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss'),?,?,?)";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertExamination);
            stmt.setInt(1, 0);
            stmt.setString(2, badanie.getNazwa());
            stmt.setString(3, badanie.getDatagodzinabadania());
            stmt.setInt(4, badanie.getOddzialy_id());
            stmt.setInt(5, badanie.getPacjenci_id());
            stmt.setInt(6, badanie.getPracownik_id());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public List<Sz_badania> getPatientExamination(Sz_pacjenci p) {
        List<Sz_badania> badania = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String showExamination = "select * from SZ_BADANIA " +
                    "where sz_pacjenci_id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(showExamination);
            stmt.setInt(1, p.getId());
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_badania badanie = new Sz_badania(
                        rs.getString(2), rs.getString(3), rs.getInt(4),
                        rs.getInt(5), rs.getInt(6), rs.getInt(1));

                badania.add(badanie);
            }
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return badania;
    }

    public void deleteExamination(Sz_badania b) {
        PreparedStatement stmt = null;
        try {
            String deleteExamination = "delete from SZ_BADANIA where id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(deleteExamination);
            stmt.setInt(1, b.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void updateExamination(Sz_badania badanie) {
        PreparedStatement stmt = null;
        try {
            String updatePatient = "update SZ_BADANIA SET nazwa = ?, " +
                    "datagodzinabadania = TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss')" +
                    ", sz_oddzialy_id =?,sz_pacjenci_id=?, " +
                    "sz_pracownicy_pracownikid = ? WHERE ID = ?  ";
            stmt = Connector.getInstance().getConnection().prepareStatement(updatePatient);
            stmt.setString(1, badanie.getNazwa());
            stmt.setString(2, badanie.getDatagodzinabadania());
            stmt.setInt(3, badanie.getOddzialy_id());
            stmt.setInt(4, badanie.getPacjenci_id());
            stmt.setInt(5, badanie.getPracownik_id());
            stmt.setInt(6, badanie.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void addPobyt(Sz_pobyty pobyt) {
        PreparedStatement stmt = null;
        try {
            String insertPobyt = "insert into SZ_POBYTY " +
                    "(sz_pacjenci_id,dataprzyjecia,sz_oddzialy_id) values " +
                    "(?,TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss'),?)";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertPobyt);
            stmt.setInt(1, pobyt.getPacjenci_id());
            stmt.setString(2, pobyt.getDataprzyjecia());
            stmt.setInt(3, pobyt.getOddzialy_id());
            stmt.execute();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public boolean addOddzial(Sz_oddzialy oddzialToAdd) {

        Statement stmt = null;
        boolean valToReturn = true;
        int kierownik = oddzialToAdd.getKierownikkliniki();
        String query = String.format("insert into sz_oddzialy(nazwa, KIEROWNIKKLINIKI, MAKSYMALNALICZBAPACJENTOW) values" +
                        " ('%s', %d, %d )",
                oddzialToAdd.getNazwa(), kierownik == 0 ? null : kierownik, oddzialToAdd.getMaksymalnaliczbapacjentow());
        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            int result = stmt.executeUpdate(query);
            if (result < 1) {
                valToReturn = false;
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można dodać oddziału");
            valToReturn = false;
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
            valToReturn = false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }

        return valToReturn;
    }

    public boolean updateOddzial(Sz_oddzialy oddzial) {
        Statement stmt = null;
        String query = String.format("update sz_oddzialy set nazwa = '%s', KIEROWNIKKLINIKI  = %d, MAKSYMALNALICZBAPACJENTOW = %d where id = %d",
                oddzial.getNazwa(), oddzial.getKierownikkliniki(), oddzial.getMaksymalnaliczbapacjentow(), oddzial.getId());
        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            int result = stmt.executeUpdate(query);

        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można zaktualizować oddzialu");
            return false;
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
            return false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return true;
    }


    //---------------operacje
    public boolean dodajUsunLekarzOperacja(int lekarzId, int operacjaId, boolean usun) {
        String query = "";
        if (usun) {
            query = "{call USUNLEKARZAZOPERACJI(?, ?)}";
        } else {
            query = "{call DODAJLEKARZADOOPERACJI(?, ?)}";
        }
        CallableStatement statement = null;
        boolean result = true;
        try {
            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.setInt(1, lekarzId);
            statement.setInt(2, operacjaId);
            int res = statement.executeUpdate();
            if (res < 1) {
                result = false;
            }
        } catch (SQLException e) {
            result = false;
            ExceptionHandler.displayException("Nie udało się " + (usun ? "usunąć" : "dodać") + " lekarza.");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
            result = false;
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return result;
    }

    public boolean updateOperation(Sz_operacje operationBeingUpdated) {
        Statement stmt = null;
        boolean result = true;
        //dateformatter
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String query = String.format("update sz_operacje set ODDZIALY_ID = %d,  DATAGODZINAROZPOCZECIA = TO_DATE('%s'), RODZAJOPERACJI = '%s' ",
                operationBeingUpdated.getOddzialy_id(), operationBeingUpdated.getDatagodzinarozpoczecia().format(formatter), operationBeingUpdated.getRodzajoperacji());
        try {
            stmt = Connector.getInstance().getConnection().createStatement();
            int res = stmt.executeUpdate(query);
            if (res < 1) {
                result = false;
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z lekarzami");
            result = false;
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
            result = false;
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }

        }
        return result;
    }


    public int addOperation(Sz_operacje operacja) {
        String query = "{? = call dodajoperacje_func(?, ?, ?, ?)}";
        CallableStatement statement = null;
        int operacja_id = -1;
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

            statement = Connector.getInstance().getConnection().prepareCall(query);
            statement.registerOutParameter(1, Types.INTEGER);
            statement.setString(2, operacja.getRodzajoperacji());
            statement.setString(3, operacja.getDatagodzinarozpoczecia().format(formatter));
            statement.setInt(4, operacja.getPacjenci_id());
            statement.setInt(5, operacja.getOddzialy_id());
            statement.execute();
            operacja_id = statement.getInt(1);
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie udało się dodać operacji");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return operacja_id;
    }

    public List<OperationTableView> getDataForOperations() {
        Statement stmt = null;
        ResultSet rs = null;
        List<OperationTableView> operationTableViews = new ArrayList<>();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

        String query = "select o.operacjaid, p.imie, p.nazwisko, p.pesel,to_char(o.datagodzinarozpoczecia, 'YYYY-MM-DD HH:MI'), o.rodzajoperacji, o.oddzialy_id, p.ID,  oo.nazwa from sz_operacje o join sz_pacjenci p on p.id = o.pacjenci_id join sz_oddzialy oo on o.ODDZIALY_ID = oo.ID";
        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(
                    query
            );

            while (rs.next()) {
                LocalDateTime dateTime = LocalDateTime.parse(rs.getString(5), formatter);
                OperationTableView operationTableView = new OperationTableView(
                        rs.getInt(1), rs.getString(2), rs.getString(3), rs.getString(4),
                        dateTime, rs.getString(6),rs.getInt(7),
                        rs.getInt(8), rs.getString(9)
                );
                operationTableViews.add(operationTableView);
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z danymi operacji!");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return operationTableViews;
    }

    public List<Sz_wykonywaneoperacje> getWykonywaneBadanie(int operacjaid) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Sz_wykonywaneoperacje> wykonywaneoperacjes = new ArrayList<>();

        String query = "select o.operacjaid, p.imie, p.nazwisko, p.pesel, o.datagodzinarozpoczecia, o.rodzajoperacji, o.oddzialy_id, p.ID from sz_operacje o join sz_pacjenci p on p.id = o.pacjenci_id";
        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(
                    query
            );

            while (rs.next()) {

                Sz_wykonywaneoperacje wykonywaneoperacja = new Sz_wykonywaneoperacje(
                        rs.getInt(1), rs.getInt(2)
                        );

                wykonywaneoperacjes.add(wykonywaneoperacja);
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z danymi lekarzy do operacji!");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return wykonywaneoperacjes;
    }
    public List<Sz_pracownicy> getDoctorsFromOperation(int operacjaid) {
        Statement stmt = null;
        ResultSet rs = null;
        List<Sz_pracownicy> doctors = new ArrayList<>();

        String query = String.format("select imie, nazwisko, stanowisko, pracownikid from sz_pracownicy p join sz_wykonywaneoperacje wo on p.pracownikid = wo.LEKARZE_PRACOWNIKID join sz_operacje o on wo.OPERACJE_OPERACJAID = o.OPERACJAID where o.operacjaid = %d ", operacjaid);
        try {
            stmt = Connector.getInstance().getConnection().createStatement();

            rs = stmt.executeQuery(
                    query
            );

            while (rs.next()) {

                Sz_pracownicy pracownik = new Sz_pracownicy();
                pracownik.setImie(rs.getString(1));
                pracownik.setNazwisko(rs.getString(2));
                pracownik.setStanowisko(rs.getInt(3));
                pracownik.setPracownikid(rs.getInt(4));
                doctors.add(pracownik);
            }
        } catch (SQLException e) {
            ExceptionHandler.displayException("Nie można pobrać listy z danymi lekarzy do operacji!");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return doctors;
    }



    public boolean ifOnWard(Sz_pacjenci pacjent) {
        List<Sz_pobyty> badania = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String showExamination = "select * from SZ_POBYTY " +
                    "where sz_pacjenci_id=? and datawypisania is NULL ";
            stmt = Connector.getInstance().getConnection().prepareStatement(showExamination);
            stmt.setInt(1, pacjent.getId());
            rs = stmt.executeQuery();
            int count = 0;
            while (rs.next()) {
                count++;
            }
            if (count == 0) return false;
            return true;
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return false;
    }

    public List<Sz_pobyty> getPobytyPacjenta(Sz_pacjenci pacjent) {
        List<Sz_pobyty> pobyty = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String showExamination = "select sz_pacjenci_id,dataprzyjecia,nvl(datawypisania,''),sz_oddzialy_id,pobytid from SZ_POBYTY " +
                    "where sz_pacjenci_id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(showExamination);
            stmt.setInt(1, pacjent.getId());
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_pobyty pobyt = new Sz_pobyty(
                        rs.getInt(5), rs.getInt(1), rs.getInt(4),
                        rs.getString(2), rs.getString(3));

                pobyty.add(pobyt);
            }
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return pobyty;
    }


    public void updatePobyt(Sz_pobyty pobyt) {
        PreparedStatement stmt = null;
        try {
            String updatePobyt;
            if (pobyt.getDatawypisania() != null) {
                updatePobyt = "update SZ_POBYTY SET dataprzyjecia = TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss'), " +
                        "datawypisania = TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss')" +
                        ", sz_oddzialy_id =? where pobytid =?";
                stmt = Connector.getInstance().getConnection().prepareStatement(updatePobyt);
                stmt.setString(1, pobyt.getDataprzyjecia());
                stmt.setString(2, pobyt.getDatawypisania());
                stmt.setInt(3, pobyt.getOddzialy_id());
                stmt.setInt(4,pobyt.getID());
                stmt.executeUpdate();
            } else {
                updatePobyt = "update SZ_POBYTY SET dataprzyjecia = TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss')" +
                        ", sz_oddzialy_id =? where pobytid =?";
                stmt = Connector.getInstance().getConnection().prepareStatement(updatePobyt);
                stmt.setString(1, pobyt.getDataprzyjecia());
                stmt.setInt(2, pobyt.getOddzialy_id());
                stmt.setInt(3,pobyt.getID());
                stmt.executeUpdate();
            }

        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Pomyślnie zedytowano pobyt.");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void deletePobyt(Sz_pobyty pobyt) {
        PreparedStatement stmt = null;
        try {
            String deletePobyt = "delete from SZ_POBYTY where pobytid=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(deletePobyt);
            stmt.setInt(1, pobyt.getID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            //  ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                    ExceptionHandler.displayException("Pomyślnie usunięto pobyt");

                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public List<PacjentNaOddziale> getPacjenciNaOddziale() {
        List<PacjentNaOddziale> pacjenci = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String showExamination = "SELECT id,pobytid,sz_oddzialy_id,imie,nazwisko,pesel,sz_oddzialy_id,dataprzyjecia\n" +
                    "FROM SZ_PACJENCI\n" +
                    "INNER JOIN sz_pobyty\n" +
                    "ON sz_pacjenci.ID = sz_pobyty.sz_pacjenci_id where datawypisania is null";
            stmt = Connector.getInstance().getConnection().prepareStatement(showExamination);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                PacjentNaOddziale pacjent = new PacjentNaOddziale(
                        rs.getInt(1), rs.getInt(2),
                        rs.getInt(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8));

                pacjenci.add(pacjent);
            }
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return pacjenci;
    }

    public void wypisz(PacjentNaOddziale pacjentNaOddziale) {
        PreparedStatement stmt = null;
        try {
            String updatePobyt;
            updatePobyt = "update SZ_POBYTY SET datawypisania = TO_DATE(?, 'yyyy/mm/dd hh24:mi:ss') " +
                    "where pobytid=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(updatePobyt);
            stmt.setString(1, pacjentNaOddziale.getDataWpisania());
            stmt.setInt(2, pacjentNaOddziale.getPobytID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Pomyślnie zedytowano pobyt.");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public Sz_pacjenci getPacjent(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Sz_pacjenci pacjent = new Sz_pacjenci(null, null, null, null, null, null, 0);
        try {
            String getPacjent = "select * from sz_pacjenci where id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(getPacjent);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                pacjent = new Sz_pacjenci(
                        rs.getString(2), rs.getString(3), rs.getString(4),
                        rs.getString(5), rs.getString(6), rs.getString(7), rs.getInt(1));
            }
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return pacjent;
    }

    public Sz_badania getBadanie(int id) {
        PreparedStatement stmt = null;
        ResultSet rs = null;
        Sz_badania badanie = new Sz_badania(null, null, 0, 0, 0, 0);
        try {
            String getBadanie = "select * from sz_badania where id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(getBadanie);
            stmt.setInt(1,id);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                badanie = new Sz_badania(
                        rs.getString(2), rs.getString(3), rs.getInt(4),
                        rs.getInt(5), rs.getInt(6), rs.getInt(1));
            }
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return badanie;
    }

    public List<Badanie> getAllExaminations() {
        List<Badanie> badania = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String showExamination = "SELECT sz_badania.id,SZ_BADANIA.NAZWA,pesel,datagodzinabadania,sz_oddzialy.nazwa,sz_PRACOWNICY.IMIE,sz_PRACOWNICY.NAZWISKO,sz_pacjenci.ID\n" +
                    "FROM SZ_BADANIA\n" +
                    "INNER JOIN sz_PACJENCI\n" +
                    "ON sz_pacjenci.ID = SZ_BADANIA.SZ_PACJENCI_ID\n" +
                    "INNER JOIN sz_PRACOWNICY\n" +
                    "ON sz_pracownicy.pracownikID = SZ_BADANIA.SZ_PRACOWNICY_PRACOWNIKID\n" +
                    "INNER JOIN sz_oddzialy\n" +
                    "ON sz_oddzialy.id = SZ_BADANIA.SZ_ODDZIALY_ID";
            stmt = Connector.getInstance().getConnection().prepareStatement(showExamination);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Badanie badanie = new Badanie(
                        rs.getString(1), rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6)+' '+rs.getString(7),
                        rs.getString(8));


                badania.add(badanie);
            }
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return badania;
    }

    public List<Sz_leki> getLeki() {
        List<Sz_leki> leki = new ArrayList<>();
        PreparedStatement stmt = null;
        ResultSet rs = null;

        try {
            String getLeki = "select * from sz_leki";
            stmt = Connector.getInstance().getConnection().prepareStatement(getLeki);
            rs = stmt.executeQuery();
            while (rs.next()) {
                // Sz_lekarze(String imie, String nazwisko, double pensja,  int oddzialID, String specjalizacja, String stopiennaukowy, int ID)
                Sz_leki lek = new Sz_leki(
                        rs.getString(1), rs.getString(2),
                        rs.getString(3));
                leki.add(lek);
            }
        } catch (SQLException e) {
            ExceptionHandler.showMessage("Nie można pobrać listy z pacjentami");
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
        return leki;
    }

    public void updateLeki(Sz_leki lek)
    {
        PreparedStatement stmt = null;
        try {
            String updateLeki = "update SZ_LEKI SET nazwamiedzynarodowa = ?," +
                       "nazwahandlowa=? where leki_id= ?";
                stmt = Connector.getInstance().getConnection().prepareStatement(updateLeki);
                stmt.setString(1, lek.getNazwaMiedzynarodowa());
                stmt.setString(2, lek.getNazwaHandlowa());
            stmt.setString(3, lek.getId());
                stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Pomyślnie zedytowano lek.");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }

    public void insertLeki(Sz_leki lek)
    {
        PreparedStatement stmt = null;
        try {
            String insertLeki = "insert into SZ_LEKI " +
                    "(leki_id,nazwamiedzynarodowa,nazwahandlowa) values " +
                    "(?,?,?)";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertLeki);
            stmt.setString(2, lek.getNazwaMiedzynarodowa());
            stmt.setString(3, lek.getNazwaHandlowa());
            stmt.setString(1, null);
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Pomyślnie dodano lek.");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }
    public void deleteLeki(Sz_leki lek)
    {
        PreparedStatement stmt = null;
        try {
            String insertLeki = "DELETE from SZ_LEKI " +
                    "where leki_id=?";
            stmt = Connector.getInstance().getConnection().prepareStatement(insertLeki);
            stmt.setString(1, lek.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            ExceptionHandler.getMessage(e);
        } catch (Exception e) {
            ExceptionHandler.displayException(e);
        } finally {
            try {
                if (stmt != null) {
                    ExceptionHandler.showMessage("Pomyślnie usunięto lek.");
                    stmt.close();
                }
            } catch (SQLException e) {
                ExceptionHandler.displayException("Nie udało się zamknąć");
            }
        }
    }



}

