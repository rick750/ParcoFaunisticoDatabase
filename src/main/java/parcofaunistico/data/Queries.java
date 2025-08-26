package parcofaunistico.data;

public final class Queries {
    public static final String SHOW_PERSONE = 
    """
    SELECT *
    FROM PERSONA p, VISITATORE v
    WHERE p.codice_fiscale = v.codice_fiscale;
    """;

    public static final String SHOW_AREE = 
    """
    SELECT *
    FROM AREA
    """;

    public static final String SHOW_ZONA_RICREATIVA = 
    """
    SELECT zr.nome, a.orario_apertura, a.orario_chiusura
    FROM ZONA_RICREATIVA zr, AREA a
    WHERE a.nome = zr.nome         
    """;

    public static final String SHOW_ZONA_AMMINISTRATIVA = 
     """
    SELECT za.nome, a.orario_apertura, a.orario_chiusura
    FROM ZONA_AMMINISTRATIVA za, AREA a
    WHERE a.nome = za.nome         
    """;

    public static final String SHOW_HABITAT =
    """
    SELECT h.nome, a.orario_apertura, a.orario_chiusura
    FROM AREA a, HABITAT h
    WHERE a.nome = h.nome
    """;

    public static final String SHOW_SPECIE =
    """
    SELECT *
    FROM SPECIE        
    """;

    public static final String SHOW_ESEMPLARE = 
    """
    SELECT *
    FROM ESEMPLARE   
    """;

    public static final String SHOW_DIETA = 
    """
    SELECT * 
    FROM DIETA        
    """;

    public static final String SHOW_PRODOTTO = 
    """
    SELECT *
    FROM PRODOTTO        
    """;

    public static final String SHOW_ORDINE = 
    """
    SELECT *
    FROM ORDINE        
    """;

    public static final String SHOW_RENDIMENTO_GIORNALIERO = 
    """
    SELECT * 
    FROM RENDIMENTO_GIORNALIERO        
    """;

    public static final String SHOW_SCONTO =
    """
    SELECT *
    FROM SCONTO        
    """;

    public static final String SHOW_AFFLUENZA =
    """
    SELECT v.nome, COUNT(*) as numVisite
    FROM visita v
    GROUP BY v.nome
    """;

    public static final String SHOW_APPLICAZIONI_SCONTI =
    """
    SELECT pv.codice_sconto, COUNT(*) AS numApplicazioni, s.tipologia
    FROM PAGAMENTO_VISITA pv, SCONTO s
    WHERE pv.codice_sconto = s.codice_sconto
    AND pv.codice_sconto IS NOT NULL 
    GROUP BY pv.codice_sconto;        
    """;

    public static final String SHOW_INCASSI_BIGLIETTI =
    """
    SELECT pv.data_effettuazione, SUM(b.prezzo_effettivo) AS totale_incassi_giornalieri
    FROM BIGLIETTO b, PAGAMENTO_VISITA pv
    WHERE (b.codice_transazione = pv.codice_transazione AND b.codice_fiscale = pv.codice_fiscale) 
	OR (b.codice_transazione = pv.codice_transazione AND b.codice_gruppo = pv.codice_gruppo) 
    GROUP BY pv.data_effettuazione;         
    """;

    public static final String SHOW_CLASSIFICA_PRODOTTI = 
    """
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
    """;

    public static final String SHOW_ACQUISTI_PRODOTTO = 
    """
    SELECT p.codice_prodotto, p.nome, SUM(o.quantita_acquistata) AS totale_venduto
    FROM PRODOTTO p, ORDINE o
    WHERE p.codice_prodotto = o.codice_prodotto
    GROUP BY o.codice_prodotto;        
    """;
}
