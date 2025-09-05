package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Area {
    private final String nome;
    private final Time orarioApertura;
    private final Time orarioChiusura;
    private final String habitat;
    private final String zonaAmministrativa;
    private final String zonaRicreativa;

    public Area(final String nome, final Time orchius, final Time oraper,
                final String habitat, final String zAmm, final String zRic) {
                    this.nome = nome;
                    this.orarioApertura = oraper;
                    this.orarioChiusura = orchius;
                    this.habitat = habitat;
                    this.zonaAmministrativa = zAmm;
                    this.zonaRicreativa = zRic;
                }

    @Override
    public String toString() {
        return this.nome + ",       "
             + this.orarioApertura + ",     "
             + this.orarioChiusura + ",      "
             + (this.zonaAmministrativa == "" ? "" : this.zonaAmministrativa) 
             + (this.zonaRicreativa == "" ? "" : this.zonaRicreativa)
             + (this.habitat == "" ? "" : this.habitat);
    }

    public static final class DAO {
        public static List<Area> list(Connection connection) {
            var aree = new ArrayList<Area>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_AREE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var orarioApertura = resultSet.getTime("orario_apertura");
                    final var orarioChiusura = resultSet.getTime("orario_chiusura");
                    String temp;

                    String zonaAmministrativa;
                    temp = resultSet.getString(4);
                    if (temp == null) {
                        zonaAmministrativa = "";
                    } else {
                        zonaAmministrativa = "Zona Amministrativa";
                    }

                    temp = resultSet.getString(5);
                    String zonaRicreativa;
                    if (temp == null) {
                        zonaRicreativa = "";
                    } else {
                        zonaRicreativa = " Zona Ricreativa";
                    }

                    temp = resultSet.getString(6);
                    String habitat;
                    if (temp == null) {
                        habitat = "";
                    } else {
                        habitat = "Habitat";
                    }

                    final Area area = new Area(nome, orarioChiusura, orarioApertura, habitat, zonaAmministrativa, zonaRicreativa);
                    aree.add(area);
                }
               
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return aree;            
        }

        public static boolean check(Connection connection, String nomeArea) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(nomeArea));
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

        public static boolean insert(final Connection connection, final String nomeZona, final Parametri tipoZona, final Map<Parametri, String> fields) {
            
             final String insertQuery = """
                        INSERT AREA(nome, orario_apertura, orario_chiusura, ZONA_AMMINISTRATIVA, ZONA_RICREATIVA, HABITAT)
                        VALUES (?, ?, ?, ?, ?, ?)
                    """;
            
            try (PreparedStatement stmtZona = connection.prepareStatement(insertQuery)) {
                stmtZona.setString(1, nomeZona);
                stmtZona.setTime(2, Time.valueOf(fields.get(Parametri.ORARIO_INIZIO)));
                stmtZona.setTime(3, Time.valueOf(fields.get(Parametri.ORARIO_FINE)));
                switch(tipoZona) {
                    case NOME_ZONA_AMMINISTRATIVA -> {
                        stmtZona.setString(4, nomeZona);
                        stmtZona.setString(5, null);
                        stmtZona.setString(6, null);
                    }
                    case NOME_ZONA_RICREATIVA -> {
                        stmtZona.setString(4, null);
                        stmtZona.setString(5, nomeZona);
                        stmtZona.setString(6, null);
                    }
                    case NOME_HABITAT -> {
                        stmtZona.setString(4, null);
                        stmtZona.setString(5, null);
                        stmtZona.setString(6, nomeZona);
                    }

                    default -> {return false;}
                }
                
                int righe = stmtZona.executeUpdate();
                System.out.println("Ho inserito " + righe + "dentro area");
                return true;
            } catch (final Exception e) {
                e.printStackTrace();
                return false;
            }
        }

        private static String getCheckQuery(String nomeArea) {
            return Queries.CHECK_AREA.get() + "\'" + nomeArea + "\'";
        }
    }
}
