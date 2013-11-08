package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.model.diagram.UMLDiagramModel;
import kr.co.n3soft.n3com.model.umlclass.AttributeEditableTableItem;
import kr.co.n3soft.n3com.model.umlclass.CompartmentModel;
import kr.co.n3soft.n3com.model.umlclass.OperationEditableTableItem;
import kr.co.n3soft.n3com.model.usecase.ActorModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.PositionConstants;

public class ClassModel extends UMLDiagramModel {
	static final long serialVersionUID = 1;
	public static int count = 0;
	ElementLabelModel name = null;
	ElementLabelModel streotype = null;
	ElementLabelModel emtpyElement = new ElementLabelModel(true);	//110823 SDM - 수정>>공백 생성시 수정불가 상태로 생성
	ActorModel actorModel = null;
	CompartmentModel attrModel = null;
	CompartmentModel operModel = null;
	java.util.ArrayList attributes = new java.util.ArrayList();
	java.util.ArrayList operations = new java.util.ArrayList();
	boolean isMode = true;
	boolean isComponent = false;
	boolean isArtiface = false;
	boolean isViewMulti = false;
	String multi = "";

	static {
		try {
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	public void setMulti(String p){
		multi = p;

	}

	public void setViewMulti(boolean p){
		isViewMulti = p;

	}

	public boolean getViewMulti(){
		return isViewMulti;
	}

	public OperationEditableTableItem searchOperationEditableTableItem(String value){
		for(int i=0;i<this.operations.size();i++){
			OperationEditableTableItem opem = (OperationEditableTableItem)operations.get(i);
			if(opem.toMessageString().equals(value)){
				return opem;
			}

		}
		return null;
	}



	public ClassModel(int type, int w, int h) {
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		size.width = 100;
		size.height = 30;
		name = new ElementLabelModel(PositionConstants.CENTER);
		name.setType("NAME");
		this.addChild(name);
		this.uMLDataModel.setElementProperty(name.getType(), name);
		attrModel = new CompartmentModel();
		attrModel.setCompartmentModelType("ATTR");
		attrModel.setParentModel(this.getParentModel());
		operModel = new CompartmentModel();
		operModel.setCompartmentModelType("OPER");
		operModel.setParentModel(this.getParentModel());
		this.uMLDataModel.setElementProperty(attrModel.getCompartmentModelType(), attrModel);
		this.uMLDataModel.setElementProperty(operModel.getCompartmentModelType(), operModel);
		this.uMLDataModel.setElementProperty("ATTR_A", attributes);
		this.uMLDataModel.setElementProperty("OPER_A", operations);
	}

	public ClassModel(int w, int h) {
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		size.width = w;
		size.height = h;
		name = new ElementLabelModel(PositionConstants.CENTER);
		name.setType("NAME");
		this.addChild(name);
		attrModel = new CompartmentModel();
		attrModel.setCompartmentModelType("ATTR");
		attrModel.setParentModel(this.getParentModel());
		this.addChild(attrModel);
		operModel = new CompartmentModel();
		operModel.setCompartmentModelType("OPER");
		operModel.setParentModel(this.getParentModel());
		this.addChild(operModel);
		this.uMLDataModel.setElementProperty(name.getType(), name);
		this.uMLDataModel.setElementProperty(attrModel.getCompartmentModelType(), attrModel);
		this.uMLDataModel.setElementProperty(operModel.getCompartmentModelType(), operModel);
		this.uMLDataModel.setElementProperty("ATTR_A", attributes);
		this.uMLDataModel.setElementProperty("OPER_A", operations);
	}

	public ClassModel() {
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		streotype.setType("STREOTYPE");
		size.width = 100;
		size.height = 45;
		name = new ElementLabelModel(PositionConstants.CENTER);
		name.setType("NAME");
		this.addChild(name);
		attrModel = new CompartmentModel();
		attrModel.setCompartmentModelType("ATTR");
		attrModel.setParentModel(this.getParentModel());
		this.addChild(attrModel);
		operModel = new CompartmentModel();
		operModel.setCompartmentModelType("OPER");
		operModel.setParentModel(this.getParentModel());
		this.addChild(operModel);
		this.uMLDataModel.setElementProperty(name.getType(), name);
		this.uMLDataModel.setElementProperty(attrModel.getCompartmentModelType(), attrModel);
		this.uMLDataModel.setElementProperty(operModel.getCompartmentModelType(), operModel);
		this.uMLDataModel.setElementProperty("ATTR_A", attributes);
		this.uMLDataModel.setElementProperty("OPER_A", operations);
	}


	//PKY 08081801 S 오퍼레이션 어트리뷰트 숨길수 있는 기능
	public void setShowAttr(boolean p){
			if(p){

				java.util.ArrayList arrayItem  = (java.util.ArrayList) this.getChildren();
				boolean isOper = false;
				for(int i = 0 ; i < arrayItem.size(); i ++){
					if(arrayItem.get(i) instanceof CompartmentModel){
						CompartmentModel compart = (CompartmentModel)arrayItem.get(i);
						if(compart.getCompartmentModelType().equals("OPER")){
							isOper=true;
							this.removeChild(operModel);
							this.addChild(attrModel);	
							this.addChild(operModel);
							i = arrayItem.size();
						}
					}
				}
				if(!isOper)
					this.addChild(attrModel);

			}else
				this.removeChild(attrModel);

	}
	public void setShowOper(boolean p){

		if(p){
			this.addChild(operModel);	
		}else
			this.removeChild(operModel);
		
	}
	//PKY 08081801 E 오퍼레이션 어트리뷰트 숨길수 있는 기능

	public void setMode(boolean p) {
		this.isMode = p;
	}

	public void setComponent(boolean p) {
		this.isComponent = p;
	}

	public boolean getComponent() {
		return this.isComponent;
	}

	public void setIsArtiface(boolean p) {
		this.isArtiface = p;
	}

	public boolean getIsArtiface() {
		return this.isArtiface;
	}

	//라인 연결시 부모에게 연결시켜야 하는 경우
	public void setParentClassModel(UMLModel p) {
		attrModel.setParentModel(p);
		operModel.setParentModel(p);
	}
	
	public void setEmptyElement(){
//		if("<<atomic>>".equals(this.getStereotype())){
			this.addChild(emtpyElement,0);
//		}
	}

	public void setStereotype(String type) {
		try {
			if (!this.isMode)
				return;
			if("atomic".equals(type)){
	        	 this.setComponent(false);
	         }
			streotype.setType("STREOTYPE");
			if (type != null && type.trim().length() > 0) {
				if (this.uMLDataModel.getElementProperty(streotype.getType()) == null) {
					this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
					//				this.removeChild(streotype);

					streotype.setLabelContents(type);
					
					this.addChild(streotype, 0); 
					this.setEmptyElement();
					if("atomic".equals(type) || "<<atomic>>".equals(type)){	//110816 SDM - save>>load시 아토믹 컴포넌트의 <<atomic>>부분이 밀리는 현상 수정
//			        	 this.setComponent(false);
			        	 this.setEmptyElement();
			         }
				}
				else{
					streotype.setLabelContents(type);
				}

//				type = "<<" + type + ">>";
//				if (!streotype.getLabelContents().equals(type)) {
//				streotype.setLabelContents(type);
//				try {
//				if (this.uMLDataModel.getElementProperty(streotype.getType()) == null) {
//				borderContainlModel.removeChild(streotype);
//				}
//				}
//				catch (Exception e) {
//				e.printStackTrace();
//				}
//				this.uMLDataModel.setElementProperty(streotype.getType(), streotype);
//				//				this.removeChild(streotype);
//				borderContainlModel.addChild(streotype, 0);
//				}
			}
			else {
				if (this.uMLDataModel.getElementProperty(streotype.getType()) != null) {
					this.removeChild(streotype);
					streotype.setLabelContents("");
				}
				//			this.removeChild(streotype);
				this.uMLDataModel.setElementProperty(streotype.getType(), null);
			}
			this.setName(this.getName());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}

	//	public java.util.ArrayList cloneAttributes(){
	//		java.util.ArrayList attributesClone = new java.util.ArrayList();
	//		for(int i=0;i<this.attributes.size();i++){
	//			AttributeEditableTableItem attrItem = (AttributeEditableTableItem)this.attributes.get(i);
	//			attributesClone.add(attrItem.clone());
	//		}
	//		this.setAttributes(attributesClone);
	//		return attributesClone;
	//		
	//	}
	//	
	//	public java.util.ArrayList cloneOperations(){
	//		java.util.ArrayList operationsClone = new java.util.ArrayList();
	//		for(int i=0;i<this.operations.size();i++){
	//			OperationEditableTableItem attrItem = (OperationEditableTableItem)this.operations.get(i);
	//			operationsClone.add(attrItem.clone());
	//		}
	//		this.setOperations(operationsClone);
	//		return operations;
	//		
	//	}
	public ClassModel(UMLDataModel p) {
		streotype = new ElementLabelModel(PositionConstants.CENTER);
		size.width = 100;
		size.height = 45;
		ElementLabelModel streotypeTemp = (ElementLabelModel)p.getElementProperty("STREOTYPE");
		if (streotypeTemp != null) {
			this.setEmptyElement();
		
			streotype = streotypeTemp;
			if(streotype.getLabelContents().equals("<<atomic>>"))
				this.setEmptyElement();
			
			this.addChild(streotype);
		}
		name = (ElementLabelModel)p.getElementProperty("NAME");
		this.addChild(name);
		attrModel = (CompartmentModel)p.getElementProperty("ATTR");
		attrModel.setParentModel(this.getParentModel());
		attrModel.setCompartmentModelType("ATTR");
		this.addChild(attrModel);
		operModel = (CompartmentModel)p.getElementProperty("OPER");
		operModel.setParentModel(this.getParentModel());
		operModel.setCompartmentModelType("OPER");
		this.addChild(operModel);
		name = (ElementLabelModel)p.getElementProperty("NAME");
		attributes = (java.util.ArrayList) p.getElementProperty("ATTR_A");
		operations = (java.util.ArrayList) p.getElementProperty("OPER_A");
		this.uMLDataModel.setElementProperty(name.getType(), name);
		this.uMLDataModel.setElementProperty(attrModel.getCompartmentModelType(), attrModel);
		this.uMLDataModel.setElementProperty(operModel.getCompartmentModelType(), operModel);
		this.uMLDataModel.setElementProperty("ATTR_A", attributes);
		this.uMLDataModel.setElementProperty("OPER_A", operations);
		this.setCloneAttribute();
		this.setCloneOperation();
		this.setUMLDataModel(p);
	}
	/*
	 * 프로젝트 읽기시 사용됨
	 */
	public void setLoadModel(UMLDataModel p){
		//어튜리뷰트 추가
		Object obj = p.getElementProperty("ATTR_A");
		if(obj!=null && obj instanceof java.util.ArrayList){
			this.setAttributes((java.util.ArrayList)obj);
		}
		obj = p.getElementProperty("OPER_A");
		//오퍼레이션 추가
		if(obj!=null && obj instanceof java.util.ArrayList){
			this.setOperations((java.util.ArrayList)obj);
		}

	}

	public java.util.ArrayList getAttributes() {
		return this.attributes;
	}

	public java.util.ArrayList getOperations() {
		return this.operations;
	}

	public void setAttributes(java.util.ArrayList attr) {
		this.attrModel.removeAll();
		this.setAttribute(attr);
	}

	public void setOperations(java.util.ArrayList attr) {
		this.operModel.removeAll();
		this.setOperation(attr);
	}

	public void setActorModel(ActorModel p) {
		this.actorModel = p;
	}

	public ActorModel getActorModel() {
		return this.actorModel;
	}

	public void setName(String uname) {

		name.setLabelContents(uname);

		
	      //PKY 08091003 S
			UMLModel um =	(UMLModel)this.getAcceptParentModel();
			UMLTreeModel ut = this.getUMLTreeModel();
			if(ut!=null){
		        	for(int j = 0; j < ut.getIN3UMLModelDeleteListeners().size(); j ++){
		        		N3EditorDiagramModel n3EditDiagrma = (N3EditorDiagramModel)ut.getIN3UMLModelDeleteListeners().get(j);
		        		for(int i=0; i<n3EditDiagrma.frameModels.size();i++){
		        			FrameModel fm = (FrameModel)n3EditDiagrma.frameModels.get(i);
		        			fm.refreshDiagram(n3EditDiagrma);
		        		}
		        	}
		
			}
		
	      //PKY 08091003 E
	}

	public ElementLabelModel getElementLabelModelName() {
		return name;
	}

	public ElementLabelModel getElementLabelModelStreotype() {
		return this.streotype;
	}

	public CompartmentModel getattrModel() {
		return attrModel;
	}

	public CompartmentModel getoperModel() {
		return this.operModel;
	}

	//	public void removeName(){
		//		
		//	}
	public void removeMode() {
		this.removeChild(this.streotype);
		this.removeChild(this.name);
		this.removeChild(this.attrModel);
		this.removeChild(this.operModel);
	}

	public void addMode() {
		this.addChild(streotype);
		this.addChild(name);
		this.addChild(attrModel);
		this.addChild(operModel);
	}

	public String getName() {
		if(this.isViewMulti){
			if(name.getMulti()!=null && name.getMulti().trim().length()>0){
				String temp = name.getLabelContents();
				temp	= temp.replaceAll("["+name.getMulti()+"]", "");
				int index =  temp.lastIndexOf("[]");
				if(index>0){
					temp = temp.substring(0,index);
				}
				return temp;
			}
		}
		return name.getLabelContents();
	}

	public String getStereotype() {
		if(streotype!=null)
			return streotype.getLabelContents();
		else 
			return "";
//		if (streotype != null && streotype.getLabelContents().trim().length() > 0) {
//		String temp = streotype.getLabelContents().replaceFirst("<<", "");
//		temp = temp.replaceFirst(">>", "");
//		return temp;
//		}
//		else {
//		return "";
//		}
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
		return "";
	}

	public void setUMLDataModel(UMLDataModel p) {
		uMLDataModel = p;
	}

	public void setTreeModel(UMLTreeModel p) {
		super.setTreeModel(p);
		name.setTreeModel(p);
	}

	public Object getPropertyValue(Object propName) {
		if (ID_ATTRIBUTE.equals(propName))
			return attributes;
		else if (ID_OPERATION.equals(propName))
			return this.operations;
		return super.getPropertyValue(propName);
	}

	public void setPropertyValue(Object id, Object value) {

		super.setPropertyValue(id, value);
	}

	public void setCloneAttribute() {
		for (int i = 0; i < attributes.size(); i++) {
			Object obj = attributes.get(i);
			AttributeElementModel child = new AttributeElementModel((AttributeEditableTableItem)obj);
			this.attrModel.addChild(child);
		}
	}

	public void setCloneOperation() {
		for (int i = 0; i < operations.size(); i++) {
			Object obj = operations.get(i);
			OperationElementModel child = new OperationElementModel((OperationEditableTableItem)obj);
			this.operModel.addChild(child);
		}
	}

	public void setAttribute(java.util.ArrayList list) {
		attributes.clear();
		for (int i = 0; i < list.size(); i++) {
			Object obj = list.get(i);
			attributes.add(obj);
		}
		for (int i = 0; i < attributes.size(); i++) {
			Object obj = attributes.get(i);
			AttributeElementModel child = new AttributeElementModel((AttributeEditableTableItem)obj);
			this.attrModel.addChild(child);
		}
	}

	public void setOperation(java.util.ArrayList list) {
		operations.clear();
		try{
			for (int i = 0; i < list.size(); i++) {
				Object obj = list.get(i);
				operations.add(obj);
			}

			for (int i = 0; i < operations.size(); i++) {
				Object obj = operations.get(i);
				OperationElementModel child = new OperationElementModel((OperationEditableTableItem)obj);
				this.operModel.addChild(child);
			}
		}

		catch(Exception e){
			e.printStackTrace();
		}
	}


//	public void setName(String uname) {
//	super.setName(uname);
//UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
//name.fireChildUpdate(e);
//firePropertyChange(ID_NAME, null, uname); //$NON-NLS-1$
////firePropertyChange("name", null, _name); //$NON-NLS-1$
//}

//public void addUpdateListener(UMLModel p) {
//	this.name.addUpdateListener(p);
//	}

//	public void removeUpdateListener(UMLModel p) {
//	this.name.removeUpdateListener(p);
//	}

//	public void updateModel(UpdateEvent p) {
//	if (p.getType() == IUpdateType.REMOVE_NAME) {
//	this.setTypeRef(null);


//	}
//	else  if (p.getType() == IUpdateType.RENAME_TYPE) {
//	this.setName(this.getName());


//	}
////	this.name.fireChildUpdate(p);
//	}


	public Object clone() {
		ClassModel clone = new ClassModel((UMLDataModel)this.getUMLDataModel().clone());
		//		clone.setUMLDataModel((UMLDataModel)this.getUMLDataModel().clone());
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
	//2008033101 PKY S 
	public boolean isMode() {
		return isMode;
	}
	//2008033101 PKY E


}
