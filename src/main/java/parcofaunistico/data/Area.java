package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

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
    }
}
