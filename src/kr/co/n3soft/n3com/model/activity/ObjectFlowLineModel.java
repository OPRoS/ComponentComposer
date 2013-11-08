package kr.co.n3soft.n3com.model.activity;

import java.util.Vector;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
//PKY 08072401 S ObjectFlow¸ðµ¨ Ãß°¡
public class ObjectFlowLineModel extends ControlFlowLineModel {
	public Object clone() {
		ObjectFlowLineModel clone = new ObjectFlowLineModel();
        java.util.List list = this.getBendpoints();
        java.util.Vector cloneList = new Vector();
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
