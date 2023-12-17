package net.niftystik.niftynotes;

import java.util.List;

import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class TwelveTET {
    public static final Interval SEMITONE = new Interval(0);

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

        @Override
        public Note getNoteAtInterval(Interval interval) {
            double freq = interval.ratio * frequency;
            double epsilon = 0.000000001;
            for (Note note : NOTES.notes)
                if (abs(note.frequency - freq) < epsilon)
                    return note;
            return null;
        }

        Note(String name, Accidental accidental, double frequency) {
            super(name, frequency);
            setAccidentalFrequency(accidental);
        }

        protected void setAccidentalFrequency(Accidental accidental) {
            this.accidental = accidental;
            if (accidental != null) {
                if (accidental.weight < 0)
                    for (int i = accidental.weight; i < 0; i++)
                        this.frequency /= pow(2, 1 / 12d);
                else if (accidental.weight > 0)
                    for (int i = accidental.weight; i > 0; i--)
                        this.frequency *= pow(2, 1 / 12d);
            } else
                accidental = null;
        }

        public Note setAccidental(Accidental accidental) {
            for (Note acc : accidentals)
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
            if (accidental == null)
                return name + octave;
            return name + octave + accidental.toString();
        }
    }

    public static final class Interval extends net.niftystik.niftynotes.Interval {
        private Quality quality;
        private Quantity quantity;
        private int semitones;


        Interval(Quality quality, Quantity quantity, int semitones) {
            super((quality == null ? "" : quality + " ") + quantity, pow(2, (semitones - ((semitones / 12) * 12)) / 12d) + semitones / 12);
            this.quality = quality;
            this.quantity = quantity;
            this.semitones = semitones;
        }

        public Interval(int semitones) {
            super(pow(2, semitones / 12d));
            this.semitones = semitones;
        }
    }
}
