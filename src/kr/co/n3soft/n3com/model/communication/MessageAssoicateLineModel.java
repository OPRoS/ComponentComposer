package kr.co.n3soft.n3com.model.communication;

import java.util.HashMap;
import java.util.Vector;

import kr.co.n3soft.n3com.model.comm.LineBendpointModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.LineTextModel;
import kr.co.n3soft.n3com.model.usecase.ExtendLineModel;

public class MessageAssoicateLineModel extends LineModel {
	

    public MessageAssoicateLineModel() {
       
    }

    public Object clone() {
    	MessageAssoicateLineModel clone = new MessageAssoicateLineModel();
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
