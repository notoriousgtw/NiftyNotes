package net.niftystik.niftynotes;

import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class Main {
    public static void main(String[] args) {
        TwelveTETConstants.register();
        TwelveTETNoteGraph notes = TwelveTETConstants.notes;
        for (TwelveTETIntervalGraph.Node node : intervalGraph.nodes)
            System.out.println(node);
    }
}