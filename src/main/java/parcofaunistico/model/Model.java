package parcofaunistico.model;

import java.sql.Connection;
import java.util.List;

import parcofaunistico.data.AcquistiProdotto;
import parcofaunistico.data.Affluenza;
import parcofaunistico.data.ApplicazioneSconto;
import parcofaunistico.data.Area;
import parcofaunistico.data.ClassificaProdotto;
import parcofaunistico.data.Dipendente;
import parcofaunistico.data.Esemplare;
import parcofaunistico.data.Habitat;
import parcofaunistico.data.IncassoBiglietto;
import parcofaunistico.data.Ordine;
import parcofaunistico.data.Prodotto;
import parcofaunistico.data.RendimentoGiornaliero;
import parcofaunistico.data.Sconto;
import parcofaunistico.data.Specie;
import parcofaunistico.data.Visitatore;
import parcofaunistico.data.ZonaAmministrativa;
import parcofaunistico.data.ZonaRicreativa;

public interface Model {

    List<Visitatore> loadVisitatori();

    List<Esemplare> loadEsemplari();

    List<Ordine> loadOrdini();

    List<Prodotto> loadProdotti();

    List<AcquistiProdotto> loadAcquistiProdotti();

    List<Affluenza> loadAffluenze();

    List<ApplicazioneSconto> loadApplicazioniSconto();
    
    List<ClassificaProdotto> loadClassificaProdotti();

    List<IncassoBiglietto> loadIncassiBiglietti();

    List<RendimentoGiornaliero> loadRendimentiGiornalieri();

    Boolean checkVisitatore(String codiceFiscale);

    Boolean checkDipendente(String codiceFiscale);

    List<Area> loadAree();
    
    List<ZonaAmministrativa> loadZoneAmministrative();

    List<ZonaRicreativa> loadZoneRicreative();

    List<Habitat> loadHabitat();

    List<Dipendente> loadDipendenti();

    List<Specie> loadSpecie();

    List<Sconto> loadSconti();

    static Model fromConnection(Connection connection) {
        return new DBModel(connection);
    }
}
