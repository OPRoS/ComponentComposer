package kr.co.n3soft.n3com.model.deployment;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class NodeModel extends ClassifierModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    NodeContainerModel actionModel = null;
    CompartmentModel attrModel = null;
    java.util.List temp_childs = null;
    UMLModel cm = null;
    Integer type = new Integer(-1);
    BorderContainlModel borderContainlModel = null;
    private UMLModel callBeavior = null;
    public int default_width = 100;
    public int default_height = 50;

    public NodeModel() {
        super(100, 120);
        //		default_width = 80;
        //		default_height = 50;
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
//        this.getClassModel().setComponent(true);
        //		this.setStereotype("interface");
    }
    
    public NodeModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }

    //	public FinalActiionModel(){
    ////		attrModel = new CompartmentModel();
    //		
    //		actionModel = new NodeContainerModel();
    //		actionModel.setParentModel(this);
    //		actionModel.setLayout(BorderLayout.CENTER);
    //		actionModel.setParentLayout(1);
    //		this.addChild(actionModel);
    //		streotype= new ElementLabelModel(PositionConstants.CENTER);
    //		size.width = 80;
    //		size.height= 50;
    //		name = new ElementLabelModel(PositionConstants.CENTER);
    //		name.setType("NAME");
    //		borderContainlModel = new BorderContainlModel();
    //		borderContainlModel.setParentModel(this);
    //		borderContainlModel.setLayout(BorderLayout.TOP);
    //		borderContainlModel.setParentLayout(1);
    //		borderContainlModel.setCenterModel(name);
    //		this.addChild(borderContainlModel);
    //		this.uMLDataModel.setElementProperty(String.valueOf(BorderLayout.CENTER), name);
    //	
    //		
    //		
    //
    //	}
    public NodeModel(UMLDataModel p) {
        super(p);
        //		this.callBeavior = (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
//        this.getClassModel().setComponent(true);
        this.setUMLDataModel(p);
    }

    public Object clone() {
    	NodeModel clone = new NodeModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
        }
        clone.setPortManager(this.getPortManager().clone(clone));
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