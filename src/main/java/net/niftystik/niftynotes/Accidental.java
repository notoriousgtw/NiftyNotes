package net.niftystik.niftynotes;

public enum Accidental {
    DOUBLE_FLAT ("\uD834\uDD2B", -2),
    FLAT ("♭",-1),
    NATURAL ("♮", 0),
    SHARP ("♯", 1),
    DOUBLE_SHARP ("\uD834\uDD2A", 2);

    String symbol;
    int weight;

    Accidental(String symbol, int weight) {
        this.symbol = symbol;
        this.weight = weight;
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
