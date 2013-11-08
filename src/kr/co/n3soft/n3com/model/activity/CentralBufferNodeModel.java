package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.BarModel;
import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;

public class CentralBufferNodeModel extends ClassifierModel {
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
    BarModel barModel = new BarModel();

    public CentralBufferNodeModel() {
        super(100, 50);
        //		default_width = 80;
        //		default_height = 50;
        this.setStereotype("centralBuffer");//PKY 08090801 S 
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
//        this.getClassModel().addChild((UMLModel)barModel, 1);
//        barModel.setAcceptParentModel(this);
       
    }
    public CentralBufferNodeModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }
    public CentralBufferNodeModel(UMLDataModel p) {
        super(p);
        //		this.callBeavior = (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        this.setUMLDataModel(p);
//        this.getClassModel().addChild((UMLModel)barModel, 1);
//        barModel.setAcceptParentModel(this);
    }
    
    public void setMultiplicity(String p){
    	uMLDataModel.setProperty(this.ID_MULTI, p);
    	firePropertyChange(ID_MULTI, null, null); //$NON-NLS-1$
   
    }
    
    public String getMultiplicity(){
    	if(uMLDataModel.getProperty(this.ID_MULTI)==null){
    		this.setMultiplicity("");
    	}
    	return uMLDataModel.getProperty(this.ID_MULTI);
    }

    public Object clone() {
    	CentralBufferNodeModel clone = new CentralBufferNodeModel((UMLDataModel)this.getUMLDataModel().clone());
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
