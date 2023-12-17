package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.List;

import net.niftystik.niftynotes.TwelveTET.Note;
public class TwelveTETNoteContainer extends NoteContainer<Note> {
    protected List<Note> natural_notes;

    protected List<Note> accidentals;
    protected List<List<Note>> enharmonics;

    TwelveTETNoteContainer(List<Note> notes, List<Note> accidentals) {
        super(notes);
        setAccidentals(accidentals);
        setEnharmonics();
    }

    private void setEnharmonics() {
        List<Note>[] enharmonics = new List[notes.size()];
        for (Note note : notes) {
            List<Note> enharmonic_group = new ArrayList<>();
            enharmonic_group.add(note);
            for (Note accidental : accidentals) {
                if (note.isEnharmonic(accidental) && accidental.accidental != Accidental.NATURAL && accidental.accidental != note.accidental)
                    enharmonic_group.add(accidental);
                if (accidental.frequency > note.frequency && !accidental.name.equals(note.name))
                    break;
            }
            enharmonics[notes.indexOf(note)] = (List.of(enharmonic_group.toArray(new Note[]{})));
        }
        this.enharmonics = List.of(enharmonics);
    }

    @Override
    public Note getNote(String name) {
        return null;
    }

    public Note getNote(String name, Accidental accidental) {
        return null;
    }

    @Override
    protected void setNotes(List<Note> notes) {
        this.notes = List.of(notes.toArray(new Note[]{}));
    }

    protected void setAccidentals(List<Note> accidentals) {
        this.accidentals = List.of(accidentals.toArray(new Note[]{}));
    }
}
