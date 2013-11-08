package kr.co.n3soft.n3com.project.browser;

import javac.parser.SourceManager;
import kr.co.n3soft.n3com.model.comm.IN3UMLModelDeleteListener;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.projectmanager.OPRoSManifest;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.core.runtime.IAdaptable;
import org.eclipse.ui.views.properties.IPropertySource;

public class UMLTreeModel  implements IAdaptable {
	
	public String ip;
	public String port;
	
	public OPRoSManifest oPRoSManifest;
	
    private java.util.List iN3UMLModelDeleteListeners = new java.util.ArrayList();
    private String name;
    private String stereo;
    private UMLTreeParentModel parent;
    private int modelType = 0;
    private Object refModel = null;
//    private java.util.ArrayList packageNames = new java.util.ArrayList();

    public void setRefModel(Object p) {
        modelType = ProjectManager.getInstance().getModelType((UMLModel)p, -1);
        this.refModel = p;
        
    }

    public Object getRefModel() {
        return refModel;
    }

    public UMLTreeModel(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
    
    
    public String getStereo() {
    	if(this.refModel!=null){
    		if(refModel instanceof UMLModel){
    			UMLModel um = (UMLModel)refModel;
    			if(um.getStereotype()!=null && !um.getStereotype().trim().equals("")){
    				stereo = um.getStereotype();
    			}
    		}
    	}
        return stereo;
    }

    public void setName(String p) {
        name = p;
    }

    public int getModelType() {
        return modelType;
    }

    public void setParent(UMLTreeParentModel parent) {
        this.parent = parent;
    }

    public UMLTreeParentModel getParent() {
        return parent;
    }

    public String toString() {
    	stereo = this.getStereo();
    	if(stereo!=null){
        return stereo+getName();
    	}
    	else{
    		 return getName();
    	}
    }

    public Object getAdapter(Class key) {
        if (key == IPropertySource.class) {
            if (refModel != null)
                return refModel;
        }
        return null;
    }

    public void addN3UMLModelDeleteListener(IN3UMLModelDeleteListener p) {
        this.iN3UMLModelDeleteListeners.add(p);
    }

    public void removeN3UMLModelDeleteListener(IN3UMLModelDeleteListener p) {
        this.iN3UMLModelDeleteListeners.remove(p);
    }

    public void fireChildRemoved() {
        try {
            for (int i = 0; i < this.iN3UMLModelDeleteListeners.size(); i++) {
                IN3UMLModelDeleteListener l = (IN3UMLModelDeleteListener)iN3UMLModelDeleteListeners.get(i);
                l.removeModel((UMLModel)this.getRefModel());
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

	public java.util.List getIN3UMLModelDeleteListeners() {
		return iN3UMLModelDeleteListeners;
	}

	public void setIN3UMLModelDeleteListeners(java.util.List modelDeleteListeners) {
		iN3UMLModelDeleteListeners = modelDeleteListeners;
	}
	
	public void getPackage(java.util.ArrayList p){
		if(this.getParent()!=null){
			p.add(this.getName());
			this.getParent().getPackage(p);
		}
		else
		return ;
	}
	public void writeH(){
		 UMLModel um1 = (UMLModel)this.getRefModel();
		 SourceManager.getInstance().setType(2);
			SourceManager.getInstance().writeH(um1);
	 }
	 public void writeJava(){
		 UMLModel um1 = (UMLModel)this.getRefModel();
		 SourceManager.getInstance().setType(1);
			SourceManager.getInstance().writeJava(um1);
	 }
}
