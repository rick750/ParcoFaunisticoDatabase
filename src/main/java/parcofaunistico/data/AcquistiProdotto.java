package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AcquistiProdotto {
    private final String codice;
    private final String nome;
    private final int acquisti;

    public AcquistiProdotto(final String codice, final String nome, final int acquisti) {
        this.codice = codice;
        this.nome = nome;
        this.acquisti = acquisti;
    }

    @Override
    public String toString() {
        return "codice prodotto: " + this.codice + ",       "
              + "nome prodotto: " + this.nome + ",      "
              + "quantità venduta: " + this.acquisti;
    }

    public static final class DAO {
        public static List<AcquistiProdotto> list(Connection connection) {
            var acquisti = new ArrayList<AcquistiProdotto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ACQUISTI_PRODOTTO.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_prodotto");
                    final var nome = resultSet.getString("nome");
                    final var quantita = resultSet.getInt("totale_venduto");

                    final AcquistiProdotto acquisto = new AcquistiProdotto(codice, nome, quantita);
                    acquisti.add(acquisto);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return acquisti;            
        }
    }
}
