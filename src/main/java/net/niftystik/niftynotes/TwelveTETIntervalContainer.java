package net.niftystik.niftynotes;

import net.niftystik.niftynotes.TwelveTET.Interval;

import java.util.ArrayList;
import java.util.List;

import static net.niftystik.niftynotes.TwelveTETConstants.*;

public class TwelveTETIntervalContainer extends IntervalContainer<Interval> {

    TwelveTETIntervalContainer(List<Interval> intervals) {
        super(intervals);
    }

    @Override
    protected void setIntervals(List<Interval> intervals) {
        this.intervals = List.of(intervals.toArray(new Interval[]{}));
    }

    public Interval getInterval(Quality quality, Quantity quantity) {
        for (Interval interval : intervals)
            if (interval.quality == quality && interval.quantity == quantity)
                return interval;
        return null;
    }

    public ArrayList<Interval> getIntervalsBySize(int semitones) {
        ArrayList<Interval> intervals = new ArrayList<>();

        for (Interval interval : this.intervals) {
            if (interval.semitones == semitones)
                intervals.add(interval);
        }
        return intervals;
    }

    public Interval getSimplest(int semitones) {
        ArrayList<Interval> intervals = getIntervalsBySize(semitones);
        Interval ret_interval = null;

        try {
            int max_priority = 0;
            for (Interval interval : intervals) {
                if (interval.quantity.degree == 1) {
                    if (max_priority < 5) {
                        max_priority = 5;
                        ret_interval = interval;
                    }
                }
                else if (interval.quality == Quality.MAJOR) {
                    if (max_priority < 4) {
                        max_priority = 4;
                        ret_interval = interval;
                    }
                }
                else if (interval.quality == Quality.MINOR) {
                    if (max_priority < 3) {
                        max_priority = 3;
                        ret_interval = interval;
                    }
                }
                else if (interval.quality == Quality.PERFECT) {
                    if (max_priority < 2) {
                        max_priority = 2;
                        ret_interval = interval;
                    }
                }
                else if (interval.quality == Quality.AUGMENTED) {
                    if (max_priority < 1) {
                        max_priority = 1;
                        ret_interval = interval;
                    }
                }
                else if (interval.quality == Quality.DIMINISHED) {
                    if (max_priority < 0) {
                        max_priority = 0;
                        ret_interval = interval;
                    }
                }
                else
                    throw new IllegalArgumentException("Invalid number of semitones");
            }
        } catch (IllegalArgumentException e) {
            System.out.println(e);
        }
        return ret_interval;
    }

    public static Interval asTone(int semitones) {
        if (semitones > 2 || semitones == 0)
            throw new IllegalArgumentException("semitones must be 1 or 2");
        if (semitones == 1)
            return SEMITONE;
        else
            return TONE;
    }

    public static Interval asTone(Interval interval) {
        if (interval.semitones > 2 || interval.semitones == 0)
            throw new IllegalArgumentException("interval must be 1 or 2 semitones");
        if (interval.semitones == 1)
            return SEMITONE;
        else
            return TONE;
    }

    public static Interval asStep(int semitones) {
        if (semitones > 2 || semitones == 0)
            throw new IllegalArgumentException("semitones must be 1 or 2");
        if (semitones == 1)
            return HALF_STEP;
        else
            return WHOLE_STEP;
    }

    public static Interval asStep(Interval interval) {
        if (interval.semitones > 2 || interval.semitones == 0)
            throw new IllegalArgumentException("interval must be 1 or 2 semitones");
        if (interval.semitones == 1)
            return HALF_STEP;
        else
            return WHOLE_STEP;
    }
}
