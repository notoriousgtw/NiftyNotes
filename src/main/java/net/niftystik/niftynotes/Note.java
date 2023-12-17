package net.niftystik.niftynotes;

import java.util.ArrayList;

import static java.lang.Math.abs;
import static net.niftystik.niftynotes.TwelveTETConstants.NOTES;

public abstract class Note<N extends Note, I extends Interval> {
    protected String name;
    protected double frequency;

    Note(String name, double frequency) {
        this.name = name;
        this.frequency = frequency;
    }

    public String getName() {
        return name;
    }

    public double getFrequency() {
        return frequency;
    }

    public boolean isEnharmonic(Note note) {
        double epsilon = 0.000000001;
        if (abs(note.frequency - frequency) < epsilon)
            return true;
        return false;
    }

    public abstract N getNoteAtInterval(I interval);
}
