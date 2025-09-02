package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

import javax.swing.JTextField;

public class Biglietto {
    private final String codiceBiglietto;
    private final Date dataValidita;
    private final String codFiscale;
    private final String codGruppo;
    private final String codTransazione;
    private final Double prezzoBase;
    private final Double prezzoEffettivo;
    private final String codPercorso;
    public Biglietto(final String codiceBiglietto, final Date dataValidita, final String codFiscale, final String codGruppo,
            final String codTransazione, final Double prezzoBase, final Double prezzoEffettivo, final String codPercorso) {
        this.codiceBiglietto = codiceBiglietto;
        this.dataValidita = dataValidita;
        this.codFiscale = codFiscale;
        this.codGruppo = codGruppo;
        this.codTransazione = codTransazione;
        this.prezzoBase = prezzoBase;
        this.prezzoEffettivo = prezzoEffettivo;
        this.codPercorso = codPercorso;
    }

    public static final class DAO {
        public static String getLast(Connection connection) {
            String codiceBiglietto;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ULTIMO_BIGLIETTO.get());
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    codiceBiglietto = resultSet.getString("codice_biglietto");
                
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return codiceBiglietto;
        }

        public static boolean insert(Connection connection, Map<Parametri, String> dati) {
            final String codiceBiglietto = dati.get(Parametri.CODICE_BIGLIETTO);
            final Date dataValidita = Date.valueOf(dati.get(Parametri.DATA_VALIDITA));
            final String codFiscale = dati.get(Parametri.CODICE_FISCALE);
            final String codGruppo = dati.get(Parametri.CODICE_GRUPPO);
            final String codTransazione = dati.get(Parametri.CODICE_TRANSAZIONE);
            final Double prezzoBase = Double.parseDouble(dati.get(Parametri.PREZZO_BASE));
            final Double prezzoEffettivo = Double.parseDouble(dati.get(Parametri.PREZZO_EFFETTIVO));
            final String codicePercorso = dati.get(Parametri.CODICE_PERCORSO);

            final String queryBiglietto = """
                        INSERT INTO BIGLIETTO(codice_biglietto, data_validita, codice_fiscale, codice_gruppo,
                         codice_transazione, prezzo_base, prezzo_effettivo, codice_percorso)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtBiglietto = connection.prepareStatement(queryBiglietto)) {
                stmtBiglietto.setString(1, codiceBiglietto);
                stmtBiglietto.setDate(2, dataValidita);
                stmtBiglietto.setString(3, codFiscale);
                stmtBiglietto.setString(4, codGruppo);
                stmtBiglietto.setString(5, codTransazione);
                stmtBiglietto.setDouble(6, prezzoBase);
                stmtBiglietto.setDouble(7, prezzoEffettivo);
                stmtBiglietto.setString(8, codicePercorso);
                int righeInserite = stmtBiglietto.executeUpdate();
                System.out.println("Righe inserite: " + righeInserite);

                
            } catch (Exception e) {
                System.out.println("Problemi nell'inserimento di BIGLIETTO");
                e.printStackTrace();
                return false;
            }

            return true;
        }
    }
    
}
