package uuu.views;

import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.swt.graphics.Image;

public class ConsoleTableItem {
	public UMLTreeModel ref = null;
	public UMLModel refModel = null;
	public String typeName = null;
	public String packageName = null;
	public String msg = "";
	
	public String toString(){
		if(ref!=null){
			  return "(UML"+typeName+"):"+packageName+":"+ref.getName();
			
		}
		else{
			return msg;
		}
		
		
	}
	
	public Image getModelImage(){
		UMLTreeModel um = ref;
		if(um!=null){
		if(um.getModelType()==1){
    		if(um.getRefModel() instanceof N3EditorDiagramModel){
    			N3EditorDiagramModel nd = (N3EditorDiagramModel)um.getRefModel();
    			//useCase diagram
//    			if(nd.getDiagramType()==8){
    			
    				return ProjectManager.getInstance().getDiagramImage(nd.getDiagramType());
//    			}
    		}
    	}//useCase
		else
		return ProjectManager.getInstance().getImage(21);
		}
		return ProjectManager.getInstance().getImage(101);
	}

}
