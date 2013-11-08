package kr.co.n3soft.n3com.model.composite;

import java.util.Vector;
import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;

public class DelegateLineModel extends LineModel {
	
    public Object clone() {
        DelegateLineModel clone = new DelegateLineModel();
        java.util.List list = this.getBendpoints();
        java.util.Vector cloneList = new Vector();
        for (int i = 0; i < list.size(); i++) {
            LineBendpointModel lineBendpointModel = (LineBendpointModel)list.get(i);
            cloneList.add(lineBendpointModel.clone());
        }
        clone.setSourceTerminal(this.getSourceTerminal());
        clone.setTargetTerminal(this.getTargetTerminal());
        clone.setBendpoints(cloneList);
        clone.setOldSource(this.getSource());
        clone.setOldTraget(this.getTarget());
        return clone;
    }
}
