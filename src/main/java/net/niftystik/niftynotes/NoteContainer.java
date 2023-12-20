package net.niftystik.niftynotes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.NoSuchElementException;

public abstract class NoteContainer<N extends Note> {
    protected List<N> notes;

}
