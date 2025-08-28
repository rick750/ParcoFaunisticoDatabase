package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Ordine {
    final String codiceOrdine;
    final int quantita;
    final Date data;

    public Ordine(final String codice, final int quantita, final Date data) {
        this.codiceOrdine = codice;
        this.quantita = quantita;
        this.data = data;
    }

    @Override
    public String toString() {
        return this.codiceOrdine + ", "
             + this.quantita + ", "
             + this.data;
    }

     public static final class DAO {
        public static List<Ordine> list(Connection connection) {
            var ordini = new ArrayList<Ordine>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ORDINE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_ordine");
                    final var quantita = resultSet.getInt("quantita_acquistata");
                    final var data = resultSet.getDate("data");
                    final var ordine = new Ordine(codice, quantita, data);
                    ordini.add(ordine);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return ordini;            
        }
    }
}
