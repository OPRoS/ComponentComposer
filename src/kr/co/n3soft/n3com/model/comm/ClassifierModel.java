package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.activity.FinalActiionModel;
import kr.co.n3soft.n3com.model.activity.FinalStrcuturedActivityModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.EnumerationModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;

public class ClassifierModel extends PortContainerModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	public ElementLabelModel name = null;
	ElementLabelModel streotype = null;
	boolean isContainer = false;//2008042901 PKY S 
	public NodeContainerModel boundaryModel = new NodeContainerModel();
	//	public CompartmentManager compartmentManager = new CompartmentManager();
	java.util.List temp_childs = null;
	TypeRefModel classModel = null;
	public int default_width = 112;//2008041702PKY S
	public int default_height = 50;//2008041702PKY S
	boolean isPackage = false;
	boolean isSteroSize = false;//PKY 08081101 S 스트레오 오토사이즈 개선
	N3EditorDiagramModel n3EditorDiagramModel = null;




	public ClassifierModel(int type, int w, int h) {
		classModel = new TypeRefModel(type, w, h);
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		size.width = default_width;
		size.height = default_height;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
		//PKY 08081101 S Timing Group 포함형식으로 변경
//		if(this instanceof GroupModel)
//		this.addContainer(boundaryModel);//PKY 08052101 S 컨테이너에서 그룹으로 변경
		//PKY 08081101 E Timing Group 포함형식으로 변경

	}

	public ClassifierModel() {
		classModel = new TypeRefModel();
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		size.width = default_width;
		size.height = default_height;
		if(this instanceof EnumerationModel){//2008041702PKY S
			size.height =size.height +15;
		}//2008041702PKY E
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
	}

	public ClassifierModel(int w, int h) {
		default_width = w;
		this.default_height = h;
		classModel = new TypeRefModel(w, h / 3);
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		size.width = default_width;
		size.height = default_height;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
	}


	public ClassifierModel(int w, int h,int classW,int classH) {
		default_width = w;
		this.default_height = h;
		classModel = new TypeRefModel(10, 10);
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		size.width = default_width;
		size.height = default_height;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
	}



	public void setParentClassModel(UMLModel p){
		classModel.setParentModel(p);
		classModel.setParentClassModel(p);
		classModel.setAcceptParentModel(p);
	}

