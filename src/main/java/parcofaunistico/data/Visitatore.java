package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Visitatore {

    private final String codiceFiscale;
    private final String nome;
    private final String cognome;
    private final int eta;
    private final String indirizzo;
    private final String telefono;
    private final String email;

    public Visitatore(String codiceFiscale, String nome, String cognome, int eta, String indirizzo, String telefono, String email) {
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
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_PERSONE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
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

        public static boolean check(Connection connection, String codiceFiscale) {
            try (
                var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceFiscale));
                var resultSet = preparedStatement.executeQuery();
            ) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }            
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        private static String getCheckQuery(String codiceFiscale) {
            return Queries.CHECK_VISITATORE.get() + "\'"+codiceFiscale+ "\'";
        }
    }

}
