package net.niftystik.niftynotes;

public abstract class Interval {
    protected String name;
    protected String abbr;
    protected String alt_name;
    protected String  alt_abbr;
    protected double ratio;
    protected Interval enharmonic;

    Interval(double ratio) {
        this.ratio = ratio;
    }

    Interval(String name, double ratio) {
        this.name = name;
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setAbbr(String abbr) {
        this.abbr = abbr;
    }

    public String getAbbr() {
        return abbr;
    }

    public void setAlt_Name(String alt_name) {
        this.alt_name = alt_name;
    }

    public String getAlt_Name() {
        return alt_name;
    }

    public void setAlt_Abbr(String alt_abbr) {
        this.alt_abbr = alt_abbr;
    }

    public String getAlt_Abbr() {
        return alt_abbr;
    }

    public double getRatio() {
        return ratio;
    }

    public void setEnharmonic(Interval enharmonic) {
        this.enharmonic = enharmonic;
    }

    public Interval getEnharmonic() {
        return enharmonic;
    }

    @Override
    public String toString() {
        return name;
    }
}
