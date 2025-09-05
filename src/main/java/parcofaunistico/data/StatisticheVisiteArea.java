package parcofaunistico.data;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StatisticheVisiteArea {
    private final String area;
    private final double mediaVisitatori;

    public StatisticheVisiteArea(final String area, final double mediaVisitatori) {
        this.area = area;
        this.mediaVisitatori = mediaVisitatori;
    }
    
    @Override
    public String toString() {
        return "area: " + this.area + ", media visitatori: " + this.mediaVisitatori;
    }

    public static final class DAO {
        public static List<StatisticheVisiteArea> list(Connection connection) {
            var result = new ArrayList<StatisticheVisiteArea>();
            try (
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_STATISTICHE_VISITE_AREA.get());
                var rs = preparedStatement.executeQuery();
            ) {
                while (rs.next()) {
                    final var area = rs.getString("area");
                    final var media = rs.getDouble("media_visitatori");
                    result.add(new StatisticheVisiteArea(area, media));
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return result;
        }
    }
}
