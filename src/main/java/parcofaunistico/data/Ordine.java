package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

         private static String getQuery(final String codiceFiscale) {
            return Queries.SHOW_VISITATORE_ORDINI.get() + "\'"+codiceFiscale+ "\'";
        }
    }
}
