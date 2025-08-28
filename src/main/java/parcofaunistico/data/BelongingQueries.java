package parcofaunistico.data;

import java.util.EnumMap;
import java.util.HashMap;
import java.util.Map;

public final class BelongingQueries {
    private static final Map<Queries, Integer> QUERIES_LETTURA_DIPENDENTE = new EnumMap<>(Queries.class) {{
        put(Queries.SHOW_AREE, 0);
        put(Queries.SHOW_ZONA_AMMINISTRATIVA, 1);
        put(Queries.SHOW_ZONA_RICREATIVA, 2);
        put(Queries.SHOW_HABITAT, 3);
        put(Queries.SHOW_SPECIE, 6);
        put(Queries.SHOW_ESEMPLARE, 7);
        put(Queries.SHOW_ORDINE, 8);
        put(Queries.SHOW_PRODOTTO, 9);
    }};

    private static final Map<Queries, Integer> QUERIES_LETTURA_VISITATORE = new EnumMap<>(Queries.class) {{
        put(Queries.SHOW_ZONA_RICREATIVA, 2);
        put(Queries.SHOW_HABITAT, 3);
        put(Queries.SHOW_SPECIE, 6);
        put(Queries.SHOW_ESEMPLARE, 7);
        put(Queries.SHOW_PRODOTTO, 9);
        put(Queries.SHOW_SCONTO, 10);
    }};

    private static final Map<Queries, Integer> QUERIES_LETTURA_MANAGER = new EnumMap<>(Queries.class) {{
        put(Queries.SHOW_AREE, 0 );
        put(Queries.SHOW_ZONA_AMMINISTRATIVA, 1);
        put(Queries.SHOW_ZONA_RICREATIVA, 2);
        put(Queries.SHOW_HABITAT, 3);
        put(Queries.SHOW_VISITATORE, 4);
        put(Queries.SHOW_DIPENDENTE, 5);
        put(Queries.SHOW_SPECIE, 6);
        put(Queries.SHOW_ESEMPLARE, 7);
        put(Queries.SHOW_ORDINE, 8);
        put(Queries.SHOW_PRODOTTO, 9);
        put(Queries.SHOW_ACQUISTI_PRODOTTO, 11);
        put(Queries.SHOW_AFFLUENZA, 12);
        put(Queries.SHOW_APPLICAZIONI_SCONTI, 13);
        put(Queries.SHOW_CLASSIFICA_PRODOTTI, 14);
        put(Queries.SHOW_INCASSI_BIGLIETTI, 15);
        put(Queries.SHOW_RENDIMENTO_GIORNALIERO, 16);
    }};

    private static final Map<Integer, Queries> QUERIES_LETTURA = new HashMap<>() {{
        put(0, Queries.SHOW_AREE );
        put(1, Queries.SHOW_ZONA_AMMINISTRATIVA);
        put(2, Queries.SHOW_ZONA_RICREATIVA);
        put(3, Queries.SHOW_HABITAT);
        put(4, Queries.SHOW_VISITATORE);
        put(5, Queries.SHOW_DIPENDENTE);
        put(6, Queries.SHOW_SPECIE);
        put(7, Queries.SHOW_ESEMPLARE);
        put(8, Queries.SHOW_ORDINE);
        put(9, Queries.SHOW_PRODOTTO);
        put(10, Queries.SHOW_SCONTO);
        put(11, Queries.SHOW_ACQUISTI_PRODOTTO);
        put(12, Queries.SHOW_AFFLUENZA);
        put(13, Queries.SHOW_APPLICAZIONI_SCONTI);
        put(14, Queries.SHOW_CLASSIFICA_PRODOTTI);
        put(15, Queries.SHOW_INCASSI_BIGLIETTI);
        put(16, Queries.SHOW_RENDIMENTO_GIORNALIERO);
    }};




    private BelongingQueries() { }

    public static Map<Queries, Integer> getManagerReadingQueries() {
        return QUERIES_LETTURA_MANAGER;
    }

    public static Map<Queries, Integer> getDipendenteReadingQueries() {
        return QUERIES_LETTURA_DIPENDENTE;
    }

    public static Map<Queries, Integer> getVisitatoreReadingQueries() {
        return QUERIES_LETTURA_VISITATORE;
    }

    public static Map<Integer, Queries> getReadingQueries() {
        return QUERIES_LETTURA;
    }
}
