package parcofaunistico.data;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.swing.JTextField;

public class PagamentoBiglietto {
    private final String codiceFiscale;
    private final String codiceGruppo;
    private final Date dataAcquisto;


    public PagamentoBiglietto(String codiceFiscale, String codiceGruppo, Date dataAcquisto) {
        this.codiceFiscale = codiceFiscale;
        this.codiceGruppo = codiceGruppo;
        this.dataAcquisto = dataAcquisto;
    }

    @Override
    public String toString() {
        return this.codiceFiscale;
    }

    public String getCodiceFiscale() {
        return codiceFiscale;
    }

    public static final class DAO {
        public static List<Visitatore> list(Connection connection) {
            var persone = new ArrayList<Visitatore>();
            try (
                    var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_VISITATORE.get());
                    var resultSet = preparedStatement.executeQuery();) {
                while (resultSet.next()) {
                    var codice_fiscale = resultSet.getString("codice_fiscale");
                    var nome = resultSet.getString("nome");
                    var cognome = resultSet.getString("cognome");
                    var eta = resultSet.getInt("eta");
                    var indirizzo = resultSet.getString("indirizzo");
                    var telefono = resultSet.getString("telefono");
                    var email = resultSet.getString("email");
                    var persona = new Visitatore(codice_fiscale, nome, cognome, eta, indirizzo, telefono, email);
                    persone.add(persona);
                }
            } catch (SQLException e) {
                throw new DAOException(e);
            }
            return persone;
        }

        public static boolean check(Connection connection, String codiceFiscale) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(codiceFiscale));
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

        public static boolean insert(Connection connection, Map<Parametri, JTextField> textfields) {
            final String codiceFiscale = textfields.get(Parametri.CODICE_FISCALE).getText();
            final String nome = textfields.get(Parametri.NOME).getText();
            final String cognome = textfields.get(Parametri.COGNOME).getText();
            final int eta = Integer.parseInt(textfields.get(Parametri.ETA).getText());
            final String indirizzo = textfields.get(Parametri.INDIRIZZO).getText();
            final String telefono = textfields.get(Parametri.TELEFONO).getText();
            final String email = textfields.get(Parametri.EMAIL).getText();

            // Primo inserimento nella tabella persone
            final String queryPersone = """
                        INSERT INTO PERSONA(codice_fiscale, nome, cognome, eta, indirizzo, telefono, email)
                        VALUES (?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtPersone = connection.prepareStatement(queryPersone)) {
                stmtPersone.setString(1, codiceFiscale);
                stmtPersone.setString(2, nome);
                stmtPersone.setString(3, cognome);
                stmtPersone.setInt(4, eta);
                stmtPersone.setString(5, indirizzo);
                stmtPersone.setString(6, telefono);
                stmtPersone.setString(7, email);
                int righeInserite = stmtPersone.executeUpdate();
                System.out.println("Righe inserite: " + righeInserite);

                // Secondo inserimento nella tabella visitatori
                final String queryVisitatori = """
                            INSERT INTO VISITATORE(codice_fiscale)
                            VALUES (?)
                        """;

                try (PreparedStatement stmtVisitatori = connection.prepareStatement(queryVisitatori)) {
                    stmtVisitatori.setString(1, codiceFiscale);
                    righeInserite = stmtVisitatori.executeUpdate();
                    System.out.println("Righe inserite: " + righeInserite);
                } catch (Exception e) {
                    System.out.println("Problemi nell'inserimento di VISITATORE");
                    e.printStackTrace();
                    return false;
                }
            } catch (Exception e) {
                System.out.println("Problemi nell'inserimento di PERSONA");
                e.printStackTrace();
                return false;
            }

            return true;
        }

        private static String getCheckQuery(String codiceFiscale) {
            return Queries.CHECK_VISITATORE.get() + "\'" + codiceFiscale + "\'";
        }
    }

}
