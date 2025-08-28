package parcofaunistico.data;

public enum User {
    MANAGER("Manager"),
    DIPENDENTE("Dipendente"),
    VISITATORE("Visitatore");

    private final String nome;

    User(final String nome) {
        this.nome = nome;
    }

    public final String get() {
        return this.nome;
    }
}
