package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

public class Gruppo {
    private final String codice;
    private final int numPartecipanti;

    public Gruppo(final String codice, final int numPartecipanti) {
        this.codice = codice;
        this.numPartecipanti = numPartecipanti;
    }

    public static class DAO {
        public static String getLast(final Connection connection) {
            String codiceGruppo;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ULTIMO_GRUPPO.get());
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    codiceGruppo = resultSet.getString("codice_gruppo");
                
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return codiceGruppo;
        }

        public static boolean insert(final Connection connection, final String codiceGruppo, 
                                        final int numPartecipanti, final List<String> partecipanti) {
            // Primo inserimento nella tabella persone
            final String queryGruppo = """
                    INSERT INTO GRUPPO(codice_gruppo, numero_partecipanti)
                    VALUES (?, ?)
                    """;
            final String queryPartecipazione = """
                    INSERT INTO partecipazione(codice_gruppo, codice_fiscale)
                    VALUES (?, ?)
                    """;
            try (PreparedStatement stmtGruppo = connection.prepareStatement(queryGruppo)) {
                stmtGruppo.setString(1, codiceGruppo);
                stmtGruppo.setInt(2, numPartecipanti);
                stmtGruppo.executeUpdate();
                
                try (PreparedStatement psPartecipazione = connection.prepareStatement(queryPartecipazione)) {
                    for (String codiceFiscale : partecipanti) {
                        psPartecipazione.setString(1, codiceGruppo);
                        psPartecipazione.setString(2, codiceFiscale);
                        psPartecipazione.addBatch();
                    }
                    psPartecipazione.executeBatch();
                }
            } catch (final Exception e) {
                return false;
            }
            return true; 
        }
    }
}
