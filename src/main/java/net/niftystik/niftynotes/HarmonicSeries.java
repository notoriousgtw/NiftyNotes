package net.niftystik.niftynotes;

import java.util.*;

public class HarmonicSeries {
    public static ArrayList<Float> getSeries(float fundamental, int n) {
        ArrayList<Float> series = new ArrayList<>();
        for (int i = 1; i < n + 1; i++)
            series.add(i * fundamental);
        return series;
    }

    public static float getHarmonic(float fundamental, int n) {
        return fundamental * n;
    }
}
