package net.niftystik.niftynotes;

import java.util.List;

public abstract class IntervalContainer<I extends Interval> {
    protected List<I> intervals;

    IntervalContainer(List<I> intervals) {
        setIntervals(intervals);
    }


    protected abstract void setIntervals(List<I> intervals);
}
