package kr.co.n3soft.n3com.model.usecase;

import org.eclipse.swt.graphics.Image;

import kr.co.n3soft.n3com.model.comm.BorderContainlModel;
import kr.co.n3soft.n3com.model.comm.ClassifierModel;
import kr.co.n3soft.n3com.model.comm.ElementLabelModel;
import kr.co.n3soft.n3com.model.comm.LineModel;
import kr.co.n3soft.n3com.model.comm.NodeContainerModel;
import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.model.comm.UMLElementModel;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class UseCaseModel extends ClassifierModel {
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
	//ijs080429
//	public  Image LOGIC_ICON = createImage(UseCaseModel.class, "icons/usecase.gif"); //$NON-NLS-1$
//	java.util.ArrayList scenario = new java.util.ArrayList();
	public UseCaseModel() {
		super(100, 50);
		//20080721IJS 아래사항 제거
//		LOGIC_ICON = createImage(UseCaseModel.class, "icons/usecase.gif"); //$NON-NLS-1$
		//		default_width = 80;
		//		default_height = 50;
		this.setAttrIsBorder(false);
		this.setOperIsBorder(false);
	}
	
	 public UseCaseModel(UMLDataModel p,boolean isLoad) {
	        super(p,isLoad);
//	        this.setUMLDataModel(p);
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
	public UseCaseModel(UMLDataModel p) {
		super(p, 100, 50);
		//		this.callBeavior = (UMLModel)this.uMLDataModel.getElementProperty("callBeavior");
		this.setAttrIsBorder(false);
		this.setOperIsBorder(false);

		this.setUMLDataModel(p);
	}

	public Object clone() {
		UseCaseModel clone = new UseCaseModel((UMLDataModel)this.getUMLDataModel().clone());
//		//		clone.temp_childs = this.activityModel.getChildren();
//		for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//		UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//		clone.boundaryModel.addChild((UMLElementModel)um.clone());
//		}
		clone.setPortManager(this.getPortManager().clone(clone));
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
		return clone;
	}
	public Image getIconImage() {
		return LOGIC_ICON;
	}
}
