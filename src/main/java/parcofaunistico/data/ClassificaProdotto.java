package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ClassificaProdotto {
    private final String zona;
    private final String codiceProdotto;
    private final String nomeProdotto;
    private final int quantitaTotale;
    private final double ricavo;
    private final double ricavoTotale;
    private final double contributo;

    public ClassificaProdotto(final String zona, final String codProd, final String nomeProd,
                                final int quanTot, final double ricavo, final double ricTot, final double contr) {
                                    this.zona = zona;
                                    this.codiceProdotto = codProd;
                                    this.nomeProdotto = nomeProd;
                                    this.quantitaTotale = quanTot;
                                    this.ricavo = ricavo;
                                    this.ricavoTotale = ricTot;
                                    this.contributo = contr;
                                }

    @Override
    public String toString() {
        return "nome zona: " + this.zona + ",       "
              + "codice prodotto: " + this.codiceProdotto + ",      "
              + "nome prodotto: " + this.nomeProdotto + ",      "
              + "quantità totale venduta: " + this.quantitaTotale + ",      "
              + "ricavo prodotto: " + this.ricavo + ",      "
              + "ricavo totale zona: " + this.ricavoTotale + ",     "
              + "contributo prodotto in %: " + this.contributo;
    }

    public static final class DAO {
        public static List<ClassificaProdotto> list(Connection connection) {
            var classifiche = new ArrayList<ClassificaProdotto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_CLASSIFICA_PRODOTTI.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var zona = resultSet.getString("zona");
                    final var codice = resultSet.getString("codice_prodotto");
                    final var nome = resultSet.getString("prodotto_nome");
                    final var quantita = resultSet.getInt("quantita_tot");
                    final var ricavo = resultSet.getDouble("ricavo");
                    final var ricavoZona = resultSet.getDouble("ricavo_totale_zona");
                    final var contributo = resultSet.getDouble("contributo_prodotto_percentuale");

                    final ClassificaProdotto classifica = new ClassificaProdotto(zona, codice, nome, quantita,
                     ricavo, ricavoZona, contributo);
                    classifiche.add(classifica);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return classifiche;            
        }
    }
}
