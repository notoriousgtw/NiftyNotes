package net.niftystik.niftynotes;

import org.apache.commons.math3.util.Precision;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class TwelveTET {
    TwelveTET() {
    }

    static NoteContainer<Note> getNotes() {
        return NOTES;
    }

    public static final class Note extends net.niftystik.niftynotes.Note<Note, Interval> {
        protected Accidental accidental;
        protected int octave;

        Note(String name, double frequency) {
            super(name, frequency);
        }

        Note(String name, Accidental accidental, double frequency) {
            super(name, frequency);
            setAccidentalFrequency(accidental);
        }


        @Override
        public Note getNoteAtInterval(Interval interval) {
            double freq = interval.ratio * frequency;
            for (Note note : NOTES.all_notes)
                if (Precision.equals(note.frequency, freq, 1e-8))
                    for (Note enharmonic : NOTES.getEnharmonics(note))
                        if (!enharmonic.name.equals(name) && enharmonic.accidental != Accidental.DOUBLE_FLAT && enharmonic.accidental != Accidental.DOUBLE_SHARP)
                            return enharmonic;
            return null;
        }

        public Note getEnharmonic(Accidental accidental) {
            for (Note enharmonic : NOTES.getEnharmonics(this))
                if (enharmonic.accidental == accidental)
                    return enharmonic;
            throw new RuntimeException("There is no enharmonic with accidental:" + accidental);
        }

        protected void setAccidentalFrequency(Accidental accidental) {
            this.accidental = accidental;
            if (accidental != null) {
                if (accidental.weight < 0)
                    for (int i = accidental.weight; i < 0; i++)
                        frequency /= pow(2, 1 / 12d);
                else if (accidental.weight > 0)
                    for (int i = accidental.weight; i > 0; i--)
                        frequency *= pow(2, 1 / 12d);
            } else
                accidental = null;
        }

        public Note getNoteAccidental(Accidental accidental) {
            for (Note acc : NOTES.all_notes)
                if (acc.name.equals(name) && acc.octave == octave && acc.accidental == accidental)
                    return acc;
            return null;
        }

        public Accidental getAccidental() {
            return accidental;
        }

        public int getOctave() {
            return octave;
        }

        @Override
        public String toString() {
            if (accidental == null || accidental == Accidental.NATURAL)
                return name + octave;
            return name + octave + accidental.toString();
        }

        public Note getNextNat() {
            for (int i = 0; i < NOTES.natural_notes.size(); i++) {
                Note note = NOTES.natural_notes.get(i);
                if (name.equals(note.name) && octave == note.octave)
                   return NOTES.natural_notes.get(i + 1);
            }
            return null;
        }
    }

    public static final class Interval extends net.niftystik.niftynotes.Interval<Interval> {
        protected Quality quality;
        protected Quantity quantity;
        protected int semitones;
        protected int octave;


        Interval(Quality quality, Quantity quantity, int semitones) {
            super((quality == null ? "" : quality + " ") + quantity, pow(2, (semitones - ((semitones / 12) * 12)) / 12d) + semitones / 12);
            this.quality = quality;
            this.quantity = quantity;
            this.semitones = semitones;
            if (semitones > 12 && semitones < 24)
                octave = 1;
            else if (semitones > 24)
                octave = 2;
            else
                octave = 0;
        }

        public Interval(String name, int semitones) {
            super(name, pow(2, semitones / 12d));
            this.semitones = semitones;
        }

        public Quality getQuality() {
            return quality;
        }

        public Quantity getQuantity() {
            return quantity;
        }
    }
}
