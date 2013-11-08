package kr.co.n3soft.n3com.model.usecase;

import java.util.HashMap;
import java.util.Vector;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;

public class AssociateLineModel extends LineModel {
    public Object clone() {
    	
    	
        AssociateLineModel clone = new AssociateLineModel();
        clone.setDetailProp((HashMap)this.getDetailProp().clone());
        java.util.List list = this.getBendpoints();
        java.util.Vector cloneList = new Vector();
        for (int i = 0; i < list.size(); i++) {
            LineBendpointModel lineBendpointModel = (LineBendpointModel)list.get(i);
            cloneList.add(lineBendpointModel.clone());
        }
        //		clone.setSource(this.getSource());
        //		clone.setTarget(this.getTarget());
        clone.setSourceTerminal(this.getSourceTerminal());
        clone.setTargetTerminal(this.getTargetTerminal());
        clone.setBendpoints(cloneList);
        clone.setOldSource(this.getSource());
        clone.setOldTraget(this.getTarget());
       
        return clone;
    }
}
