package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Time;

public class Percorso {
    final String codice;
    final int numZone;
    final Time durata;

    public Percorso(String codice, int numZone, Time durata) {
        this.codice = codice;
        this.numZone = numZone;
        this.durata = durata;
    }

    public static final class DAO {
        public static boolean check (final Connection connection, final String codice) {
             try (
                    var preparedStatement = DAOUtils.prepare(connection, getQuery(codice));
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

        private static String getQuery(final String codice) {
            return Queries.CHECK_PERCORSO.get() + "\'" + codice + "\'";
        }
    }
    
}
