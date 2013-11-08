package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.activity.TextAttachModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;
import org.eclipse.draw2d.BorderLayout;
import org.eclipse.draw2d.PositionConstants;
import org.eclipse.draw2d.geometry.Dimension;

public class ClassifierModelTextAttach extends TextAttachModel {
    static final long serialVersionUID = 1;
    public static int count = 0;
    public ElementLabelModel name = null;
    ElementLabelModel streotype = null;
    public NodeContainerModel boundaryModel = new NodeContainerModel();
    java.util.List temp_childs = null;
    TypeRefModel classModel = null;
    public int default_width = 112;//2008041702PKY S
    public int default_height = 65;//2008041702PKY S

    public ClassifierModelTextAttach() {
        classModel = new TypeRefModel();
        classModel.setParentModel(this);
        classModel.setParentClassModel(this);
        //		boundaryModel = new NodeContainerModel();
        //		boundaryModel.setParentModel(this);
        //		boundaryModel.setLayout(BorderLayout.CENTER);
        //		boundaryModel.setParentLayout(1);
        //		this.addChild(boundaryModel);
        size.width = default_width;
        size.height = default_height;
        classModel.setLayout(BorderLayout.TOP);
        classModel.setParentLayout(1);
        this.addChild(classModel);
        this.uMLDataModel.setElementProperty("ClassModel", classModel);
    }

    public ClassifierModelTextAttach(int w, int h) {
        default_width = w;
        this.default_height = h;
        classModel = new TypeRefModel(w, h / 3);
        classModel.setParentModel(this);
        classModel.setParentClassModel(this);
        //		boundaryModel = new NodeContainerModel();
        //		boundaryModel.setParentModel(this);
        //		boundaryModel.setLayout(BorderLayout.CENTER);
        //		boundaryModel.setParentLayout(1);
        //		this.addChild(boundaryModel);
        size.width = default_width;
        size.height = default_height;
        classModel.setLayout(BorderLayout.TOP);
        classModel.setParentLayout(1);
        this.addChild(classModel);
        this.uMLDataModel.setElementProperty("ClassModel", classModel);
    }

    public ClassifierModelTextAttach(UMLDataModel p) {
        classModel = (TypeRefModel)p.getElementProperty("ClassModel");
        classModel.setParentModel(this);
        classModel.setParentClassModel(this);
        String nodeContainerModel = (String)p.getElementProperty("NodeContainerModel");
        if (nodeContainerModel != null && nodeContainerModel.equals("true")) {
            this.addContainer(new NodeContainerModel());
        }
        //		boundaryModel = new NodeContainerModel();
        //		boundaryModel.setParentModel(this);
        //		boundaryModel.setLayout(BorderLayout.CENTER);
        //		boundaryModel.setParentLayout(1);
        //		this.addChild(boundaryModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = default_width;
        size.height = default_height;
        //		name = (ElementLabelModel)p.getElementProperty("NAME");
        classModel.setLayout(BorderLayout.TOP);
        classModel.setParentLayout(1);
        this.addChild(classModel);
        this.uMLDataModel.setElementProperty("ClassModel", classModel);
        this.setUMLDataModel(p);
    }
    
    public ClassifierModelTextAttach(UMLDataModel p, boolean isLoad) {
        classModel = (TypeRefModel)p.getElementProperty("ClassModel");
        classModel.setParentModel(this);
        classModel.setParentClassModel(this);
        String nodeContainerModel = (String)p.getElementProperty("NodeContainerModel");
        if (nodeContainerModel != null && nodeContainerModel.equals("true")) {
            this.addContainer(new NodeContainerModel());
        }
        //		boundaryModel = new NodeContainerModel();
        //		boundaryModel.setParentModel(this);
        //		boundaryModel.setLayout(BorderLayout.CENTER);
        //		boundaryModel.setParentLayout(1);
        //		this.addChild(boundaryModel);
        streotype = new ElementLabelModel(PositionConstants.CENTER);
        size.width = default_width;
        size.height = default_height;
        //		name = (ElementLabelModel)p.getElementProperty("NAME");
        classModel.setLayout(BorderLayout.TOP);
        classModel.setParentLayout(1);
        this.addChild(classModel);
        this.uMLDataModel.setElementProperty("ClassModel", classModel);
        this.setLoadModel(p);
//        this.setUMLDataModel(p);
    }
  //PKY 08082201 S 인터페이스 맵핑안되는문제
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
	//PKY 08082201 E 인터페이스 맵핑안되는문제


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
    
    
    

