package parcofaunistico.data;

public enum Queries {
    SHOW_VISITATORE("""
            SELECT *
            FROM PERSONA p, VISITATORE v
            WHERE p.codice_fiscale = v.codice_fiscale;
            """),

    SHOW_VISITATORE_SINGOLO("""
            SELECT *
            FROM PERSONA p
            WHERE p.codice_fiscale = """),

    SHOW_DIPENDENTE("""
            SELECT *
            FROM PERSONA p, DIPENDENTE d
            WHERE p.codice_fiscale = d.codice_fiscale;
            """),

    SHOW_AREE("""
            SELECT *
            FROM AREA
            """),

    SHOW_ZONA_RICREATIVA("""
            SELECT zr.nome, a.orario_apertura, a.orario_chiusura
            FROM ZONA_RICREATIVA zr, AREA a
            WHERE a.nome = zr.nome
            """),

    SHOW_ZONA_AMMINISTRATIVA("""
            SELECT za.nome, a.orario_apertura, a.orario_chiusura
            FROM ZONA_AMMINISTRATIVA za, AREA a
            WHERE a.nome = za.nome
            """),

    SHOW_HABITAT("""
            SELECT h.nome, a.orario_apertura, a.orario_chiusura
            FROM AREA a, HABITAT h
            WHERE a.nome = h.nome
            """),

    SHOW_SPECIE("""
            SELECT *
            FROM SPECIE
            """),

    SHOW_ESEMPLARE("""
            SELECT *
            FROM ESEMPLARE
            """),

    SHOW_DIETA("""
            SELECT *
            FROM DIETA
            """),

    SHOW_PRODOTTO("""
            SELECT *
            FROM PRODOTTO
            """),

    SHOW_ORDINE("""
            SELECT o.*, p.codice_prodotto, p.nome AS nomeProd, p.descrizione, p.prezzo_unitario, (o.quantita_acquistata * p.prezzo_unitario ) AS PrezzoOrdine
            FROM ORDINE o, PRODOTTO p
            WHERE o.codice_prodotto = p.codice_prodotto
            """),

    SHOW_RENDIMENTO_GIORNALIERO("""
            SELECT *
            FROM RENDIMENTO_GIORNALIERO
            """),

    SHOW_SCONTO("""
            SELECT *
            FROM SCONTO
            """),

    SHOW_SCONTO_SINGOLO("""
            SELECT *
            FROM SCONTO
            WHERE codice_sconto = """),

    SHOW_AFFLUENZA("""
            SELECT v.nome, COUNT(*) as numVisite
            FROM visita v
            GROUP BY v.nome
            """),

    SHOW_APPLICAZIONI_SCONTI("""
            SELECT pv.codice_sconto, COUNT(*) AS numApplicazioni, s.tipologia
            FROM PAGAMENTO_VISITA pv, SCONTO s
            WHERE pv.codice_sconto = s.codice_sconto
            AND pv.codice_sconto IS NOT NULL
            GROUP BY pv.codice_sconto;
            """),

    SHOW_INCASSI_BIGLIETTI("""
                SELECT pv.data_effettuazione, SUM(b.prezzo_effettivo) AS totale_incassi_giornalieri
                FROM BIGLIETTO b, PAGAMENTO_VISITA pv
                WHERE (b.codice_transazione = pv.codice_transazione AND b.codice_fiscale = pv.codice_fiscale)
            OR (b.codice_transazione = pv.codice_transazione AND b.codice_gruppo = pv.codice_gruppo)
                GROUP BY pv.data_effettuazione;
                """),

    SHOW_CLASSIFICA_PRODOTTI("""
            WITH stat_prodotto AS (
            SELECT
            o.nome AS zona,
            o.codice_prodotto,
            p.nome AS prodotto_nome,
            SUM(o.quantita_acquistata) AS quantita_tot,
            SUM(o.quantita_acquistata * p.prezzo_unitario) AS ricavo
            FROM ORDINE o
            JOIN PRODOTTO p ON p.codice_prodotto = o.codice_prodotto
            GROUP BY o.nome, o.codice_prodotto, p.nome
            ),
            ricavo_zona AS (
            SELECT zona, SUM(ricavo) AS ricavo_totale_zona
            FROM stat_prodotto
            GROUP BY zona
            ),
            classifica AS (
            SELECT
            sp.*,
            rz.ricavo_totale_zona,
            ROUND(100 * sp.ricavo / NULLIF(rz.ricavo_totale_zona,0), 2) AS contributo_prodotto_percentuale,
            ROW_NUMBER() OVER (
              PARTITION BY sp.zona
              ORDER BY sp.quantita_tot DESC, sp.ricavo DESC
            ) AS rn
            FROM stat_prodotto sp
            JOIN ricavo_zona rz USING (zona)
            )
            SELECT
            zona,
            codice_prodotto,
            prodotto_nome,
            quantita_tot,
            ricavo,
            ricavo_totale_zona,
            contributo_prodotto_percentuale
            FROM classifica
            WHERE rn <= 3
            ORDER BY zona, quantita_tot DESC, rn;
            """),

    SHOW_ACQUISTI_PRODOTTO("""
            SELECT p.codice_prodotto, p.nome, SUM(o.quantita_acquistata) AS totale_venduto
            FROM PRODOTTO p, ORDINE o
            WHERE p.codice_prodotto = o.codice_prodotto
            GROUP BY o.codice_prodotto;
            """),
    
    CHECK_VISITATORE("""
            SELECT v.codice_fiscale
            FROM VISITATORE v
            WHERE v.codice_fiscale = """
    ),
    
    CHECK_DIPENDENTE("""
            SELECT d.codice_fiscale
            FROM DIPENDENTE d
            WHERE d.codice_fiscale = """
    ),

    FIND_NOME("""
        SELECT p.codice_fiscale, p.nome
        FROM PERSONA p
        WHERE p.codice_fiscale = """
    ),

    SHOW_GIORNATA_LAVORATIVA("""
        SELECT g.*, d.codice_fiscale
        FROM GIORNATA_LAVORATIVA g, DIPENDENTE d
        WHERE d.codice_fiscale = g.codice_fiscale
        AND d.codice_fiscale = """),

    SHOW_VISITATORE_ORDINI("""
        SELECT v.codice_fiscale, r.codice_fiscale, o.*, p.codice_prodotto, p.nome AS nomeProd, p.descrizione, p.prezzo_unitario, (o.quantita_acquistata * p.prezzo_unitario ) AS PrezzoOrdine
        FROM VISITATORE v, RICHIESTA r, ORDINE o, PRODOTTO P
        WHERE p.codice_prodotto = o.codice_prodotto
        AND o.codice_ordine = r.codice_ordine
        AND r.codice_fiscale = v.codice_fiscale
        AND v.codice_fiscale = """),

    SHOW_PERCORSI("""
        SELECT *
        FROM PERCORSO            
        """),

    SHOW_ULTIMO_PAGAMENTO("""
        SELECT *
        FROM PAGAMENTO_VISITA
        ORDER BY codice_transazione DESC
        LIMIT 1;            
        """),

    SHOW_ULTIMO_BIGLIETTO("""
        SELECT *
        FROM BIGLIETTO
        ORDER BY codice_biglietto DESC
        LIMIT 1;           
        """);

    private final String query;

    Queries(final String query) {
        this.query = query;
    }

    public String get() {
        return this.query;
    }
}
