package net.niftystik.niftynotes;

import net.niftystik.niftynotes.TwelveTET.Note;
import net.niftystik.niftynotes.TwelveTET.Interval;

import java.util.List;

public class TwelveTETNoteGraph extends NoteGraph<Note, TwelveTETNoteGraph.Node>{
   TwelveTETNoteGraph() {
      super();
   }

   @Override
   void add(Note note) {
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

   public class Populator extends DFS {

      Populator() {
         super();
      }
      @Override
      protected void visit(net.niftystik.niftynotes.Node node) {

      }

      @Override
      protected void mark(net.niftystik.niftynotes.Node node) {

      }

      @Override
      protected List neighbors(net.niftystik.niftynotes.Node node) {
         return null;
      }
   }

   public class Node extends net.niftystik.niftynotes.Node<Note, Edge> {
      Node(Note note) {
         super(note);
      }
   }

   public class Edge extends net.niftystik.niftynotes.Edge<Interval, Node> {

      Edge(Node start, Node end) {
         super(start, end);
      }
   }
}
