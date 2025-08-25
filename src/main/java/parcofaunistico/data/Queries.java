package parcofaunistico.data;

public final class Queries {
    public static final String SHOW_PERSONE = 
    """
    SELECT *
    FROM PERSONA p, VISITATORE v
    WHERE p.codice_fiscale = v.codice_fiscale;
    """;
}
