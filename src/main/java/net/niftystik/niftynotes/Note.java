package net.niftystik.niftynotes;

import org.apache.commons.math3.util.Precision;

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
        if (Precision.equals(frequency, note.frequency, 1e-8))
            return true;
        return false;
    }

    public abstract N getNoteAtInterval(I interval);
}
