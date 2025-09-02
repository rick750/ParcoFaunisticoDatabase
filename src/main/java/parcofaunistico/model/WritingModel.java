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
}
