package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;
import org.apache.commons.math3.util.Precision;

import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class TwelveTETScale extends Scale<Note, Interval> {
    protected Accidental accidental;
    protected int mode_degree;

    TwelveTETScale(String name, ArrayList<Interval> formula) {
        super(name, formula);
        mode_degree = 1;
    }

    TwelveTETScale (String name, ArrayList<Interval> formula, int mode_degree) {
        super(name, formula);
        this.formula = formula;
        setStructure();
        this.mode_degree = mode_degree;
    }

    protected void setStructure() {
        if (structure == null)
            structure = new ArrayList<>();
        else
            structure.clear();

//        for (int i = 0 ; i < formula.size() ; i++) {
//            if (i == formula.size() - 1) {
//                structure.add(INTERVALS.asStep(12 - formula.get(i).semitones));
//                break;
//            }
//            structure.add(INTERVALS.asStep(formula.get(i + 1).semitones - formula.get(i).semitones));
//        }
    }

    public void setTonic(Note tonic) {
        if (tonic == null) {
            if (notes != null) {
                notes.clear();
                notes = null;
            }
            this.tonic = null;
            return;
        }

        this.tonic = tonic;
        if (notes == null)
            notes = new ArrayList<>();
        else
            notes.clear();

        notes.add(tonic);

        Note note = tonic;

        for (Interval interval : structure) {
            note = note.getNoteAtInterval(interval);
            notes.add(note);
        }
    }


    public Note getTonic() {
        return tonic;
    }

    public Note getNextDiatonic(Note note, boolean octaveRollover) {
        return null;
    }

    public TwelveTETScale getMode(int degree) {
        if (degree < 1 || degree > formula.size())
            throw new IllegalArgumentException("degree must be between 1 and the number of notes in the scale inclusive");
        TwelveTETScale mode = null;
        try {
            mode = (TwelveTETScale) this.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
        mode.formula.clear();

        int semitones = 0;
        int i = degree - 1;
        boolean first = true;
//        while (i != degree - 1 || first) {
//            if (first) {
//                first = false;
//                mode.formula.add(INTERVALS.getInterval(Quality.PERFECT, Quantity.FIRST));
//                i = i < structure.size() - 1 ? i + 1 : 0;
//                continue;
//            }
//            semitones += structure.get(i == 0 ? structure.size() - 1 : i - 1).semitones;
//            Interval interval = INTERVALS.getSimplest(semitones);
//            if (interval.quantity.degree == mode.formula.get(mode.formula.size() - 1).quantity.degree)
//                mode.formula.add(interval.getEnharmonic());
//            else
//                mode.formula.add(INTERVALS.getSimplest(semitones));
//            i = i < structure.size() - 1 ? i + 1 : 0;
//        }
        mode.setStructure();
        mode.mode_degree = degree;
        return mode;
    }

    public TwelveTETScale getMode(int degree, String name) {
        TwelveTETScale mode = getMode(degree);
        mode.name = name;
        return mode;
    }

    public boolean isDiatonic(Note note) {
        return notes.contains(note);
    }

    protected void checkFormula(ArrayList<Interval> formula) {
        if (formula.get(0).quantity != Quantity.FIRST && formula.get(0).quality != null)
            throw new IllegalArgumentException("scale formula must begin with an unaltered first");
        for (int i = 1 ; i < formula.size() ; i++) {
            if (formula.get(i).semitones <= formula.get(i - 1).semitones)
                throw new IllegalArgumentException("scale formula must be a list of intervals ordered by increasing size");
//            if (formula.get(i).semitones > 12 || formula.get(i).equals(INTERVALS.getInterval(Quality.MINOR, Quantity.NINTH)))
//                throw new IllegalArgumentException("scale formula must fit within the octave");
        }
    }
}
