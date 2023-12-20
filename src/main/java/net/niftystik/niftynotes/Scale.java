package net.niftystik.niftynotes;

import java.util.ArrayList;

public abstract class Scale<N extends Note, I extends Interval> implements Cloneable{
    protected String name;
    protected ArrayList<I> formula;
    protected ArrayList<I> structure;
    protected N tonic;
    protected ArrayList<N> notes;

    Scale (String name, ArrayList<I> formula) {
        this.name = name;
        checkFormula(formula);
        this.formula = formula;
        setStructure();
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public ArrayList<I> getFormula() {
        return formula;
    }

    public ArrayList<I> getStructure() {
        return structure;
    }

    protected abstract void checkFormula(ArrayList<I> formula);
    protected abstract void setStructure();

    @Override
    protected Object clone() throws CloneNotSupportedException {
        Scale scale = (Scale) super.clone();
        scale.formula = (ArrayList<Interval>) formula.clone();
        scale.structure = (ArrayList<Interval>) structure.clone();
        return scale;
    }
}
