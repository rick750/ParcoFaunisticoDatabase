package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Affluenza {
    private final String nome;
    private final int affluenza;

    public Affluenza(final String nome, final int affl) {
        this.nome = nome;
        this.affluenza = affl;
    }

    @Override
    public String toString() {
        return this.nome + ", "
              + this.affluenza;
    }

    
    public static final class DAO {
        public static List<Affluenza> list(Connection connection) {
            var affluenze = new ArrayList<Affluenza>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_AFFLUENZA.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var affluenza = resultSet.getInt("numVisite");

                    final Affluenza aff = new Affluenza(nome, affluenza);
                    affluenze.add(aff);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return affluenze;            
        }
    }
}
