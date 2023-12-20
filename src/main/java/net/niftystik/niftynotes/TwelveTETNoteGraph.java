package net.niftystik.niftynotes;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;
import org.apache.commons.math3.util.Precision;

import static net.niftystik.niftynotes.TwelveTETConstants.INTERVALS;

public class TwelveTETNoteGraph extends NoteGraph<Interval, Note, TwelveTETNoteGraph.Node>{
   TwelveTETNoteGraph() {
      super();
   }

   @Override
   void add(Note note) {
      nodes.add(new Node(note));
   }

//   protected void populateIntervals() {
//      for (Node node : nodes) {
//         for (TwelveTET.Interval interval : INTERVALS.intervals) {
//            if (interval.semitones == 0)
//               continue;
//
//            char target_name = (char) (node.note.name.charAt(0) + (interval.quantity.degree));
//            double freq = interval.ratio * node.note.frequency;
//
//            for (Node interval_node : nodes) {
//               if (interval.quality == Quality.AUGMENTED)
//               if (interval_node.note.name.equals(target_name) && Precision.equals(interval_node.note.frequency, freq, 1e-8)) {
//
//               }
//            }
//         }
//      }
//   }

   public class Node extends NoteNode {
      Node(Note note) {
         super(note);
      }
   }
}
