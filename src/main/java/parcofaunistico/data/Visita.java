package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Visita {
    private final String nomeArea;
    private final Date data;

    public Visita(final String codiceFiscale, final String area, final Date data) {
        this.nomeArea = area;
        this.data = data;
    }

    @Override
    public String toString() {
        return "Nome Area: " + this.nomeArea + ", "
              + "Data: " + this.data;
    }

    
    public static final class DAO {
        public static List<Visita> list(Connection connection, String codiceFiscale) {
            var visite = new ArrayList<Visita>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, getQuery(codiceFiscale));
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {

                    final var nome = resultSet.getString("nome");
                    final var data = resultSet.getDate("data");

                    final var visita = new Visita(codiceFiscale, nome, data);
                    visite.add(visita);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return visite;            
        }

        private static String getQuery(final String codiceFiscale) {
            return Queries.SHOW_VISITE.get() + "\'" + codiceFiscale + "\'" + " ORDER BY data";
        } 
    }
}
