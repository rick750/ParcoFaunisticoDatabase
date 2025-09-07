package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Specie {
    final String nomeScientifico;
    final String nomeComune;
    final String abitudini;
    final int numeroEsemplari;

    public Specie(final String nomeS, final String nomeC, final String ab, final int num) {
        this.nomeScientifico = nomeS;
        this.nomeComune = nomeC;
        this.abitudini = ab;
        this.numeroEsemplari = num;
    }

    public String getNomeScientifico() {
        return nomeScientifico;
    }

    public int getNumeroEsemplari() {
        return numeroEsemplari;
    }

    @Override
    public String toString() {
        return "Nome Scientifico: " + nomeScientifico + ",      "
                + "Nome Comune: " + nomeComune + ",       "
                + "Abitudini: " + abitudini + ",    "
                + "Numero esemplari: " + numeroEsemplari;
    }

    public static final class DAO {
        public static List<Specie> list(Connection connection) {
            var specie = new ArrayList<Specie>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_SPECIE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nomeScientifico = resultSet.getString("nome_scientifico");
                    final var nomeComune = resultSet.getString("nome_comune");
                    final var abitudini = resultSet.getString("abitudini");
                    final var numeroEsemplari = resultSet.getInt("numero_esemplari");
                    final var s = new Specie(nomeScientifico, nomeComune, abitudini, numeroEsemplari);
                    specie.add(s);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return specie;            
        }

        public static Map<Parametri, String> getFromEsemplare(Connection connection, final String nomeEsemplare) {
            final var fields = new EnumMap<Parametri, String>(Parametri.class);
            try(
                var preparedStatement = DAOUtils.prepare(connection, getSpecieSingolaQuery(nomeEsemplare));
                var resultSet = preparedStatement.executeQuery();
            ) {
                    resultSet.next();
                    final var nomeScientifico = resultSet.getString("nome_scientifico");
                    final var numeroEsemplari = resultSet.getInt("numero_esemplari");
                    fields.put(Parametri.NOME_SCIENTIFICO, nomeScientifico);
                    fields.put(Parametri.NUMERO_ESEMPLARI, String.valueOf(numeroEsemplari));  
            }
               catch (SQLException e) {
                e.printStackTrace();
            }
            return fields;            
        }

         public static boolean check(Connection connection, String nome_scientifico) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(nome_scientifico));
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

        public static boolean insert(Connection connection, Map<Parametri, String> textfields) {
            final String nome_scientifico = textfields.get(Parametri.NOME_SCIENTIFICO);
            final String nomeComune = textfields.get(Parametri.NOME_COMUNE);
            final String abitudini = textfields.get(Parametri.ABITUDINI);
            final int numEsemplari = Integer.parseInt(textfields.get(Parametri.NUMERO_ESEMPLARI));
            final String nomeHabitat = textfields.get(Parametri.NOME_HABITAT);


            final String querySpecie = """
                        INSERT INTO SPECIE(nome_scientifico, nome_comune, abitudini, numero_esemplari, nome)
                        VALUES (?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtSpecie = connection.prepareStatement(querySpecie)) {
                stmtSpecie.setString(1, nome_scientifico);
                stmtSpecie.setString(2, nomeComune);
                stmtSpecie.setString(3, abitudini);
                stmtSpecie.setInt(4, numEsemplari);
                stmtSpecie.setString(5, nomeHabitat);
                stmtSpecie.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        public static boolean addEsemplare(Connection connection, String nomeScientifico) {
            final String queryUpdate = """
                UPDATE SPECIE
                SET numero_esemplari = numero_esemplari + 1
                WHERE nome_scientifico = ?""";
                try ( PreparedStatement stmt = connection.prepareStatement(queryUpdate)) {
                        stmt.setString(1, nomeScientifico);
                        stmt.executeUpdate();

                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
        }

         public static boolean removeEsemplare(Connection connection, String nomeScientifico) {
            final String queryUpdate = """
                UPDATE SPECIE
                SET numero_esemplari = numero_esemplari - 1
                WHERE nome_scientifico = ?""";
                try ( PreparedStatement stmt = connection.prepareStatement(queryUpdate)) {
                        stmt.setString(1, nomeScientifico);
                        stmt.executeUpdate();
                } catch (SQLException e) {
                    e.printStackTrace();
                    return false;
                }
                return true;
        }

        public static boolean delete(final Connection connection, final String nomeScientifico) {
            final String deleteQuery = "DELETE FROM SPECIE WHERE nome_scientifico = ?";

            try (PreparedStatement stmt = connection.prepareStatement(deleteQuery)) {
                stmt.setString(1, nomeScientifico);
                stmt.executeUpdate();
                return true;
            } catch (SQLException e) {
                e.printStackTrace();
                return false;
            }
        }




        private static String getCheckQuery(String nome_scientifico) {
            return Queries.CHECK_SPECIE.get() + "\'" + nome_scientifico + "\'";
        }

        private static String getSpecieSingolaQuery(String nomeEsemplare) {
            return Queries.SHOW_SPECIE_SINGOLA_FROM_ESEMPLARE.get() + "\'" + nomeEsemplare + "\'";
        }
    }
}
