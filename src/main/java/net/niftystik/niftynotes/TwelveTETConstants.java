package net.niftystik.niftynotes;

import java.math.BigDecimal;
import java.util.*;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;
import org.apache.commons.math3.util.Precision;

import static java.lang.Math.pow;

public class TwelveTETConstants {

    protected static TwelveTETIntervalGraph intervalGraph = new TwelveTETIntervalGraph();

    static {
        intervalGraph.populateIntervals();
    }

    public static final ImmutableTwelveTETIntervalGraph INTERVALS = new ImmutableTwelveTETIntervalGraph(intervalGraph);

    public static final Interval SEMITONE = new Interval("Semitone", 1);
    public static final Interval TONE = new Interval("Tone", 2);
    public static final Interval HALF_STEP = new Interval("Half Step", 1);
    public static final Interval WHOLE_STEP = new Interval("Whole Step", 2);

    protected static TwelveTETNoteGraph notes = new TwelveTETNoteGraph();

    static {
        int n = -8;
        int octave = 0;
        char name = 'C';

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

            notes.add(double_flat);
            notes.add(flat);
            notes.add(natural);
            notes.add(sharp);
            notes.add(double_sharp);

            n = (name != 'B' && name != 'E' ? n + 2 : n + 1);
            if (name == 'B')
                octave++;
            name = (name == 'G' ? 'A' : (char) (name + 1));
        }
//        notes.populateIntervals();
    }

    public static final TwelveTETNoteContainer NOTES = new TwelveTETNoteContainer();


//    public static final TwelveTETScale MAJOR = new TwelveTETScale("Major", new ArrayList<>() {{
//        add(INTERVALS.getInterval(Quality.PERFECT, Quantity.FIRST));
//        add(INTERVALS.getInterval(Quality.MAJOR, Quantity.SECOND));
//        add(INTERVALS.getInterval(Quality.MAJOR, Quantity.THIRD));
//        add(INTERVALS.getInterval(Quality.PERFECT, Quantity.FOURTH));
//        add(INTERVALS.getInterval(Quality.PERFECT, Quantity.FIFTH));
//        add(INTERVALS.getInterval(Quality.MAJOR, Quantity.SIXTH));
//        add(INTERVALS.getInterval(Quality.MAJOR, Quantity.SEVENTH));
//    }});
//
//    public static final TwelveTETScale IONIAN = MAJOR.getMode(1, "Ionian");
//    public static final TwelveTETScale DORIAN = MAJOR.getMode(2, "Dorian");
//    public static final TwelveTETScale PHRYGIAN = MAJOR.getMode(3, "Phrygian");
//    public static final TwelveTETScale LYDIAN = MAJOR.getMode(4, "Lydian");
//    public static final TwelveTETScale MIXOLYDIAN = MAJOR.getMode(5, "Mixolydian");
//    public static final TwelveTETScale AEOLIAN = MAJOR.getMode(6, "Aeolian");
//    public static final TwelveTETScale MINOR = MAJOR.getMode(6, "Minor");
//    public static final TwelveTETScale LOCRIAN = MAJOR.getMode(7, "Locrian");

    public static void register() {}
}
