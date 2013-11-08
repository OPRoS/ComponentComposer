package kr.co.n3soft.n3com.model.usecase;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;

public class FinalBoundryModel extends ClassifierModel {
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
  //ijs080429
//    public  Image LOGIC_ICON = createImage(FinalBoundryModel.class, "icons/boundary.gif"); //$NON-NLS-1$

    public FinalBoundryModel() {
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
    public FinalBoundryModel(UMLDataModel p) {
        super(p);
//        NodeContainerModel pp =(NodeContainerModel)this.uMLDataModel.getElementProperty("NodeContainerModel");
//       if(pp!=null){
//    	  this.boundaryModel = pp;
//       }
          this.setUMLDataModel(p);
    }

    public Object clone() {
        FinalBoundryModel clone = new FinalBoundryModel((UMLDataModel)this.getUMLDataModel().clone());
      
        ProjectManager.getInstance().setAddLine(false);
        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
          
            clone.boundaryModel.addChild((UMLElementModel)um.clone());
            
        }
        ProjectManager.getInstance().setAddLine(true);
        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
//        clone.sourceModel = this;
//        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
//        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
//            LineModel li = (LineModel)this.getSourceConnections().get(i1);
//            LineModel liCopy = (LineModel)li.clone();
//            ProjectManager.getInstance().addSelectLineModel(liCopy);
//        }
        return clone;
    }
    public Image getIconImage() {
        return LOGIC_ICON;
    }
}
