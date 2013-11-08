package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.activity.ActivityParameterModel;
import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.geometry.Dimension;
import org.eclipse.draw2d.geometry.Point;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.swt.events.MouseEvent;

public class EntryPointModel extends PortModel {
    public EntryPointModel() {
        name = new ElementLabelModel();
        name.setType("NAME");
        this.size.height = 15;
        this.size.width = 15;
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public EntryPointModel(UMLDataModel p) {
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.setAttachElementLabelModel(name);
        this.setUMLDataModel(p);
    }

    public EntryPointModel(PortContainerModel p) {
        this.size.height = 15;
        this.size.width = 15;
        this.portCm = p;
        name = new ElementLabelModel();
        name.setType("NAME");
        this.setAttachElementLabelModel(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public Object clone() {
        EntryPointModel clone = new EntryPointModel((UMLDataModel)this.getUMLDataModel().clone());
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
