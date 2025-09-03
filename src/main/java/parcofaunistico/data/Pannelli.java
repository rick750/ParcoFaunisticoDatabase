package parcofaunistico.data;

public enum Pannelli {
    USER("user"),
    ACCEDI_REGISTRATI("accReg"),
    LOGIN("login"),
    REGISTRAZIONE("reg"),
    REGISTRAZIONE_VISITATORE("regVis"),
    REGISTRAZIONE_GRUPPO("regGruppo"),
    REGISTRAZIONE_DIPENDENTE("regDip"),
    ACQUISTO_BIGLIETTO_VISITATORE("AcqBiglVis"),
    ACQUISTO_BIGLIETTO_GRUPPO("AcqBiglGrup"),
    ORDINE("ordine");

    private String name;

    Pannelli(final String name) {
        this.name = name;
    }

    public String get() {
        return this.name;
    }
}