    public void addContainer(NodeContainerModel p) {
        boundaryModel = p;
        boundaryModel.setParentModel(this);
        boundaryModel.setLayout(BorderLayout.CENTER);
        boundaryModel.setParentLayout(1);
        this.addChild(boundaryModel);
        this.uMLDataModel.setElementProperty("NodeContainerModel", "true");
    }

    public void removeContainer(NodeContainerModel p) {
        //		boundaryModel = p;
        //		boundaryModel.setParentModel(this);
        //		boundaryModel.setLayout(BorderLayout.CENTER);
        //		boundaryModel.setParentLayout(1);
        this.removeChild(boundaryModel);
        this.uMLDataModel.setElementProperty("NodeContainerModel", null);
    }

    public void setAttrIsBorder(boolean p) {
        this.classModel.attrModel.setIsBorder(p);
    }

    public void setOperIsBorder(boolean p) {
        this.classModel.operModel.setIsBorder(p);
    }

    public ClassModel getClassModel() {
        return this.classModel;
    }

    public boolean isChild(UMLElementModel child) {
        return temp_childs.contains(child);
    }

    public void setName(String uname) {
        classModel.setName(uname);
    }

    public void setStereotype(String uname) {
        classModel.setStereotype(uname);
        firePropertyChange(this.ID_REFRESH, null, null);
        //PKY 08080501 S 스트레오타입 삭제 시 사이즈 줄어들도록 수정
		ProjectManager.getInstance().autoSize(classModel);		
		if(uname.equals(""))
			this.setSize(new Dimension(this.getSize().width,this.getSize().height-16));
		//PKY 08080501 E 스트레오타입 삭제 시 사이즈 줄어들도록 수정
    }

    public String getName() {
        return classModel.getName();
    }

    public String getStereotype() {
        return classModel.getStereotype();
    }

    public void addNameAfterModel(UMLModel p) {
        this.classModel.addChild(p, 1);
    }

    //	public String getStreotype(){
    //		return classModel.getStereotype();
    //	}
    //	
    public static String TERMINALS_OUT[] = new String[] {
        "1", "2", "3", "4", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "5", "6", "7", "8"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public static String TERMINALS_IN[] = new String[] {
        "A", "B", "C", "D", //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$
            "E", "F", "G", "H"
    }; //$NON-NLS-4$//$NON-NLS-3$//$NON-NLS-2$//$NON-NLS-1$

    public void update() {
        //		Enumeration elements = outputs.elements();
        //		Wire w;
        //		int val = 0;
        //		while (elements.hasMoreElements()) {
        //			w = (Wire) elements.nextElement();
        ////			if (w.getSourceTerminal().equals(terminal) && this.equals(w.getSource()))
        //				w.setValue(false);
        //		}
        //		
        //		for (int i=0; i<8;i++)
        //			setOutput(TERMINALS_OUT[i],getInput(TERMINALS_IN[i]));
    }

    public String toString() {
        return getStereotype() + " " + this.getName();
        //		return elementLabel.getLabelContents();
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
  //PKY 08080501 S 사용자가 컬러색상을 수정 할 수있도록 수정
	public void refresh(){
		firePropertyChange(this.ID_REFRESH, null, null);
	}
	//PKY 08080501 E 사용자가 컬러색상을 수정 할 수있도록 수정
}
