package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RendimentoGiornaliero {
    final Date data;
    final int numeroVendite;
    final double fatturato;

    public RendimentoGiornaliero(final Date data, final int numVendite, final Double fatturato) {
        this.data = data;
        this.numeroVendite = numVendite;
        this.fatturato = fatturato;
    }

    @Override
    public String toString() {
        return "Data: " + this.data + ", "
             + "Numero Vendite: " + this.numeroVendite + ", "
             + "Fatturato: " + this.fatturato;
    }

    public static final class DAO {
        public static List<RendimentoGiornaliero> list(Connection connection) {
            var rendimenti = new ArrayList<RendimentoGiornaliero>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_RENDIMENTO_GIORNALIERO.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var data = resultSet.getDate("data");
                    final var numVendite = resultSet.getInt("numero_vendite");
                    final var fatturato = resultSet.getDouble("fatturato");
                    final var rendimento = new RendimentoGiornaliero(data, numVendite, fatturato);
                    rendimenti.add(rendimento);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return rendimenti;            
        }
    }
}


