package kr.co.n3soft.n3com.model.activity;

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
import kr.co.n3soft.n3com.model.usecase.BoundaryModel;
import kr.co.n3soft.n3com.model.usecase.FinalBoundryModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.swt.graphics.Color;

public class HPartitionModel extends ClassifierModel {
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
    public int default_height = 200;

    public HPartitionModel() {
        super(1, 100, 30);
        size.width = 200;
        size.height = 400;
        this.setBackGroundColor(new Color(null,255,255,255));//PKY 08090907 S
        //		this.setAttrIsBorder(false);
        //		this.setOperIsBorder(false);
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
    public HPartitionModel(UMLDataModel p) {
    	 super(1, 100, 30);//PKY 08081101 S 스윔레인 복사 시 붙여넣으면 모양이 다름
        size.width = 100;
        size.height = 400;
        //		this.setAttrIsBorder(false);
        //		this.setOperIsBorder(false);
//        p.getProperty(key)
        this.setUMLDataModel(p);

    }

    public Object clone() {
        HPartitionModel clone = new HPartitionModel((UMLDataModel)this.getUMLDataModel().clone());
//        this.boundaryModel = clone.getBoundaryModel();
        //		clone.temp_childs = this.activityModel.getChildren();
        ProjectManager.getInstance().setAddLine(false);
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
          
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
            
        }
        ProjectManager.getInstance().setAddLine(true);
        
        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setSize(this.getSize());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
        clone.setStereotype(this.getStereotype());//PKY 08081101 S 스윔레인 복사 시 붙여넣으면 모양이 다름
//        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
//        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
//            LineModel li = (LineModel)this.getSourceConnections().get(i1);
//            LineModel liCopy = (LineModel)li.clone();
//            ProjectManager.getInstance().addSelectLineModel(liCopy);
//        }
        return clone;
    }
}
