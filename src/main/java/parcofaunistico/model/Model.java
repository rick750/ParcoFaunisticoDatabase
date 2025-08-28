package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;

import parcofaunistico.data.AcquistiProdotto;
import parcofaunistico.data.Affluenza;
import parcofaunistico.data.ApplicazioneSconto;
import parcofaunistico.data.ClassificaProdotto;
import parcofaunistico.data.Esemplare;
import parcofaunistico.data.IncassoBiglietto;
import parcofaunistico.data.Visitatore;

public interface Model {

    List<Visitatore> loadPersone();

    List<Esemplare> loadEsemplari();

    List<Affluenza> loadAffluenze();

    List<ApplicazioneSconto> loadApplicazioniSconto(); 

    List<IncassoBiglietto> loadIncassiBiglietti();

    List<ClassificaProdotto> loadClassificaProdotti();

    List<AcquistiProdotto> loadAcquistiProdotti();

    Boolean checkVisitatore(String codiceFiscale);

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
