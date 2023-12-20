package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import net.niftystik.niftynotes.TwelveTET.Note;
import org.apache.commons.math3.stat.ranking.NaturalRanking;
import org.apache.commons.math3.util.Precision;

import static java.lang.Math.pow;
import static net.niftystik.niftynotes.TwelveTETConstants.INTERVALS;
import static net.niftystik.niftynotes.TwelveTETConstants.NOTES;

public class TwelveTETNoteContainer extends NoteContainer<Note> {
    protected Map<Note, List<List<Note>>> note_map;
    protected List<Note> all_notes;
    protected List<Note> natural_notes;
    protected List<Note> accidentals;
    protected List<List<Note>> enharmonics;

    TwelveTETNoteContainer() {
        generateNotes();
        setEnharmonics();
    }

    protected void generateNotes() {
        int n = -8;
        int octave = 0;
        char name = 'C';

        ArrayList<Note> notes = new ArrayList<>();
        ArrayList<Note> natural_notes = new ArrayList<>();
        ArrayList<Note> accidentals = new ArrayList<>();
        ArrayList<Note> all_notes = new ArrayList<>();


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
            Note note = new Note(Character.toString(name), pow(2, (n - 49) / 12d) * 440d);
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

        for (Note note : notes)
            if (note.accidental == null)
                natural_notes.add(note);

        for (Note note : accidentals)
            all_notes.add(note);
        for (int i = 0; i < accidentals.size() + natural_notes.size(); i++) {
            Note note = all_notes.get(i);
            if (note.accidental == Accidental.NATURAL) {
                for (Note natural : natural_notes) {
                    if (natural.name.equals(note.name) && natural.octave == note.octave) {
                        all_notes.add(i, natural);
                        i++;
                        break;
                    }
                }
            }
        }
        this.accidentals = List.of(accidentals.toArray(new Note[]{}));
        this.notes = List.of(notes.toArray(new Note[]{}));
        this.all_notes = List.of(all_notes.toArray(new Note[]{}));
        this.natural_notes = List.of(natural_notes.toArray(new Note[]{}));
    }

    protected void setNatural_Notes() {
    }

    protected void setAll_Notes() {
    }

    private void setEnharmonics() {
        List<Note>[] enharmonics = new List[notes.size()];
        for (Note note : notes) {
            List<Note> enharmonic_group = new ArrayList<>();
            enharmonic_group.add(note);
            for (Note accidental : accidentals) {
                if (note.isEnharmonic(accidental) && accidental.accidental != Accidental.NATURAL && accidental.accidental != note.accidental)
                    enharmonic_group.add(accidental);
                if (enharmonic_group.size() == 3)
                    break;
            }
            enharmonics[notes.indexOf(note)] = (List.of(enharmonic_group.toArray(new Note[]{})));
        }
        this.enharmonics = List.of(enharmonics);
    }

    public Note getNote(double frequency) {
        for (Note note : all_notes) {
            if (Precision.equals(note.frequency, frequency, 1e-8)) {
                if (NOTES.getEnharmonics(note).size() != 2 && note.accidental != Accidental.SHARP && note.accidental != Accidental.FLAT) {
                    return note;
                } else if (note.accidental != Accidental.DOUBLE_SHARP && note.accidental != Accidental.DOUBLE_FLAT) {
                    return note;
                }
            }
        }
        return null;
    }

    public Note getNote(String name, int octave) {
        for (Note note : natural_notes)
            if (note.name.equals(name) && note.octave == octave && note.accidental == null)
                return note;
        return null;
    }

    public Note getNote(String name, int octave, Accidental accidental) {
        for (Note note : all_notes)
            if (note.name.equals(name) && note.octave == octave && note.accidental == accidental)
                return note;
        return null;
    }

    public List<Note> getEnharmonics(Note note) {
        for (List<Note> enharmonic_group : enharmonics)
            if (enharmonic_group.contains(note))
                return enharmonic_group;
        return null;
    }
}