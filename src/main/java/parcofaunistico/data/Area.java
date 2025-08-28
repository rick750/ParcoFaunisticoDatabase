package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Area {
    private final String nome;
    private final Time orarioApertura;
    private final Time orarioChiusura;
    private final Optional<String> habitat;
    private final Optional<String> zonaAmministrativa;
    private final Optional<String> zonaRicreativa;

    public Area(final String nome, final Time orchius, final Time oraper,
                final Optional<String> habitat, final Optional<String> zAmm, final Optional<String> zRic) {
                    this.nome = nome;
                    this.orarioApertura = oraper;
                    this.orarioChiusura = orchius;
                    this.habitat = habitat;
                    this.zonaAmministrativa = zAmm;
                    this.zonaRicreativa = zRic;
                }

    @Override
    public String toString() {
        return this.nome + ", "
             + this.orarioApertura + ", "
             + this.orarioChiusura + ", "
             + this.zonaAmministrativa + ", "
             + this.zonaRicreativa + ", "
             + this.habitat;
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
                    var zonaAmministrativa = Optional.of(resultSet.getString(3));
                    if (zonaAmministrativa == null) {
                        zonaAmministrativa = Optional.empty();
                    }
                    var zonaRicreativa = Optional.of(resultSet.getString(4));
                    if (zonaRicreativa == null) {
                        zonaRicreativa = Optional.empty();
                    }
                    var habitat = Optional.of(resultSet.getString(5));
                    if (habitat == null) {
                        habitat = Optional.empty();
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
