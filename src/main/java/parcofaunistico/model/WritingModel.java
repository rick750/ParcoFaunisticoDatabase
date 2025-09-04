package parcofaunistico.model;

import java.util.List;
import java.util.Map;

import parcofaunistico.data.Parametri;

public interface WritingModel {
    boolean insertVisitatore(Map<Parametri, String> textfields);

    Map<Parametri, String> getVisitatore(String codiceFiscale);

    Boolean checkVisitatore(String codiceFiscale);

    boolean insertGruppo(String codiceGruppo, int numPartecipanti, List<Map<Parametri, String>> partecipanti);

    boolean insertPagamentoBiglietto(Map<Parametri, String> fields);

    String getLastPayment();

    String getLastTicket();

    Double getDiscountPercent(String codiceSconto);

    Boolean checkPathName(String percorso);

    String getLastGroupCode();
    
    boolean checkProdotto(String text);

    boolean checkZonaRicreativa(String text);

    String getNomeProdotto(String text);

    Double getPrezzoProdotto(String text);

    String getActualOrderCode();

    boolean insertOrdine(Map<Parametri, String> fields);

    boolean insertGiornataLavorativa(String codiceFiscale);

    boolean checkGiornataLavorativa(String codiceFiscale, String data);

    boolean checkArea(String nomeArea);

    boolean insertDipendente(Map<Parametri, String> fields);

    boolean checkDipendente(String codiceFiscale);

    Map<Parametri, String> getSpecieFromEsemplare(String nomeEsemplare);

    boolean checkSpecie(String nome_scientifico);

    boolean checkEsemplare(String nomeEsemplare);

    boolean updateEsemplare(Map<Parametri, String> fields);

    boolean deleteEsemplare(String nomeEsemplare);

    boolean checkEsemplareInSpecie(String nomeEsemplare);

    boolean checkDieta(String alimento);

    boolean insertSpecie(Map<Parametri, String> fields);

    boolean deleteSpecie(String nomeScientifico);

    boolean updateSpecieCount(String nomeScientifico, Boolean adding);

    boolean insertEsemplare(Map<Parametri, String> fields);

    boolean insertDieta(Map<Parametri, String> fields);
}
