package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_DIPENDENTE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
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

        private static String getCheckQuery(final String codiceFiscale) {
            return Queries.CHECK_DIPENDENTE.get() + "\'"+codiceFiscale+ "\'";
        }
    }
}
