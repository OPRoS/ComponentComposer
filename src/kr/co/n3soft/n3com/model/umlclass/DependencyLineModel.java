package kr.co.n3soft.n3com.model.umlclass;

import java.util.HashMap;
import java.util.Vector;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.usecase.AssociateLineModel;

public class DependencyLineModel extends LineModel {
    public Object clone() {
        DependencyLineModel clone = new DependencyLineModel();
        java.util.List list = this.getBendpoints();
        java.util.Vector cloneList = new Vector();
        clone.setDetailProp((HashMap)this.getDetailProp().clone());
        for (int i = 0; i < list.size(); i++) {
            LineBendpointModel lineBendpointModel = (LineBendpointModel)list.get(i);
            cloneList.add(lineBendpointModel.clone());
        }
        //		clone.setSource(this.getSource());
        //		clone.setTarget(this.getTarget());
        clone.setSourceTerminal("A" + this.hashCode());
        clone.setTargetTerminal("B" + this.hashCode());
        clone.setBendpoints(cloneList);
        clone.setOldSource(this.getSource());
        clone.setOldTraget(this.getTarget());
        return clone;
    }
}
