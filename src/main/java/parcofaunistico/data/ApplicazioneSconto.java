package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicazioneSconto {
    private final String codiceSconto;
    private final int applicazioni;
    private final String tipologia;

    public ApplicazioneSconto(final String codice, final int applicazioni, final String tipologia) {
        this.codiceSconto = codice;
        this.applicazioni = applicazioni;
        this.tipologia = tipologia;
    }

    @Override
    public String toString() {
        return "codice sconto: " + this.codiceSconto + ",       " 
              + "numero applicazioni: " + this.applicazioni + ",        "
              + "tipologia: " + this.tipologia;
    }

    public static final class DAO {
        public static List<ApplicazioneSconto> list(Connection connection) {
            var applicazioni = new ArrayList<ApplicazioneSconto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_APPLICAZIONI_SCONTI.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("codice_sconto");
                    final var num = resultSet.getInt("numApplicazioni");
                    final var tipologia = resultSet.getString("tipologia"); 

                    final ApplicazioneSconto app = new ApplicazioneSconto(nome, num, tipologia);
                    applicazioni.add(app);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return applicazioni;            
        }
    }
}
