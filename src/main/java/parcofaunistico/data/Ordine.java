package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Ordine {
    private final String codiceOrdine;
    private final int quantita;
    private final Date data;
    private final String codiceProdotto;
    private final String nomeProdotto;
    private final String descrizioneProdotto;
    private final double prezzoProdotto;
    private final Double prezzoOrdine;

    public Ordine(final String codice, final int quantita, final Date data, final String codiceProdotto,
                    final String nomeProdotto, final String descrizioneProdotto, final double prezzoProd, final Double prezzoOrd) {
        this.codiceOrdine = codice;
        this.quantita = quantita;
        this.data = data;
        this.codiceProdotto = codiceProdotto;
        this.nomeProdotto = nomeProdotto;
        this.descrizioneProdotto = descrizioneProdotto;
        this.prezzoProdotto = prezzoProd;
        this.prezzoOrdine = prezzoOrd;
    }

    @Override
    public String toString() {
        return this.codiceOrdine + ",       "
             + this.quantita + ",       "
             + this.data + ",       "
             + this.codiceProdotto + ",         "
             + this.nomeProdotto + ",       "
             + this.descrizioneProdotto + ",        "
             + this.prezzoProdotto + "€" + ",         "
             + this.prezzoOrdine + "€" + ",       ";
    }

     public static final class DAO {
        public static List<Ordine> list(Connection connection) {
            var ordini = new ArrayList<Ordine>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ORDINE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_ordine");
                    final var quantita = resultSet.getInt("quantita_acquistata");
                    final var data = resultSet.getDate("data");
                    final var codiceProdotto = resultSet.getString("codice_prodotto");
                    final var nomeProdotto = resultSet.getString("nomeProd");
                    final var descrizioneProdotto = resultSet.getString("descrizione");
                    final var prezzoProd = resultSet.getDouble("prezzo_unitario");
                    final var prezzoOrd = resultSet.getDouble("PrezzoOrdine");
                    final var ordine = new Ordine(codice, quantita, data, codiceProdotto, 
                                                    nomeProdotto, descrizioneProdotto, prezzoProd, prezzoOrd);
                    ordini.add(ordine);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return ordini;            
        }

        public static boolean insert(Connection connection, Map<Parametri, String> textfields) {
            final String codiceProdotto = textfields.get(Parametri.CODICE_PRODOTTO);
            final String codiceOrdine = textfields.get(Parametri.CODICE_ORDINE);
            final int quantita = Integer.parseInt(textfields.get(Parametri.QUANTITA));
            final Date data = Date.valueOf(textfields.get(Parametri.DATA_EFFETTUAZIONE));
            final String nomeZona = String.valueOf(textfields.get(Parametri.NOME_ZONA));
            final String codiceFiscale = String.valueOf(textfields.get(Parametri.CODICE_FISCALE));

            final String queryOrdine = """
                        INSERT INTO ORDINE(codice_prodotto, codice_ordine, quantita_acquistata, data, nome)
                        VALUES (?, ?, ?, ?, ?)
                    """;
            final String queryRichiesta = """
                        INSERT INTO RICHIESTA(codice_fiscale, codice_prodotto, codice_ordine)
                        VALUES (?, ?, ?)
                    """; 

            try (PreparedStatement stmtOrdine = connection.prepareStatement(queryOrdine)) {
                stmtOrdine.setString(1, codiceProdotto);
                stmtOrdine.setString(2, codiceOrdine);
                stmtOrdine.setInt(3, quantita);
                stmtOrdine.setDate(4, data);
                stmtOrdine.setString(5, nomeZona);
                stmtOrdine.executeUpdate();
                try (PreparedStatement stmtRichiesta = connection.prepareStatement(queryRichiesta)){
                    stmtRichiesta.setString(1, codiceFiscale);
                    stmtRichiesta.setString(2, codiceProdotto);
                    stmtRichiesta.setString(3, codiceOrdine);
                    stmtRichiesta.executeUpdate();
                } catch (Exception e) {
                    return false;
                }
            } catch (Exception e) {
                return false;
            }
            return true;
        }

        public static List<Ordine> getVisitatoreOrdini(final Connection connection, final String codiceFiscale) {
            var ordini = new ArrayList<Ordine>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, getQuery(codiceFiscale));
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_ordine");
                    final var quantita = resultSet.getInt("quantita_acquistata");
                    final var data = resultSet.getDate("data");
                    final var codiceProdotto = resultSet.getString("codice_prodotto");
                    final var nomeProdotto = resultSet.getString("nomeProd");
                    final var descrizioneProdotto = resultSet.getString("descrizione");
                    final var prezzoProd = resultSet.getDouble("prezzo_unitario");
                    final var prezzoOrd = resultSet.getDouble("PrezzoOrdine");
                    final var ordine = new Ordine(codice, quantita, data, codiceProdotto,
                                                     nomeProdotto, descrizioneProdotto, prezzoProd, prezzoOrd);
                    ordini.add(ordine);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return ordini;   
        }

        public static String getLast(Connection connection) {
            String codiceOrdine;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ULTIMO_ORDINE.get());
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    codiceOrdine = resultSet.getString("codice_ordine");
                
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return codiceOrdine;
        }

         private static String getQuery(final String codiceFiscale) {
            return Queries.SHOW_VISITATORE_ORDINI.get() + "\'"+codiceFiscale+ "\'";
        }
    }
}
