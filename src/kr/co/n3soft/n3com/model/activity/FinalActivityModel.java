package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.PartitionModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class FinalActivityModel extends PartitionModel {
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
    private boolean isFinalStrcutured = false;
    

    public FinalActivityModel(int w,int h) {
    	 super(w, h);
    }

    public FinalActivityModel() {
        super(100, 50);
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
    }

    public FinalActivityModel(UMLDataModel p) {
        super(p);
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        this.setUMLDataModel(p);
    }
    
    public FinalActivityModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }

    public Object clone() {
        FinalActivityModel clone = new FinalActivityModel((UMLDataModel)this.getUMLDataModel().clone());

//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
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

	public boolean isFinalStrcutured() {
		return isFinalStrcutured;
	}

	public void setFinalStrcutured(boolean isFinalStrcutured) {
		this.isFinalStrcutured = isFinalStrcutured;
	}


}
