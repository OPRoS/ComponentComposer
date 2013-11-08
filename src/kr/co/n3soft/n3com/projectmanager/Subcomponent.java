package kr.co.n3soft.n3com.projectmanager;

import kr.co.n3soft.n3com.model.component.AtomicComponentModel;
import kr.co.n3soft.n3com.model.component.ComponentModel;
import kr.co.n3soft.n3com.project.browser.StrcuturedPackageTreeLibModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;

public class Subcomponent {
	public String name = "";
	
	public String t_name = "";
	
	public String reference = "";
	public String id = "";
	public String version = "";
	public String type = "";
	
	public UMLTreeParentModel createComponentModel(UMLTreeParentModel treeModel){
		if("".equals(t_name)){
			t_name = name;
		}
		StrcuturedPackageTreeLibModel to1 = new StrcuturedPackageTreeLibModel(name);
		if(type.equals("atomic")){
		AtomicComponentModel child = new AtomicComponentModel();
		
		child.setStereotype("atomic");
		((UMLTreeParentModel)to1).setRefModel(child);
		
		child.setTreeModel(to1);
		child.setName(t_name);
		to1.setParent((UMLTreeParentModel)treeModel);
		((UMLTreeParentModel)treeModel).addChild(to1);
		ProjectManager.getInstance().getModelBrowser().refresh(treeModel);
		ProjectManager.getInstance().getModelBrowser().expend(to1);
		UMLTreeParentModel treeModel1 = ProjectManager.getInstance().getModelBrowser().getRcet();
		ProjectManager.getInstance().addUMLModel(t_name, (UMLTreeParentModel)treeModel1, child, 1000,5);
		}
		else{
			ComponentModel child = new ComponentModel();
			child.setName(t_name);
			ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)treeModel, child, -1,5);
			return (UMLTreeParentModel)child.getUMLTreeModel();
			
		}
		t_name = "";
		return to1;
	}
	
	
	public UMLTreeParentModel createComponentTemplateModel(UMLTreeParentModel treeModel){
		
		if("".equals(t_name)){
			t_name = name;
		}
		
		StrcuturedPackageTreeLibModel to1 = new StrcuturedPackageTreeLibModel(name);
		if(type.equals("atomic")){
		AtomicComponentModel child = new AtomicComponentModel();
		
		child.setStereotype("atomic");
		((UMLTreeParentModel)to1).setRefModel(child);
		
		child.setTreeModel(to1);
		child.setName(t_name);
		to1.setParent((UMLTreeParentModel)treeModel);
		((UMLTreeParentModel)treeModel).addChild(to1);
//		ProjectManager.getInstance().getModelBrowser().refresh(treeModel);
//		ProjectManager.getInstance().getModelBrowser().expend(to1);
//		UMLTreeParentModel treeModel1 = ProjectManager.getInstance().getModelBrowser().getRcet();
//		ProjectManager.getInstance().addUMLModel(name, (UMLTreeParentModel)treeModel1, child, 1000,5);
		}
		else{
			ComponentModel child = new ComponentModel();
			child.setName(t_name);
			ProjectManager.getInstance().addUMLModel(null, (UMLTreeParentModel)treeModel, child, -1,5);
			return (UMLTreeParentModel)child.getUMLTreeModel();
			
		}
		t_name = "";
		return to1;
	}
	

}
