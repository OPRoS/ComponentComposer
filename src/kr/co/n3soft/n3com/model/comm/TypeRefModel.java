package kr.co.n3soft.n3com.model.comm;

import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

public class TypeRefModel extends ClassModel implements IUMLModelUpdateListener {
    //참조관련
    static final long serialVersionUID = 1;
    public static int count = 0;
    public UMLModel typeRefModel = null;
//    public boolean isComponent = false;

    public TypeRefModel(int type, int w, int h) {
        super(type, w, h);
    }

    public TypeRefModel() {
        super();
    }

    public TypeRefModel(int w, int h) {
        super(w, h);
    }

    public TypeRefModel(UMLDataModel p) {
        super(p);
    }

    public void setTypeRef(UMLModel p) {
        this.typeRefModel = p;
        this.uMLDataModel.setElementProperty("typeRefModel", p);
        this.setRefName(p);
        this.setName(this.getName());
    }

    public void setRefName(UMLModel p) {
        this.name.setRefModel(p);
    }

    public UMLModel getTypeRef() {
        return (UMLModel)this.uMLDataModel.getElementProperty("typeRefModel");
    }

    public void setName(String uname) {
    	String oldName = getName();//PKY 08091003 S
        super.setName(uname);
        UpdateEvent e = new UpdateEvent(IUpdateType.RENAME_TYPE, null);
        name.fireChildUpdate(e);
        firePropertyChange(ID_NAME, null, uname); //$NON-NLS-1$


//		firePropertyChange("name", null, _name); //$NON-NLS-1$
    }

    public void addUpdateListener(UMLModel p) {
        this.name.addUpdateListener(p);
    }

    public void removeUpdateListener(UMLModel p) {
        this.name.removeUpdateListener(p);
    }

    public void updateModel(UpdateEvent p) {
    	 if (p.getType() == IUpdateType.REMOVE_NAME) {
         	this.setTypeRef(null);
         	
         	
         }
         else  if (p.getType() == IUpdateType.RENAME_TYPE) {
         	this.setName(this.getName());
         	
         	
         }
//        this.name.fireChildUpdate(p);
    }

    public Object clone() {
        TypeRefModel clone = new TypeRefModel((UMLDataModel)this.getUMLDataModel().clone());
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
}
