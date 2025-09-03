package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Time;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.TextStyle;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GiornataLavorativa {
    private final Date data;
    private final String giorno;
    private final Time orarioInizio;
    private final Time orarioFine;
    
    public GiornataLavorativa(final String codice, final Date data, final String giorno,
                                 final Time inizio, final Time fine) {
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

        public static boolean insert(Connection connection, final String codiceFiscale) {
            final LocalDate oggi = LocalDate.now();
            final Date data = Date.valueOf(oggi);
            final Time orario_inizio = Time.valueOf(LocalTime.now());
            final Time orario_fine = Time.valueOf(LocalTime.of(18, 0, 0));
            
            final DayOfWeek giorno = oggi.getDayOfWeek();
            final String nomeGiorno = giorno.getDisplayName(TextStyle.FULL, Locale.ITALIAN);


            final String queryGiornataLavorativa = """
                        INSERT INTO GIORNATA_LAVORATIVA(codice_fiscale, data, giorno, orario_inizio, orario_fine)
                        VALUES (?, ?, ?, ?, ?)
                    """;
            try (PreparedStatement stmtGiornataLavorativa = connection.prepareStatement(queryGiornataLavorativa)) {
                stmtGiornataLavorativa.setString(1, codiceFiscale);
                stmtGiornataLavorativa.setDate(2, data);
                stmtGiornataLavorativa.setString(3, nomeGiorno);
                stmtGiornataLavorativa.setTime(4, orario_inizio);
                stmtGiornataLavorativa.setTime(5, orario_fine);
                int righeInserite = stmtGiornataLavorativa.executeUpdate();
                System.out.println("Righe inserite in giornate lavorative: " + righeInserite);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }
            return true;
        }

        public static boolean check(Connection connection, String codiceFiscale, String data) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceFiscale, data));
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

        private static String getQuery(final String codiceFiscale) {
            return Queries.SHOW_GIORNATA_LAVORATIVA.get() + "\'"+codiceFiscale+ "\'";
        }

         private static String getCheckQuery(final String codiceFiscale, final String data) {
            return "SELECT * FROM GIORNATA_LAVORATIVA WHERE codice_fiscale = " + "\'"+codiceFiscale+ "\'" +
            " AND data = " + "\'" + data + "\'";
        }
    }
}
