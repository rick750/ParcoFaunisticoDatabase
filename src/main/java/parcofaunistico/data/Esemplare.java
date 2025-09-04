package parcofaunistico.data;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Esemplare {
    final String nome;
    final String sesso;
    final int eta;
    final double altezza;
    final int peso;
    final boolean malato;

    public Esemplare(final String nome, final String sesso, final int eta, final double altezza,
                      final int peso, final boolean malato) {
                        this.nome = nome;
                        this.sesso = sesso;
                        this.eta = eta;
                        this.altezza = altezza;
                        this.peso = peso;
                        this.malato = malato;
    }

    @Override
    public String toString() {
        return this.nome + ", "
               + this.sesso + ", "
               + this.eta + ", "
               + this.altezza + ", "
               + this.peso + ", "
               + this.malato;
    }

    public static final class DAO {
        public static List<Esemplare> list(Connection connection) {
            var esemplari = new ArrayList<Esemplare>();
            try(
                var preparedStatement = DAOUtils.prepare(connection, Queries.SHOW_ESEMPLARE.get());
                var resultSet = preparedStatement.executeQuery();
            ) {
                while(resultSet.next()) {
                    final var nome = resultSet.getString("nome");
                    final var sesso = resultSet.getString("sesso");
                    final var eta = resultSet.getInt("eta");
                    final var altezza = resultSet.getDouble("altezza");
                    final var peso = resultSet.getInt("peso");
                    final var malato = resultSet.getBoolean("malato");
                    final var esemplare = new Esemplare(nome, sesso, eta, altezza, peso, malato);
                    esemplari.add(esemplare);
                }       
            }
               catch (SQLException e) {
                throw new DAOException(e);
            }
            return esemplari;            
        }

        public static boolean check(Connection connection, String nome) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckQuery(nome));
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

        public static boolean checkInSpecie(Connection connection, String nome) {
            try (
                    var preparedStatement = DAOUtils.prepare(connection, getCheckInSpecieQuery(nome));
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
            final String nome = textfields.get(Parametri.NOME_ESEMPLARE);
            final String sesso = textfields.get(Parametri.SESSO);
            final int eta = Integer.parseInt(textfields.get(Parametri.ETA));
            final double altezza = Double.parseDouble(textfields.get(Parametri.ALTEZZA));
            final double peso = Double.parseDouble(textfields.get(Parametri.PESO));
            final boolean malato= Boolean.parseBoolean(textfields.get(Parametri.MALATO));
            final String alimento = textfields.get(Parametri.ALIMENTO);
            final String nome_scientifico = textfields.get(Parametri.NOME_SCIENTIFICO);


            final String queryEsemplare = """
                        INSERT INTO ESEMPLARE(nome, sesso, eta, altezza, peso, malato, alimento, nome_scientifico)
                        VALUES (?, ?, ?, ?, ?, ?, ?, ?)
                    """;

            try (PreparedStatement stmtEsemplare = connection.prepareStatement(queryEsemplare)) {
                stmtEsemplare.setString(1, nome);
                stmtEsemplare.setString(2, sesso);
                stmtEsemplare.setInt(3, eta);
                stmtEsemplare.setDouble(4, altezza);
                stmtEsemplare.setDouble(5, peso);
                stmtEsemplare.setBoolean(6, malato);
                stmtEsemplare.setString(7, alimento);
                stmtEsemplare.setString(8, nome_scientifico);
                int righeInserite = stmtEsemplare.executeUpdate();
                System.out.println("Righe inserite dentro Esemplare: " + righeInserite);
            } catch (Exception e) {
                e.printStackTrace();
                return false;
            }

            return true;
        }

        public static boolean update(Connection connection, Map<Parametri, String> textfields) {
            final String nome = textfields.get(Parametri.NOME_ESEMPLARE);
            final Boolean malato = Boolean.parseBoolean(textfields.get(Parametri.MALATO));
            Integer eta;
            if ("".equals(textfields.get(Parametri.ETA))) {
                eta = null;
            } else {
                eta = Integer.parseInt(textfields.get(Parametri.ETA));
            }
            Double altezza;
            if ("".equals(textfields.get(Parametri.ALTEZZA))) {
                altezza = null;
            } else {
                altezza = Double.parseDouble(textfields.get(Parametri.ALTEZZA));
            }
            Double peso;
            if ("".equals(textfields.get(Parametri.PESO))) {
                peso = null;
            } else {
                peso = Double.parseDouble(textfields.get(Parametri.PESO));
            }

            String alimento;
            if ("".equals(textfields.get(Parametri.ALIMENTO))) {
                alimento = null;
            } else {
                alimento = textfields.get(Parametri.ALIMENTO);
            }

            StringBuilder query = new StringBuilder("UPDATE ESEMPLARE SET ");
            List<Object> valori = new ArrayList<>();

            if (eta != null) {
                query.append("eta = ?, ");
                valori.add(eta);
            }
            if (altezza != null) {
                query.append("altezza = ?, ");
                valori.add(altezza);
            }
            if (peso != null) {
                query.append("peso = ?, ");
                valori.add(peso);
            }
            if (alimento != null) {
                query.append("alimento = ?, ");
                valori.add(alimento);
            }

            query.append("malato = ?, ");
            valori.add(malato);
         

            // Rimuove l'ultima virgola e spazio
            query.setLength(query.length() - 2);

            // Aggiunge la clausola WHERE
            query.append(" WHERE nome = ?");
            valori.add(nome);

            // Prepara lo statement
            try(PreparedStatement stmt = connection.prepareStatement(query.toString());) {
                 // Imposta i parametri
                for (int i = 0; i < valori.size(); i++) {
                    stmt.setObject(i + 1, valori.get(i));
                }

                int righeModificate = stmt.executeUpdate();
                System.out.println("Ho modificato " + righeModificate + " dentro Esemplare");
                System.out.println("Query: " + query);
                System.out.println("Valori: " + valori);
            } catch(Exception e) {
                e.printStackTrace();
                System.out.println("Query: " + query);
                System.out.println("Valori: " + valori);
                return false;
            }

            return true;
        }
    }

    private static String getCheckQuery(String nome) {
            return Queries.CHECK_ESEMPLARE.get() + "\'" + nome + "\'";
        }
    
    private static String getCheckInSpecieQuery(String nome) {
            return Queries.CHECK_ESEMPLARE.get() + "\'" + nome + "\'";
    }
}
