package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Visitatore {

    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final int eta;
    private final String indirizzo;
    private final String telefono;
    private final String email;

    public Visitatore(String codiceFiscale, String nome, String cognome, int eta, String indirizzo, String telefono,
            String email) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
    }

    @Override
    public String toString() {
        return this.codiceFiscale + ", "
                + this.nome + ", "
                + this.cognome + ", "
                + this.eta + ", "
                + this.indirizzo + ", "
                + this.telefono + ", "
                + this.email;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public static final class DAO {
        public static List<Visitatore> list(Connection connection) {
            var persone = new ArrayList<Visitatore>();
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_VISITATORE.get());
                    var resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    var codice_fiscale = resultSet.getString("codice_fiscale");
                    var nome = resultSet.getString("nome");
                    var cognome = resultSet.getString("cognome");
                    var eta = resultSet.getInt("eta");
                    var indirizzo = resultSet.getString("indirizzo");
                    var telefono = resultSet.getString("telefono");
                    var email = resultSet.getString("email");
                    var persona = new Visitatore(codice_fiscale, nome, cognome, eta, indirizzo, telefono, email);
                    persone.add(persona);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return persone;
        }

        public static int getAge(final Connection connection, final String codiceFiscale) {
            int eta;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getAgeQuery(codiceFiscale));
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    eta = resultSet.getInt("eta");
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return eta;
        }

        public static Map<Parametri, String> getVisitatore(final Connection connection, final String codiceFiscale) {
            final var visitatore = new EnumMap<Parametri, String>(Parametri.class);
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getAgeQuery(codiceFiscale));
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    visitatore.put(Parametri.CODICE_FISCALE, codiceFiscale);
                    final var nome = resultSet.getString("nome");
                    visitatore.put(Parametri.NOME, nome);
                    final var cognome = resultSet.getString("cognome");
                    visitatore.put(Parametri.COGNOME, cognome);
                    final var eta = resultSet.getInt("eta");
                    visitatore.put(Parametri.ETA, String.valueOf(eta));
                    final var indirizzo = resultSet.getString("indirizzo");
                    visitatore.put(Parametri.INDIRIZZO, indirizzo);
                    final var telefono = resultSet.getString("telefono");
                    visitatore.put(Parametri.TELEFONO, telefono);
                    final var email = resultSet.getString("email");
                    visitatore.put(Parametri.EMAIL, email);
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return visitatore;
        }

        public static boolean check(Connection connection, String codiceFiscale) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceFiscale));
                    var resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        private static boolean checkExistanceVisita(final Connection connection, final String codiceFiscale, final String nomeArea) {
            try (
                var preparedStatement = DAOUtils.prepare(connection, getVisitaCheckQuery(codiceFiscale, nomeArea));
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }            
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        private static boolean checkManutenzione(final Connection connection, final String nomeArea) {
            try (
                var preparedStatement = DAOUtils.prepare(connection, getManutenzionecheckQuery(nomeArea));
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    final var oggi = Date.valueOf(LocalDate.now());
                    final var data_inizio = resultSet.getDate("data_inizio");
                    final Date data_fine = resultSet.getDate("data_fine"); 
                    if ((oggi.after(data_inizio) || oggi.equals(data_inizio)) && oggi.before(data_fine) &&
                            (data_inizio.before(data_fine) || data_inizio.equals(data_fine))) {
                            return true;
                    }
                    return false;
                } else {
                    return false;
                }            
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        private static boolean checkExistance(final Connection connection, final String codiceFiscale) {
            try (
                var preparedStatement = DAOUtils.prepare(connection, getPersonaCheckQuery(codiceFiscale));
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }            
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }


        public static boolean insert(Connection connection, Map<Parametri, String> textfields) {
            final String codiceFiscale = textfields.get(Parametri.CODICE_FISCALE);
            final String nome = textfields.get(Parametri.NOME);
            final String cognome = textfields.get(Parametri.COGNOME);
            final int eta = Integer.parseInt(textfields.get(Parametri.ETA));
            final String indirizzo = textfields.get(Parametri.INDIRIZZO);
            final String telefono = textfields.get(Parametri.TELEFONO);
            final String email = textfields.get(Parametri.EMAIL);

            // Primo inserimento nella tabella persone
            final String queryPersone = """
                        INSERT INTO PERSONA(codice_fiscale, nome, cognome, eta, indirizzo, telefono, email)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtPersone = connection.prepareStatement(queryPersone)) {
                stmtPersone.setString(1, codiceFiscale);
                stmtPersone.setString(2, nome);
                stmtPersone.setString(3, cognome);
                stmtPersone.setInt(4, eta);
                stmtPersone.setString(5, indirizzo);
                stmtPersone.setString(6, telefono);
                stmtPersone.setString(7, email);
                int righeInserite;
                if(! checkExistance(connection, codiceFiscale)) {
                    righeInserite = stmtPersone.executeUpdate();
                    System.out.println("Righe inserite in persona: " + righeInserite);
                }
                // Secondo inserimento nella tabella visitatori
                final String queryVisitatori = """
                            INSERT INTO VISITATORE(codice_fiscale)
                            VALUES (?)
                        """;

                try (PreparedStatement stmtVisitatori = connection.prepareStatement(queryVisitatori)) {
                    stmtVisitatori.setString(1, codiceFiscale);
                    righeInserite = stmtVisitatori.executeUpdate();
                    System.out.println("Righe inserite in visitatore: " + righeInserite);
                } catch (Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                return false;
            }

            return true;
        }

        public static boolean insertNewVisita(final Connection connection, final String codiceFiscale, final String nomeArea) {
            final String queryNewVisita = """
                        INSERT INTO VISITA(codice_fiscale, nome, data)
                        VALUES (?, ?, ?)
                    """;
            
            try (PreparedStatement stmtVisita = connection.prepareStatement(queryNewVisita)) {
                stmtVisita.setString(1, codiceFiscale);
                stmtVisita.setString(2, nomeArea);
                stmtVisita.setDate(3, Date.valueOf(LocalDate.now()));
                int righeInserite;
                if(! checkExistanceVisita(connection, codiceFiscale, nomeArea)) {
                    if (! checkManutenzione(connection, nomeArea)) {
                        righeInserite = stmtVisita.executeUpdate();
                        System.out.println("Righe inserite in visita: " + righeInserite);
                        return true;
                    }
                    return false;
                } else {
                    return false;
                }
            } catch(Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private static String getCheckQuery(String codiceFiscale) {
            return Queries.CHECK_VISITATORE.get() + "\'" + codiceFiscale + "\'";
        }

        private static String getPersonaCheckQuery(final String codiceFiscale) {
            return Queries.CHECK_PERSONA.get() + "\'"+codiceFiscale+ "\'";
        }

        private static String getVisitaCheckQuery(final String codiceFiscale, final String nomeArea) {
            return """
                    SELECT * 
                    FROM VISITA
                    WHERE codice_fiscale = """ + "\'" + codiceFiscale + "\'"
                    + " AND nome = " + "\'" + codiceFiscale + "\'"
                    + " AND data = " + "\'" + String.valueOf(LocalDate.now()) + "\'";
        }

        private static String getManutenzionecheckQuery(final String nomeArea) {
            return """
                    SELECT *
                    FROM MANUTENZIONE
                    WHERE nome = """ + "\'" + nomeArea + "\'";
        }

        private static String getAgeQuery(String codiceFiscale) {
            return Queries.SHOW_VISITATORE_SINGOLO.get() + "\'" + codiceFiscale + "\'";
        }
    }

}
