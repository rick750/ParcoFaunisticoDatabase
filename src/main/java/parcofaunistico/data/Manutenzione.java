package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Manutenzione {
    private final String nomeArea;
    private final Date inizio;
    private final Date fine;

    public Manutenzione(final String nomeArea, final Date inizio, final Date fine) {
        this.nomeArea = nomeArea;
        this.inizio = inizio;
        this.fine = fine;
    }

    @Override
    public String toString() {
        return "Nome Area: " + this.nomeArea + ",       "
        + "Data inizio: " + this.inizio + ",       "
        + "Data fine: " + this.fine;
    }

    public static final class DAO {
        public static List<Manutenzione> list(Connection connection) {
            var manutenzioni = new ArrayList<Manutenzione>();
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_MANUTENZIONE.get());
                    var resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    var nomeArea = resultSet.getString("nome");
                    var dataInizio = resultSet.getDate("data_inizio");
                    var dataFine = resultSet.getDate("data_fine");

                    var manutezione = new Manutenzione(nomeArea, dataInizio, dataFine);
                    manutenzioni.add(manutezione);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return manutenzioni;
        }

        public static boolean check(Connection connection, String nomeZona) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(nomeZona));
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

        public static boolean insert(Connection connection, Map<Parametri, String> textfields) {
            final String nomeZona = textfields.get(Parametri.NOME_ZONA);
            final Date dataInizio = Date.valueOf(textfields.get(Parametri.DATA_INIZIO));
            final Date dataFine = Date.valueOf(textfields.get(Parametri.DATA_FINE));

            // Primo inserimento nella tabella persone
            final String queryManutenzione = """
                        INSERT INTO MANUTENZIONE(nome, data_inizio, data_fine)
                        VALUES (?, ?, ?)
                    """;

            try (PreparedStatement stmTManutenzione = connection.prepareStatement(queryManutenzione)) {
                stmTManutenzione.setString(1, nomeZona);
                stmTManutenzione.setDate(2, dataInizio);
                stmTManutenzione.setDate(3, dataFine);
                int righeInserite = stmTManutenzione.executeUpdate();
                System.out.println("Righe inserite in manutenzione: " + righeInserite);
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        public static boolean update(Connection connection, Map<Parametri, String> textfields) {
             final String nomeZona = textfields.get(Parametri.NOME_ZONA);
            final Date dataInizio = Date.valueOf(textfields.get(Parametri.DATA_INIZIO));
            final Date dataFine = Date.valueOf(textfields.get(Parametri.DATA_FINE));
            final List<Object> valori = new ArrayList<>();
            valori.addLast(dataInizio);
            valori.addLast(dataFine);
            final String query = "UPDATE MANUTENZIONE SET data_inizio = ?, data_fine = ? WHERE nome = " + "\'" + nomeZona + "\'";

            try(PreparedStatement stmt = connection.prepareStatement(query.toString());) {
                for (int i = 0; i < valori.size(); i++) {
                    stmt.setObject(i + 1, valori.get(i));
                }
                int righeModificate = stmt.executeUpdate();
                System.out.println("Ho modificato " + righeModificate + " dentro Manutenzione");
                System.out.println("Query: " + query);
                System.out.println("Valori: " + valori);
                return true;
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Query: " + query);
                System.out.println("Valori: " + valori);
                return false;
            }
        }

        
        private static String getCheckQuery(String nomeZona) {
            return """
                    SELECT *
                    FROM MANUTENZIONE
                    WHERE nome = """ + "\'" + nomeZona + "\'";
        }
    }
}
