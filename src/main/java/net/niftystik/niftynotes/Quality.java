package net.niftystik.niftynotes;

public enum Quality {
    AUGMENTED ("Augmented"),
    MAJOR ("Major"),
    PERFECT ("Perfect"),
    MINOR ("Minor"),
    DIMINISHED ("Diminished");

    String name;

    Quality(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return this.name;
    }

}
