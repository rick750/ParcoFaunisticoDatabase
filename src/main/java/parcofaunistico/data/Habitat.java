package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class Habitat {
    private final String nome;
    private final Time orarioApertura;
    private final Time orarioChiusura;

    public Habitat(final String nome, final Time orarioApertura, final Time orarioChiusura) {
        this.nome = nome;
        this.orarioApertura = orarioApertura;
        this.orarioChiusura = orarioChiusura;
    }


    @Override
    public String toString() {
        return this.nome + ", "
              + this.orarioApertura + ", "
              + this.orarioChiusura;
    }

    public static final class DAO {
        public static List<Habitat> list(Connection connection) {
            var habitat = new ArrayList<Habitat>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_HABITAT.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var orarioApertura = resultSet.getTime("orario_apertura");
                    final var orarioChiusura = resultSet.getTime("orario_chiusura");
                    final var h = new Habitat(nome, orarioApertura, orarioChiusura);
                    habitat.add(h);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return habitat;            
        }

        public static String getLast(final Connection connection) {
            String nomeZona;
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getLastQuery());
                    var resultSet = preparedStatement.executeQuery();) {
                    resultSet.next();
                    nomeZona = resultSet.getString("nome");
                
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return nomeZona;
        }

        public static boolean insert(final Connection connection, final String nomeZona) {
            
            final String query = """
                    INSERT INTO HABITAT(nome)
                    VALUES (?)
                    """;
            
            try (PreparedStatement stmtZona = connection.prepareStatement(query)) {
                stmtZona.setString(1, nomeZona);
                stmtZona.executeUpdate();
            } catch (final Exception e) {
                return false;
            }
            return true; 
        }


        private static String getLastQuery() {
            return """
                        SELECT *
                        FROM HABITAT
                        ORDER BY nome DESC
                        LIMIT 1;
                    """;
        }
    }
}
