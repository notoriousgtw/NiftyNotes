package net.niftystik.niftynotes;

public enum Quantity {
    FIRST (1, "First", 1),
    SECOND (2, "Second",2),
    THIRD (3, "Third",3),
    FOURTH (4, "Fourth",4),
    FIFTH (5, "Fifth",5),
    SIXTH (6, "Sixth",6),
    SEVENTH (7, "Seventh",7),
    EIGHTH (8, "Eighth",1),
    NINTH (9, "Ninth",2),
    TENTH (10, "Tenth",3),
    ELEVENTH (11, "Eleventh",4),
    TWELFTH (12, "Twelfth",5),
    THIRTEENTH (13, "Thirteenth",6),
    FOURTEENTH (14, "Fourteenth",7),
    FIFTEENTH (15, "Fifteenth",1);

    protected int id;
    private String name;
    private int degree;

    Quantity(int id, String name, int degree) {
        this.name = name;
        this.degree = degree;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public static Quantity getByID(int id) {
        return Quantity.values()[id];
    }
}
