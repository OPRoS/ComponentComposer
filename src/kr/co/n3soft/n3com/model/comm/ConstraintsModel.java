package kr.co.n3soft.n3com.model.comm;

public class ConstraintsModel {
    String guard = "";
    String weight = "";

    public ConstraintsModel(String g, String w) {
        this.guard = g;
        this.weight = w;
    }

    public ConstraintsModel() {
    }

    public String getGuard() {
        return this.guard;
    }

    public void setGuard(String p) {
        this.guard = p;
    }

    public String getWeight() {
        return this.weight;
    }

    public void setWeight(String p) {
        this.weight = p;
    }

    public ConstraintsModel clone() {
        ConstraintsModel clone = new ConstraintsModel(guard, weight);
        return clone;
    }
    
    public String toString() {
        return ""; //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
