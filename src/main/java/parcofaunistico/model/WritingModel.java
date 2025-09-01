package parcofaunistico.model;

import java.util.Map;

import javax.swing.JTextField;

import parcofaunistico.data.Parametri;

public interface WritingModel {
    boolean insertVisitatore(final Map<Parametri, JTextField> textfields);

    int getVisitatoreEta(String codiceFiscale);

    String getLastPayment();

    String getLastTicket();

    Double getDiscountPercent(String codiceSconto);
}
