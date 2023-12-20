package net.niftystik.niftynotes;

import java.util.List;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;

import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class Main {
    public static void main(String[] args) {
        TwelveTETConstants.register();
        TwelveTETNoteGraph notes = TwelveTETConstants.notes;
        for (TwelveTETIntervalGraph.IntervalNode node : intervalGraph.nodes)
            System.out.println(node);

    }
}