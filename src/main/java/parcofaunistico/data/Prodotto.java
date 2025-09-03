package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class Prodotto {
    final String codiceProdotto;
    final String nome;
    final String descrizione;
    final double prezzoUnitario;

    public Prodotto(final String codice, final String nome, final String descrizione, final double prezzo) {
        this.codiceProdotto = codice;
        this.nome = nome;
        this.descrizione = descrizione;
        this.prezzoUnitario = prezzo;
    }

    @Override
    public String toString() {
        return this.codiceProdotto + ", "
              + this.nome + ", "
              + this.descrizione + ", "
              + prezzoUnitario;
    }

    public static final class DAO {
        public static List<Prodotto> list(Connection connection) {
            var prodotti = new ArrayList<Prodotto>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_PRODOTTO.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_prodotto");
                    final var nome = resultSet.getString("nome");
                    final var descrizione = resultSet.getString("descrizione");
                    final var prezzo = resultSet.getDouble("prezzo_unitario");
                    final var prodotto = new Prodotto(codice, nome, descrizione, prezzo);
                    prodotti.add(prodotto);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return prodotti;            
        }

        public static boolean check(Connection connection, String codiceProdotto) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceProdotto));
                    var resultSet = preparedStatement.executeQuery();) {
                if (resultSet.next()) {
                    return true;
                } else {
                    return false;
                }
            } catch (Exception e) {
                throw new DAOException(e);
            }
        }

        public static String getName(final Connection connection, final String codiceProdotto) {
            String name;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceProdotto));
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    name = resultSet.getString("nome");
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return name;
        }

        public static double getPrezzo(final Connection connection, final String codiceProdotto) {
            Double prezzo;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceProdotto));
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    prezzo = resultSet.getDouble("prezzo_unitario");
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return prezzo;
        }

         private static String getCheckQuery(String codiceProdotto) {
            return Queries.CHECK_PRODOTTO.get() + "\'" + codiceProdotto + "\'";
        }
    }
}
