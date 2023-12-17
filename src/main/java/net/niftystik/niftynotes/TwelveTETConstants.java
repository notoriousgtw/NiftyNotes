package net.niftystik.niftynotes;

import java.util.*;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;

import static java.lang.Math.pow;

public class TwelveTETConstants {
    protected static List<Note> notes;
    protected static List<Note> natural_notes;
    protected static List<Note> accidentals;
    protected static ArrayList<TwelveTET.Interval> intervals;

    static {
        int n = -8;
        int octave = 0;
        char name = 'C';

        notes = new ArrayList<>();
        natural_notes = new ArrayList<>();
        accidentals = new ArrayList<>();


        for (int i = 1, j = 1; n < 100; i += 5, j++) {
            Note double_flat = new Note(Character.toString(name), Accidental.DOUBLE_FLAT, pow(2, (n - 49) / 12d) * 440d);
            double_flat.octave = octave;

            Note flat = new Note(Character.toString(name), Accidental.FLAT, pow(2, (n - 49) / 12d) * 440d);
            flat.octave = octave;

            Note natural = new Note(Character.toString(name), Accidental.NATURAL, pow(2, (n - 49) / 12d) * 440d);
            natural.octave = octave;

            Note sharp = new Note(Character.toString(name), Accidental.SHARP, pow(2, (n - 49) / 12d) * 440d);
            sharp.octave = octave;

            Note double_sharp = new Note(Character.toString(name), Accidental.DOUBLE_SHARP, pow(2, (n - 49) / 12d) * 440d);
            double_sharp.octave = octave;

            accidentals.add(i - 1, double_sharp);
            accidentals.add(i - 1, sharp);
            accidentals.add(i - 1, natural);
            accidentals.add(i - 1, flat);
            accidentals.add(i - 1, double_flat);

            n = (name != 'B' && name != 'E' ? n + 2 : n + 1);
            if (name == 'B')
                octave++;
            name = (name == 'G' ? 'A' : (char) (name + 1));
        }

        n = -8;
        octave = 0;
        name = 'C';
        for (int i = 1, j = 1; n < 100; i++, j++) {
            Note note = new Note(Character.toString(name), pow (2, (n - 49) / 12d) * 440d);
            note.octave = octave;

            notes.add(i - 1, note);
            if (name != 'B' && name != 'E')
            for (Note acc : accidentals) {
                if (acc.name.equals(note.name) && acc.octave == note.octave && acc.accidental == Accidental.SHARP) {
                    notes.add(i, acc);
                    i++;
                    break;
                }
            }

            n = (name != 'B' && name != 'E' ? n + 2 : n + 1);
            if (name == 'B')
                octave++;
            name = (name == 'G' ? 'A' : (char) (name + 1));
        }
    }

    public static final TwelveTETNoteContainer NOTES = new TwelveTETNoteContainer(notes, accidentals);

    static {
        intervals = new ArrayList<>();

        int semitones = 0;
        for (int i = 1; i < 16; i++) {
            if (i == 1) {
                Interval per = new Interval(Quality.PERFECT, Quantity.values()[i - 1], semitones++);
                Interval aug = new Interval(Quality.AUGMENTED, Quantity.values()[i - 1], semitones);
                semitones--;

                intervals.add(per);
                intervals.add(aug);
            } else if (i == 4 || i == 5 || i == 8 || i == 11 || i == 12 || i == 15) {
                Interval dim = new Interval(Quality.DIMINISHED, Quantity.values()[i - 1], semitones++);
                Interval per = new Interval(Quality.PERFECT, Quantity.values()[i - 1], semitones++);
                Interval aug = new Interval(Quality.AUGMENTED, Quantity.values()[i - 1], semitones);

                intervals.add(dim);
                intervals.add(per);
                intervals.add(aug);
                if (i != 4 && i != 11)
                    semitones--;

            } else {
                Interval dim = new Interval(Quality.DIMINISHED, Quantity.values()[i - 1], semitones++);
                Interval min = new Interval(Quality.MINOR, Quantity.values()[i - 1], semitones++);
                Interval maj = new Interval(Quality.MAJOR, Quantity.values()[i - 1], semitones++);
                Interval aug = new Interval(Quality.AUGMENTED, Quantity.values()[i - 1], semitones);
                semitones--;

                intervals.add(dim);
                intervals.add(min);
                intervals.add(maj);
                intervals.add(aug);
            }
        }

    }
}
