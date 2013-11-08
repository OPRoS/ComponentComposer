package kr.co.n3soft.n3com.model.comm;

public class RoleModel {
    String aggregation = "";
    String navigation = "";
    String multiplicity = "";
    String guard = "";
    String weight = "";
    Integer aggregation_i = new Integer(0);
    Integer navigation_i = new Integer(0);
    Integer multiplicity_i = new Integer(0);

    public RoleModel(Integer agg, Integer na, Integer mu) {
        this.aggregation_i = agg;
        this.navigation_i = na;
        this.multiplicity_i = mu;
    }

    public RoleModel(String g, String w) {
        this.guard = g;
        this.weight = w;
    }

    public RoleModel() {
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

    public Integer getAggregation() {
        return aggregation_i;
    }

    public Integer getNavigation() {
        return navigation_i;
    }

    public Integer getMultiplicity() {
        return multiplicity_i;
    }

    public void setAggregation(Integer p) {
        aggregation_i = p;
    }

    public void setNavigation(Integer p) {
        navigation_i = p;
    }

    public void setMultiplicity(Integer p) {
        multiplicity_i = p;
    }

    public RoleModel clone() {
        RoleModel clone = new RoleModel(aggregation_i, navigation_i, multiplicity_i);
        return clone;
    }
    public String toString() {
        return ""; //$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
    }
}