//	public ClassifierModel(int w, int h) {
//	default_width = w;
//	this.default_height = h;
//	classModel = new TypeRefModel(w, h / 3);
//	classModel.setParentModel(p);
//	classModel.setParentClassModel(p);
//	size.width = default_width;
//	size.height = default_height;
//	classModel.setLayout(BorderLayout.TOP);
//	classModel.setParentLayout(1);
//	this.addChild(classModel);
//	this.uMLDataModel.setElementProperty("ClassModel", classModel);
//	}

	public ClassifierModel(UMLDataModel p) {
		classModel = (TypeRefModel)p.getElementProperty("ClassModel");
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		NodeContainerModel nodeContainerModel = (NodeContainerModel)p.getElementProperty("NodeContainerModel");
		if (nodeContainerModel != null) {
			this.addContainer(nodeContainerModel);
		}
//		else {
//		this.addContainer(new NodeContainerModel());
//		}
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
//		Dimension tmpSize = (Dimension)p.getElementProperty("Size");
//		if (tmpSize != null) {
//			this.size = tmpSize.getCopy();
//		}
//		;
		
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
		this.setUMLDataModel(p);
	}

	//20080811IJS
	public void setModel(UMLDataModel p){
//		this.removeModel(classModel);
		this.removeModel(classModel);
		classModel = (TypeRefModel)p.getElementProperty("ClassModel");
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		NodeContainerModel nodeContainerModel = (NodeContainerModel)p.getElementProperty("NodeContainerModel");
		if (nodeContainerModel != null) {
			this.addContainer(nodeContainerModel);
		}
//		else {
//		this.addContainer(new NodeContainerModel());
//		}
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		//PKY 08082201 S 저장 불러오기 후 참조된 모델을 움직이면 원 객체의 모델 사이즈로 변경되는문제
//		Dimension tmpSize = (Dimension)p.getElementProperty("Size");
//		if (tmpSize != null) {
//			this.size = tmpSize.getCopy();
//		}
		//PKY 08082201 E 저장 불러오기 후 참조된 모델을 움직이면 원 객체의 모델 사이즈로 변경되는문제

		;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);

		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
		this.setUMLDataModel(p);
		this.setLoadModel(p);
	}

	/*
	 * 프로젝트 읽기시 사용됨
	 */
	public ClassifierModel(UMLDataModel p,boolean isLoad) {
		classModel = (TypeRefModel)p.getElementProperty("ClassModel");
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		NodeContainerModel nodeContainerModel = (NodeContainerModel)p.getElementProperty("NodeContainerModel");
		if (nodeContainerModel != null) {
			this.addContainer(nodeContainerModel);
		}

		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		Dimension tmpSize = (Dimension)p.getElementProperty("Size");
		if (tmpSize != null) {
			this.size = tmpSize.getCopy();
		}
		;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
		this.setLoadModel(p);
	}

	/*
	 * 프로젝트 읽기시 사용됨
	 */
	public void setLoadModel(UMLDataModel p){

		
		//ID
		String value = p.getProperty(UMLModel.ID);
		if(value!=null && !value.trim().equals("")){
			this.getUMLDataModel().setId(value);
		}
		//ID_NAME
		value = p.getProperty(UMLModel.ID_NAME);
		if(value!=null && !value.trim().equals(""))
			this.setName(value);
		//ID_STEREOTYPE
		value = p.getProperty(UMLModel.ID_STEREOTYPE);
		if(value!=null && !value.trim().equals(""))
			this.setStereotype(value);
		//ID_DESCRIPTION
		value = p.getProperty(UMLModel.ID_DESCRIPTION);
		if(value!=null && !value.trim().equals(""))
			this.setDesc(value);
		//ID_SCOPE
		value = p.getProperty(UMLModel.ID_SCOPE);
		if(value!=null && !value.trim().equals(""))
			this.setScope(value);
		//ID_ACTIVE
		value = p.getProperty(UMLModel.ID_ACTIVE);
		if(value!=null && !value.trim().equals(""))
			this.setActive(value);
		//ID_MULTI
		value = p.getProperty(UMLModel.ID_MULTI);
		if(value!=null && !value.trim().equals(""))
			this.setMultiplicity(value);

		//ID_PRECONDITION
		value = p.getProperty(UMLModel.ID_PRECONDITION);
		if(value!=null && !value.trim().equals(""))
			this.setPreCondition(value);

		//ID_POSTCONDITION
		value = p.getProperty(UMLModel.ID_POSTCONDITION);
		if(value!=null && !value.trim().equals(""))
			this.setPostCondition(value);

		//ID_READONLY
		value = p.getProperty(UMLModel.ID_READONLY);
		if(value!=null && !value.trim().equals(""))
			this.setReadOnly(value);

		//ID_PARAMETERNAME
		value = p.getProperty(UMLModel.ID_PARAMETERNAME);
		if(value!=null && !value.trim().equals(""))
			this.setParameterName(value);

		//ID_SINGLEEXECUTION
		value = p.getProperty(UMLModel.ID_SINGLEEXECUTION);
		if(value!=null && !value.trim().equals(""))
			this.setSingleExecution(value);

		//ID_EFFECT
		value = p.getProperty(UMLModel.ID_EFFECT);
		if(value!=null && !value.trim().equals(""))
			this.setEffect(value);
		//ID_CONTEXT
		value = p.getProperty(UMLModel.ID_CONTEXT);
		if(value!=null && !value.trim().equals(""))
			this.setContext(value);
		if(this instanceof FinalStrcuturedActivityModel){
			value = p.getProperty("StrcuturedActivityType");
			if(value!=null && !value.trim().equals(""))
				((FinalStrcuturedActivityModel)this).setType(Integer.valueOf(value).intValue());
		}
		else if(this instanceof FinalActiionModel){
			value = p.getProperty("ActionType");
			if(value!=null && !value.trim().equals(""))
				((FinalActiionModel)this).setType(Integer.valueOf(value).intValue());
		}



		Object obj = p.getElementProperty("ID_DETAIL");
		if(obj!=null && obj instanceof java.util.ArrayList){
			java.util.ArrayList arrest = (java.util.ArrayList)obj;
			if(arrest.size()>0){
				this.setDetailProperty(arrest);
			}
		}
		obj = p.getElementProperty("ID_TAG");
		if(obj!=null && obj instanceof java.util.ArrayList){
			java.util.ArrayList arrest = (java.util.ArrayList)obj;
			if(arrest.size()>0){
				this.setTags(arrest);
			}
		}
		obj = p.getElementProperty("ID_EXTENSIONPOINT");
		if(obj!=null && obj instanceof java.util.ArrayList){
			java.util.ArrayList arrest = (java.util.ArrayList)obj;
			if(arrest.size()>0){
				this.setExtendsPoints(arrest);
			}
		}



	}

	public ClassifierModel(UMLDataModel p, int w, int h) {
		classModel = (TypeRefModel)p.getElementProperty("ClassModel");
		classModel.setParentModel(this);
		classModel.setParentClassModel(this);
		NodeContainerModel nodeContainerModel = (NodeContainerModel)p.getElementProperty("NodeContainerModel");
		if (nodeContainerModel != null) {
			this.addContainer(nodeContainerModel);
		}
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		size.width = w;
		size.height = h;
		;
		classModel.setLayout(BorderLayout.TOP);
		classModel.setParentLayout(1);
		this.addChild(classModel);
		this.uMLDataModel.setElementProperty("ClassModel", classModel);
		this.setUMLDataModel(p);
	}

	public void setViewMulti(boolean p){

	}

	public void getViewMulti(){

	}

	public void setTypeRef(UMLModel p) {
		this.getClassModel().setTypeRef(p);
	}

	public void addContainer(NodeContainerModel p) {
		boundaryModel = p;//this.getSaveContainer();//2008042201PKY S
		boundaryModel.setParentModel(this);
		boundaryModel.setLayout(BorderLayout.CENTER);
		boundaryModel.setParentLayout(1);
		this.addChild(boundaryModel);
		this.uMLDataModel.setElementProperty("NodeContainerModel", boundaryModel);
	}

	public void removeContainer(NodeContainerModel p) {
		this.removeChild(boundaryModel);
		this.uMLDataModel.setElementProperty("NodeContainerModel", null);
	}

	public void setAttrIsBorder(boolean p) {
		this.classModel.attrModel.setIsBorder(p);
	}

	public void setOperIsBorder(boolean p) {
		this.classModel.operModel.setIsBorder(p);
	}

	public TypeRefModel getClassModel() {
		return this.classModel;
	}

	public boolean isChild(UMLElementModel child) {
		return temp_childs.contains(child);
	}

	public void setName(String uname) {
		classModel.setName(uname);
	}

	public void setStereotype(String uname) {
//		V1.20 WJH E 080828 S 널포인트 참조 수정
		if(uname == null)
			return;
         
//		V1.20 WJH E 080828 E 널포인트 참조 수정
		classModel.setStereotype(uname);
		firePropertyChange(this.ID_REFRESH, null, null); //PKY 08052901 S Object Line그려지는문제
		//PKY 08081101 S Requirment 스테레오타입 변경시 사이즈 자동 되지 않도록 수정
		if(!(this instanceof EnumerationModel)){
//			ProjectManager.getInstance().autoSize(classModel);

			if(uname.equals("") && isSteroSize){
				isSteroSize=false;
				this.setSize(new Dimension(this.getSize().width,this.getSize().height-16));
			}else if(!isSteroSize && !uname.equals(""))
			{
				this.setSize(new Dimension(this.getSize().width,this.getSize().height+16));
				isSteroSize =true;
			}
		}

		//PKY 08081101 E Requirment 스테레오타입 변경시 사이즈 자동 되지 않도록 수정

	}

	public String getName() {
		return classModel.getName();
	}

	public String getStereotype() {
		if(classModel!=null){
			String dd = classModel.getStereotype();
			return dd;
		}
		else{
			return "";
		}
	}

	public void addNameAfterModel(UMLModel p) {
		this.classModel.addChild(p, 1);
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
		return getStereotype() + " " + this.getName();
	}

	public void setUMLDataModel(UMLDataModel p) {
		uMLDataModel = p;
	}

	public void setTreeModel(UMLTreeModel p) {
		super.setTreeModel(p);
		classModel.name.setTreeModel(p);
	}

	public NodeContainerModel getBoundaryModel() {
		return this.boundaryModel;
	}

	public Object clone() {
		ClassifierModel clone = new ClassifierModel((UMLDataModel)this.getUMLDataModel().clone());
		for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
			UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
			clone.boundaryModel.addChild((UMLElementModel)um.clone());
		}
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

	public boolean isPackage() {
		return isPackage;
	}

	public void setPackage(boolean isPackage) {
		this.isPackage = isPackage;
	}

	public N3EditorDiagramModel getN3EditorDiagramModel() {
		return n3EditorDiagramModel;
	}

	public void setN3EditorDiagramModel(N3EditorDiagramModel editorDiagramModel) {
		n3EditorDiagramModel = editorDiagramModel;
	}//2008042901 PKY S 

	public boolean isContainer() {
		return isContainer;
	}

	public void setContainer(boolean isContainer) {
		this.isContainer = isContainer;
	}
	//2008042901 PKY E
	//PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
	public void refresh(){
		firePropertyChange(this.ID_REFRESH, null, null);
	}
	//PKY 08080501 E 사용자가 컬러색상을 수정 할 수있도록 수정
}
