package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RendimentoGiornaliero {
    final String nome;
    final Date data;
    final int numeroVendite;
    final double fatturato;

    public RendimentoGiornaliero(final String nome, final Date data, final int numVendite, final Double fatturato) {
        this.nome = nome;
        this.data = data;
        this.numeroVendite = numVendite;
        this.fatturato = fatturato;
    }

    @Override
    public String toString() {
        return "Data: " + this.data + ", " 
                + "Nome: " + this.nome + ", " 
                + "Numero Vendite: " + this.numeroVendite + ", "
                + "Fatturato: " + this.fatturato;
    }

    public static final class DAO {
        public static List<RendimentoGiornaliero> list(Connection connection) {
            var rendimenti = new ArrayList<RendimentoGiornaliero>();
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_RENDIMENTO_GIORNALIERO.get());
                    var resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var data = resultSet.getDate("data");
                    final var numVendite = resultSet.getInt("numero_vendite");
                    final var fatturato = resultSet.getDouble("fatturato");
                    final var rendimento = new RendimentoGiornaliero(nome, data, numVendite, fatturato);
                    rendimenti.add(rendimento);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return rendimenti;
        }

        public static boolean insert(Connection connection, String nomeArea, Date data) {
            final String query = """
                        INSERT INTO RENDIMENTO_GIORNALIERO (nome, data, numero_vendite, fatturato)
                        SELECT * FROM (
                            SELECT ? AS nome,
                                   ? AS data,
                                   COALESCE(SUM(o.quantita_acquistata), 0) AS numero_vendite,
                                   COALESCE(SUM(o.quantita_acquistata * p.prezzo_unitario), 0) AS fatturato
                            FROM ORDINE o
                            JOIN PRODOTTO p
                                ON o.codice_prodotto = p.codice_prodotto
                            WHERE o.nome = ?
                              AND o.data = ?
                        ) AS new_val
                        ON DUPLICATE KEY UPDATE
                            numero_vendite = new_val.numero_vendite,
                            fatturato = new_val.fatturato;
                    """;

            try (PreparedStatement ps = connection.prepareStatement(query)) {
                java.sql.Date sqlDate = new java.sql.Date(data.getTime());
                ps.setString(1, nomeArea);
                ps.setDate(2, sqlDate);
                ps.setString(3, nomeArea);
                ps.setDate(4, sqlDate);

                int affectedRows = ps.executeUpdate();
                return affectedRows > 0;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }

    }
}
