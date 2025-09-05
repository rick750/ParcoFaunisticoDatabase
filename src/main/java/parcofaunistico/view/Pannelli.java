package parcofaunistico.view;

public enum Pannelli {
    USER("user"),
    ACCEDI_REGISTRATI("accReg"),
    LOGIN("login"),
    REGISTRAZIONE("reg"),
    REGISTRAZIONE_AREA("regArea"),
    REGISTRAZIONE_MANUTENZIONE("regManu"),
    REGISTRAZIONE_VISITATORE("regVis"),
    REGISTRAZIONE_GRUPPO("regGruppo"),
    REGISTRAZIONE_DIPENDENTE("regDip"),
    REGISTRAZIONE_SPECIE_ESEMPLARE("regAnimali"),
    MODIFICA_ESEMPLARE("modEsemplare"),
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
