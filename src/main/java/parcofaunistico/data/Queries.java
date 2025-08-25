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
}
