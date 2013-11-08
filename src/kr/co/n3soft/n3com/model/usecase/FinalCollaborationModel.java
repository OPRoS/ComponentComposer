package kr.co.n3soft.n3com.model.usecase;

import kr.co.n3soft.n3com.model.activity.ActivityModel;
import kr.co.n3soft.n3com.model.activity.FinalActivityModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.PortContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class FinalCollaborationModel extends PortContainerModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    ActivityModel activityModel = null;
    java.util.List temp_childs = null;

    public FinalCollaborationModel() {
        activityModel = new ActivityModel();
        activityModel.setParentModel(this);
        activityModel.setLayout(BorderLayout.CENTER);
        activityModel.setParentLayout(1);
        this.addChild(activityModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = 100;
        size.height = 50;
        name = new ElementLabelModel(PositionConstants.TOP);
        name.setType("NAME");
        //		name.setBackGroundColor(this.backGroundColor);
        //		name.setLabelContents("액티비티");
        name.setLayout(BorderLayout.TOP);
        name.setParentLayout(1);
        this.addChild(name);
        this.uMLDataModel.setElementProperty(name.getType(), name);
    }

    public FinalCollaborationModel(UMLDataModel p) {
        activityModel = new ActivityModel();
        activityModel.setParentModel(this);
        activityModel.setLayout(BorderLayout.CENTER);
        activityModel.setParentLayout(1);
        this.addChild(activityModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = 100;
        size.height = 50;
        name = (ElementLabelModel)p.getElementProperty("NAME");
        this.addChild(name);
        this.setUMLDataModel(p);
    }

    public boolean isChild(UMLElementModel child) {
        return temp_childs.contains(child);
    }

    public void setName(String uname) {
        name.setLabelContents(uname);
    }

    public void setStreotype(String uname) {
        streotype.setLabelContents(uname);
    }

    public String getName() {
        return name.getLabelContents();
    }

    public String getStreotype() {
        return "<<" + streotype.getLabelContents() + ">>";
    }

    public static String TERMINALS_OUT[] = new String[] {
        "1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "5", "6", "7", "8"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public static String TERMINALS_IN[] = new String[] {
        "A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "E", "F", "G", "H"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public void update() {
    }

    public String toString() {
        return getStreotype() + " " + this.getName();
    }

    public void setUMLDataModel(UMLDataModel p) {
        uMLDataModel = p;
    }

    public void setTreeModel(UMLTreeModel p) {
        super.setTreeModel(p);
        name.setTreeModel(p);
    }

    public ActivityModel getBoundaryModel() {
        return this.activityModel;
    }

    public Object clone() {
        FinalCollaborationModel clone = new FinalCollaborationModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.activityModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.activityModel.getChildren().get(i);
//            clone.activityModel.addChild((UMLElementModel)um.clone());
//        }
        clone.setSize(this.getSize());
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
