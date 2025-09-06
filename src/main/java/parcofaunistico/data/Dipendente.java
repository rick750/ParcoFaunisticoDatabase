package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dipendente {
    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final int eta;
    private final String indirizzo;
    private final String telefono;
    private final String email;
    private final String mansione;
    private final String descrizioneMansione;

    public Dipendente(final String codiceFiscale, final String nome, final String cognome,
            final int eta, final String indirizzo, final String telefono, final String email,
            final String mansione, final String descrMans) {
        this.codiceFiscale = codiceFiscale;
        this.nome = nome;
        this.cognome = cognome;
        this.eta = eta;
        this.indirizzo = indirizzo;
        this.telefono = telefono;
        this.email = email;
        this.mansione = mansione;
        this.descrizioneMansione = descrMans;
    }

    @Override
    public String toString() {
        return this.codiceFiscale + ",      "
                + this.nome + ",       "
                + this.cognome + ",     "
                + this.eta + ",     "
                + this.indirizzo + ",   "
                + this.telefono + ",    "
                + this.email + ",   "
                + this.mansione + ",    "
                + this.descrizioneMansione;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public static final class DAO {
        public static List<Dipendente> list(final Connection connection) {
            final var dipendenti = new ArrayList<Dipendente>();
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_DIPENDENTE.get());
                    var resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    final var codice_fiscale = resultSet.getString("codice_fiscale");
                    final var nome = resultSet.getString("nome");
                    final var cognome = resultSet.getString("cognome");
                    final var eta = resultSet.getInt("eta");
                    final var indirizzo = resultSet.getString("indirizzo");
                    final var telefono = resultSet.getString("telefono");
                    final var email = resultSet.getString("email");
                    final var mansione = resultSet.getString("mansione");
                    final var descrizione = resultSet.getString("descrizione_mansione");
                    final var dipendente = new Dipendente(codice_fiscale, nome, cognome,
                            eta, indirizzo, telefono, email, mansione, descrizione);

                    dipendenti.add(dipendente);
                }
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return dipendenti;
        }

        public static boolean check(final Connection connection, final String codiceFiscale) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceFiscale));
                    var resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return true;
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
                    var resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (final Exception e) {
                throw new DAOException(e);
            }
        }

        public static boolean insert(final Connection connection, final Map<Parametri, String> textfields) {
            final String codiceFiscale = textfields.get(Parametri.CODICE_FISCALE);
            final String nome = textfields.get(Parametri.NOME);
            final String cognome = textfields.get(Parametri.COGNOME);
            final int eta = Integer.parseInt(textfields.get(Parametri.ETA));
            final String indirizzo = textfields.get(Parametri.INDIRIZZO);
            final String telefono = textfields.get(Parametri.TELEFONO);
            final String email = textfields.get(Parametri.EMAIL);
            final String mansione = textfields.get(Parametri.MANSIONE);
            final String descrizioneMans = textfields.get(Parametri.DESCRIZIONE_MANSIONE);
            final String nomeArea = textfields.get(Parametri.NOME_ZONA);

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
                if (!checkExistance(connection, codiceFiscale)) {
                    righeInserite = stmtPersone.executeUpdate();
                    System.out.println("Righe inserite in persona: " + righeInserite);
                }
                final String queryDipendente = """
                            INSERT INTO DIPENDENTE(codice_fiscale, mansione, descrizione_mansione)
                            VALUES (?, ?, ?)
                        """;

                try (PreparedStatement stmtDipendente = connection.prepareStatement(queryDipendente)) {
                    stmtDipendente.setString(1, codiceFiscale);
                    stmtDipendente.setString(2, mansione);
                    stmtDipendente.setString(3, descrizioneMans);
                    righeInserite = stmtDipendente.executeUpdate();
                    System.out.println("Righe inserite in dipendente: " + righeInserite);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return false;
                }

                final String queryLavoro = """
                            INSERT INTO LAVORA(nome, codice_fiscale)
                            VALUES (?, ?)
                        """;

                try (PreparedStatement stmtLavoro = connection.prepareStatement(queryLavoro)) {
                    stmtLavoro.setString(1, nomeArea);
                    stmtLavoro.setString(2, codiceFiscale);
                    righeInserite = stmtLavoro.executeUpdate();
                    System.out.println("Righe inserite in lavora: " + righeInserite);
                } catch (final Exception e) {
                    e.printStackTrace();
                    return false;
                }
            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        private static String getCheckQuery(final String codiceFiscale) {
            return Queries.CHECK_DIPENDENTE.get() + "\'" + codiceFiscale + "\'";
        }

        private static String getPersonaCheckQuery(final String codiceFiscale) {
            return Queries.CHECK_PERSONA.get() + "\'" + codiceFiscale + "\'";
        }

        public static String[] getNomeCognome(final Connection connection, final String codiceFiscale) {
            final String query = """
                    SELECT nome, cognome
                    FROM PERSONA
                    WHERE codice_fiscale = ?
                    """;
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setString(1, codiceFiscale);
                try (var rs = stmt.executeQuery()) {
                    if (rs.next()) {
                        final String nome = rs.getString("nome");
                        final String cognome = rs.getString("cognome");
                        return new String[] { nome, cognome };
                    }
                }
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return new String[] { codiceFiscale, "" };
        }
    }
}
