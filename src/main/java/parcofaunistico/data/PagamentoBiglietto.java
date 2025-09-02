package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class PagamentoBiglietto {
    private final String codiceTransazione;
    private final String codiceFiscale;
    private final String codiceGruppo;
    private final Date dataAcquisto;
    private final String codiceSconto;
    private final String nomeZona;


    public PagamentoBiglietto(final String transaz, final String codiceFiscale, final String codiceGruppo, final Date dataAcquisto,
    final String codiceSconto, final String nomeZona) {
        this.codiceTransazione = transaz;
        this.codiceFiscale = codiceFiscale;
        this.codiceGruppo = codiceGruppo;
        this.dataAcquisto = dataAcquisto;
        this.codiceSconto = codiceSconto;
        this.nomeZona = nomeZona;
    }

    public static final class DAO {
        public static String getLast(final Connection connection) {
            String codiceTransazione;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ULTIMO_PAGAMENTO.get());
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    codiceTransazione = resultSet.getString("codice_transazione");
                
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return codiceTransazione;
        }

        public static boolean insert(final Connection connection, final Map<Parametri, String> dati) {
            final String codTransazione = dati.get(Parametri.CODICE_TRANSAZIONE);
            final String codFiscale = dati.get(Parametri.CODICE_FISCALE);
            final String codGruppo = dati.get(Parametri.CODICE_GRUPPO);
            final Date dataEffettuazione = Date.valueOf(dati.get(Parametri.DATA_EFFETTUAZIONE));
            final String codSconto = dati.get(Parametri.CODICE_SCONTO);
            final String nomeZona = dati.get(Parametri.NOME_ZONA);

            final String queryPagamento = """
                        INSERT INTO PAGAMENTO_VISITA(codice_transazione, codice_fiscale, codice_gruppo,
                         data_effettuazione, codice_sconto, nome)
                        VALUES (?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtPagamento = connection.prepareStatement(queryPagamento)) {
                stmtPagamento.setString(1, codTransazione);
                stmtPagamento.setString(2, codFiscale);
                stmtPagamento.setString(3, codGruppo);
                stmtPagamento.setDate(4, dataEffettuazione);
                stmtPagamento.setString(5, codSconto);
                stmtPagamento.setString(6, nomeZona);
                int righeInserite = stmtPagamento.executeUpdate();
                System.out.println("Righe inserite: " + righeInserite);
    
            } catch (final Exception e) {
                System.out.println("Problemi nell'inserimento di PAGAMENTO_VISITA");
                e.printStackTrace();
                return false;
            }
            return true;
        }
    }

}
