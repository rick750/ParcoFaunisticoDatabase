package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class ZonaRicreativa {
    private final String nome;
    private final Time orarioApertura;
    private final Time orarioChiusura;

    public ZonaRicreativa(final String nome, final Time orarioApertura, final Time orarioChiusura) {
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
        public static List<ZonaRicreativa> list(Connection connection) {
            var zoneRicreative = new ArrayList<ZonaRicreativa>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ZONA_RICREATIVA.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var orarioApertura = resultSet.getTime("orario_apertura");
                    final var orarioChiusura = resultSet.getTime("orario_chiusura");
                    final var zonaRic = new ZonaRicreativa(nome, orarioApertura, orarioChiusura);
                    zoneRicreative.add(zonaRic);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return zoneRicreative;            
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
                    INSERT INTO ZONA_RICREATIVA(nome)
                    VALUES (?)
                    """;
            
            try (PreparedStatement stmtZona = connection.prepareStatement(query)) {
                stmtZona.setString(1, nomeZona);
                int righe = stmtZona.executeUpdate();
                System.out.println("Ho inserito " + righe + "dentro zona ricreativa");
            } catch (final Exception e) {
                return false;
            }
            return true; 
        }


        private static String getLastQuery() {
            return """
                        SELECT *
                        FROM ZONA_RICREATIVA
                        ORDER BY nome DESC
                        LIMIT 1;
                    """;
        }

        private static String getCheckQuery(String nomeZona) {
            return Queries.CHECK_ZONA_RICREATIVA.get() + "\'" + nomeZona + "\'";
        }

    }
}
