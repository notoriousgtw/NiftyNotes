package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class NoteContainer<N extends Note> {
    protected List<N> notes;

    NoteContainer(List<N> notes) {
        setNotes(notes);
    }

    public abstract N getNote(String name);

    protected abstract void setNotes(List<N> notes);
}
