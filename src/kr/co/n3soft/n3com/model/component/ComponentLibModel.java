package kr.co.n3soft.n3com.model.component;

import java.io.File;

import kr.co.n3soft.n3com.model.comm.UMLDataModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ComponentProfile;

public class ComponentLibModel extends ComponentModel{
	
//	public File file = null;
//	public File dir = null;
//	public File fsmFile = null;
//	public File dllFile = null;
//	public String fsmFileName = "";
	
//	public boolean isInstance = false; 
//	
//	public UMLTreeModel instanceUMLTreeModel = null;
	
//	public boolean isInstance = false;
	
	
	
	
//public UMLTreeModel getInstanceUMLTreeModel() {
//	instanceUMLTreeModel = (UMLTreeModel)this.getUMLDataModel().getElementProperty("instanceUMLTreeModel");
//		return instanceUMLTreeModel;
//	}
//	public void setInstanceUMLTreeModel(UMLTreeModel instanceUMLTreeModel) {
//		this.instanceUMLTreeModel = instanceUMLTreeModel;
//		this.getUMLDataModel().setElementProperty("instanceUMLTreeModel", instanceUMLTreeModel);
//	}
//	
//	public boolean isInstance() {
//		String in = this.getUMLDataModel().getProperty("isInstance");
//		if(in!=null && !in.trim().equals("")){
//			isInstance = 	Boolean.valueOf(in);
//		}
//		return isInstance;
//	}
//	
//	public void setInstance(boolean isInstance) {
//		this.isInstance = isInstance;
//		this.getUMLDataModel().setProperty("isInstance", String.valueOf(isInstance));
//	}

	//	public boolean isInstance() {
//		return isInstance;
//	}
//	public void setInstance(boolean isInstance) {
//		this.isInstance = isInstance;
//	}
	ComponentProfile retModel = null;
	
	public  ComponentLibModel(){
		
	}
	public ComponentLibModel(UMLDataModel p) {
	        super(p);
	      
	    }
	
	
	public ComponentProfile getRetModel() {
		return (ComponentProfile)this.getUMLDataModel().getElementProperty("retModel");
	}
	public void setRetModel(ComponentProfile retModel) {
		this.retModel = retModel;
		this.getUMLDataModel().setElementProperty("retModel", retModel);
	}
	
	public Object clone() {
		//111110 SDM S - 아톰/컴포지트 라이브러리 구분
		ComponentLibModel clone = null;
		if(this.getStereotype().equals("atomic") || this.getStereotype().equals("<<atomic>>"))
			clone = new AtomicLibModel((UMLDataModel)this.getUMLDataModel().clone());
		else
			clone = new ComponentLibModel((UMLDataModel)this.getUMLDataModel().clone());
		//111110 SDM E - 아톰/컴포지트 라이브러리 구분
        //		clone.temp_childs = this.activityModel.getChildren();
//        for (int i = 0; i < this.boundaryModel.getChildren().size(); i++) {
//            UMLModel um = (UMLModel)this.boundaryModel.getChildren().get(i);
//            clone.boundaryModel.addChild((UMLElementModel)um.clone());
//        }
//        clone.setPortManager(this.getPortManager().clone(clone));
        clone.setSize(this.getSize());
        clone.setBackGroundColor(this.getBackGroundColor());
        clone.setLocation(this.getLocation());
        clone.sourceModel = this;
//        ProjectManager.getInstance().setTempCopyMap(clone.sourceModel.getID(), clone);
//        for (int i1 = 0; i1 < this.getSourceConnections().size(); i1++) {
//            LineModel li = (LineModel)this.getSourceConnections().get(i1);
//            LineModel liCopy = (LineModel)li.clone();
//            ProjectManager.getInstance().addSelectLineModel(liCopy);
//        }
        return clone;
    }
	
	

}
