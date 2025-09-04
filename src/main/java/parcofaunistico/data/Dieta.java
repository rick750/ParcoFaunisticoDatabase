package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Dieta {
    final String alimento;
    final int numeroPasti;

    public Dieta(final String alimento, final int numPasti) {
        this.alimento = alimento;
        this.numeroPasti = numPasti;
    }

    @Override
    public String toString() {
        return this.alimento + ", "
               + this.numeroPasti;
    }

    public static final class DAO {
        public static List<Dieta> list(Connection connection) {
            var diete = new ArrayList<Dieta>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_DIETA.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var alimento = resultSet.getString("alimento");
                    final var numPasti = resultSet.getInt("numero_pasti");
                    final var dieta = new Dieta(alimento, numPasti);
                    diete.add(dieta);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return diete;            
        }

        public static boolean check(Connection connection, String alimento) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(alimento));
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
            final String alimento = textfields.get(Parametri.ALIMENTO);
            final int numPasti = Integer.parseInt(textfields.get(Parametri.NUMERO_PASTI));


            final String queryDieta = """
                        INSERT INTO DIETA(alimento, numero_pasti)
                        VALUES (?, ?)
                    """;

            try (PreparedStatement stmtDieta = connection.prepareStatement(queryDieta)) {
                stmtDieta.setString(1, alimento);
                stmtDieta.setInt(2, numPasti);
                int righeInserite = stmtDieta.executeUpdate();
                System.out.println("Righe inserite dentro Dieta: " + righeInserite);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }
    }

    private static String getCheckQuery(String alimento) {
            return Queries.CHECK_DIETA.get() + "\'" + alimento + "\'";
        }
}
