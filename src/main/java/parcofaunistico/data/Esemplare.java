package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Esemplare {
    final String nome;
    final String sesso;
    final int eta;
    final double altezza;
    final int peso;
    final boolean malato;

    public Esemplare(final String nome, final String sesso, final int eta, final double altezza,
                      final int peso, final boolean malato) {
                        this.nome = nome;
                        this.sesso = sesso;
                        this.eta = eta;
                        this.altezza = altezza;
                        this.peso = peso;
                        this.malato = malato;
    }

    @Override
    public String toString() {
        return this.nome + ", "
               + this.sesso + ", "
               + this.eta + ", "
               + this.altezza + ", "
               + this.peso + ", "
               + this.malato;
    }

    public static final class DAO {
        public static List<Esemplare> list(Connection connection) {
            var esemplari = new ArrayList<Esemplare>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ESEMPLARE);
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var sesso = resultSet.getString("sesso");
                    final var eta = resultSet.getInt("eta");
                    final var altezza = resultSet.getDouble("altezza");
                    final var peso = resultSet.getInt("peso");
                    final var malato = resultSet.getBoolean("malato");
                    final var esemplare = new Esemplare(nome, sesso, eta, altezza, peso, malato);
                    esemplari.add(esemplare);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return esemplari;            
        }
    }
}
