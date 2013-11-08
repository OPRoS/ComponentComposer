package kr.co.n3soft.n3com.model.umlclass;

import java.util.HashMap;
import java.util.Vector;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;

public class MergeLineModel extends LineModel {
    public MergeLineModel() {
    }

    public Object clone() {
    	MergeLineModel clone = new MergeLineModel();
        java.util.List list = this.getBendpoints();
        java.util.Vector cloneList = new Vector();
        clone.setDetailProp((HashMap)this.getDetailProp().clone());
        for (int i = 0; i < list.size(); i++) {
            LineBendpointModel lineBendpointModel = (LineBendpointModel)list.get(i);
            cloneList.add(lineBendpointModel.clone());
        }
        clone.setSourceTerminal(this.getSourceTerminal());
        clone.setTargetTerminal(this.getTargetTerminal());
        clone.setBendpoints(cloneList);
        clone.setOldSource(this.getSource());
        clone.setOldTraget(this.getTarget());
        clone.middleLineTextModel = (LineTextModel)this.middleLineTextModel.clone();
        clone.targetLineTextModel = (LineTextModel)this.targetLineTextModel.clone();
        clone.sourceLineTextModel = (LineTextModel)this.sourceLineTextModel.clone();
        return clone;
    }
}