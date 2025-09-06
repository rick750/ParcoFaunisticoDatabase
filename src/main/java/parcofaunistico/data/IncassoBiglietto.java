package parcofaunistico.data;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class IncassoBiglietto {
    private final Date data;
    private final double incasso_giornaliero;

    public IncassoBiglietto(final Date data, final double incasso) {
        this.data = data;
        this.incasso_giornaliero = incasso;
    }

    @Override
    public String toString() {
        return "Data: " + this.data + ",      "
              + "Incasso totale: " + this.incasso_giornaliero;
    }

    public static final class DAO {
        public static List<IncassoBiglietto> list(Connection connection) {
            var incassi = new ArrayList<IncassoBiglietto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_INCASSI_BIGLIETTI.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getDate("data_effettuazione");
                    final var totale = resultSet.getDouble("totale_incassi_giornalieri");

                    final IncassoBiglietto inc = new IncassoBiglietto(nome, totale);
                    incassi.add(inc);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return incassi;            
        }
    }
}
