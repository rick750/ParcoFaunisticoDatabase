package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;

public class GiornataLavorativa {
    private final String codiceDipendente;
    private final Date data;
    private final String giorno;
    private final Time orarioInizio;
    private final Time orarioFine;
    
    public GiornataLavorativa(final String codice, final Date data, final String giorno,
                                 final Time inizio, final Time fine) {
        this.codiceDipendente = codice;
        this.data = data;
        this.giorno = giorno;
        this.orarioInizio = inizio;
        this.orarioFine = fine;
    }

    @Override
    public String toString() {
        return "Data: " + this.data + ",        "
                + "Giorno: " + this.giorno + ",     "
                + "Orario Inizio: " + this.orarioInizio + ",        "
                + "Orario Fine: " + this.orarioFine + ",        ";
    }

    public static final class DAO {
        public static List<GiornataLavorativa> list(final Connection connection, final String codiceFiscale) {
            final var acquisti = new ArrayList<GiornataLavorativa>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, getQuery(codiceFiscale));
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var codice = resultSet.getString("codice_fiscale");
                    final var data = resultSet.getDate("data");
                    final var giorno = resultSet.getString("giorno");
                    final var inizio = resultSet.getTime("orario_inizio");
                    final var fine = resultSet.getTime("orario_fine");

                    final GiornataLavorativa acquisto = new GiornataLavorativa(codice, data, giorno, inizio, fine);
                    acquisti.add(acquisto);
                }
               
            } catch (final SQLException e) {
                throw new DAOException(e);
            }
            return acquisti;            
        }

        private static String getQuery(final String codiceFiscale) {
            return Queries.SHOW_GIORNATA_LAVORATIVA.get() + "\'"+codiceFiscale+ "\'";
        }
    }
}
