package kr.co.n3soft.n3com.model.activity;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;

public class FinalActiionModel extends ClassifierModel {
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
    public int default_width = 80;
    public int default_height = 50;

    public FinalActiionModel() {
        super(80, 50);
        //		default_width = 80;
        //		default_height = 50;
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
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
    
    public FinalActiionModel(UMLDataModel p,boolean isLoad) {
        super(p,isLoad);
//        this.setUMLDataModel(p);
    }
    public void setType(int p) {
        this.type = p;
        this.uMLDataModel.setElementProperty("ActionType", String.valueOf(type));
      //PKY 08072401 S Action Action 저장 불러오기시 그림이 안되는문제   주석 처리
/*        if (p == 1) {
        }
        else if (p == 2) {
//            cm = new CallBehaviorModel();
//            cm.setParentModel(this);
//            cm.setLayout(BorderLayout.BOTTOM);
//            cm.setParentLayout(1);
//            this.addChild(cm,1);
        }
        else if (p == 3) {
            cm = new CallOperationModel();
            cm.setParentModel(this);
            //			cm.setLayout(BorderLayout.BOTTOM);
            //			cm.setParentLayout(1);
            //			borderContainlModel.setBottomModel(cm);
            //			this.addChild(cm,1);
            this.addNameAfterModel(cm);
        }*/
        //PKY 08072401 E Action Action 저장 불러오기시 그림이 안되는문제   주석 처리 
    }

    public int getType() {
        String value = (String)this.uMLDataModel.getElementProperty("ActionType");
        if (value == null)
            return -1;
        this.type = Integer.valueOf(value);
        if (this.type != null) {
            return this.type.intValue();
        }
        else {
            return -1;
        }
    }

    public void setCallBeavior(UMLModel um) {
        this.callBeavior = um;
        this.uMLDataModel.setElementProperty("callBeavior", um);
        this.setName(this.getName());
    }

    public UMLModel getCallBeavior() {
        return (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
    }

    public FinalActiionModel(UMLDataModel p) {
        super(p);
        this.callBeavior = (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
        this.setAttrIsBorder(false);
        this.setOperIsBorder(false);
        this.setUMLDataModel(p);
    }

    public boolean isChild(UMLElementModel child) {
        return temp_childs.contains(child);
    }

    public void setName(String uname) {
        if (uname != null) {
            if (this.callBeavior != null) {
                String p = ":" + this.callBeavior.getName();
                uname = uname.replaceAll(this.callBeavior.getName(), "");
                uname = uname + p;
            }
        }
        //		name.setLabelContents(uname);
        super.setName(uname);
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

    public Object clone() {
        FinalActiionModel clone = new FinalActiionModel((UMLDataModel)this.getUMLDataModel().clone());
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
        clone.setSize(this.getSize());
        clone.setLocation(this.getLocation());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.sourceModel = this;
        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
            LineModel li = (LineModel)this.getSourceConnections().get(i1);
            LineModel liCopy = (LineModel)li.clone();
            ProjectManager.getInstance().addSelectLineModel(liCopy);
        }
        clone.setType(clone.getType());
        return clone;
    }
}
