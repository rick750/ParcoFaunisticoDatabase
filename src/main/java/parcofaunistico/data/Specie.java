package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Specie {
    final String nomeScientifico;
    final String nomeComune;
    final String abitudini;
    final int numeroEsemplari;

    public Specie(final String nomeS, final String nomeC, final String ab, final int num) {
        this.nomeScientifico = nomeS;
        this.nomeComune = nomeC;
        this.abitudini = ab;
        this.numeroEsemplari = num;
    }

    @Override
    public String toString() {
        return "Nome Scientifico: " + nomeScientifico + ",      "
                + "Nome Comune: " + nomeComune + ",       "
                + "Abitudini: " + abitudini + ",    "
                + "Numero esemplari: " + numeroEsemplari;
    }

    public static final class DAO {
        public static List<Specie> list(Connection connection) {
            var specie = new ArrayList<Specie>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_SPECIE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nomeScientifico = resultSet.getString("nome_scientifico");
                    final var nomeComune = resultSet.getString("nome_comune");
                    final var abitudini = resultSet.getString("abitudini");
                    final var numeroEsemplari = resultSet.getInt("numero_esemplari");
                    final var s = new Specie(nomeScientifico, nomeComune, abitudini, numeroEsemplari);
                    specie.add(s);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return specie;            
        }
    }
}
