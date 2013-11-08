package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.PortModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class ActivityParameterModel extends PortModel {
    public ActivityParameterModel() {
        name = new ElementLabelModel();
        name.setType("NAME");
        this.size.height = 15;
        this.size.width = 30;
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public ActivityParameterModel(UMLDataModel p) {
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.setAttachElementLabelModel(name);
        this.setUMLDataModel(p);
    }

    public ActivityParameterModel(PortContainerModel p) {
        this.size.height = 15;
        this.size.width = 30;
        this.portCm = p;
        name = new ElementLabelModel();
        name.setType("NAME");
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public Object clone() {
        ActivityParameterModel clone = new ActivityParameterModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        return clone;
    }
}
