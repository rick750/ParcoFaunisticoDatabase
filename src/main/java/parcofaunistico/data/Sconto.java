package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Sconto {
    final String codiceSconto;
    final double percentuale;
    final String tipologia;

    public Sconto(final String codice, final double percentuale, final String tipologia) {
        this.codiceSconto = codice;
        this.percentuale = percentuale;
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return this.codiceSconto + ", "
             + this.percentuale + ", "
             + this.tipologia;
    }

    public static final class DAO {
        public static List<Sconto> list(Connection connection) {
            var sconti = new ArrayList<Sconto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_SCONTO);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_sconto");
                    final var percentuale = resultSet.getDouble("percentuale");
                    final var tipologia = resultSet.getString("tipologia");
                    final var sconto = new Sconto(codice, percentuale, tipologia);
                    sconti.add(sconto);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return sconti;            
        }
    }
}
