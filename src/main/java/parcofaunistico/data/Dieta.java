package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Dieta {
    final String alimento;
    final int numeroPasti;

    public Dieta(final String alimento, final int numPasti) {
        this.alimento = alimento;
        this.numeroPasti = numPasti;
    }

    @Override
    public String toString() {
        return this.alimento + ", "
               + this.numeroPasti;
    }

    public static final class DAO {
        public static List<Dieta> list(Connection connection) {
            var diete = new ArrayList<Dieta>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_DIETA);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var alimento = resultSet.getString("alimento");
                    final var numPasti = resultSet.getInt("numero_pasti");
                    final var dieta = new Dieta(alimento, numPasti);
                    diete.add(dieta);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return diete;            
        }
    }
}
