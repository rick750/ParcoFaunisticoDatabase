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
}
