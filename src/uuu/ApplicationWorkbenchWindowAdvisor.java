package uuu;

import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.project.dialog.NewProjectDialog;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.jface.dialogs.IDialogConstants;
import org.eclipse.swt.widgets.TreeItem;
import org.eclipse.ui.application.ActionBarAdvisor;
import org.eclipse.ui.application.IActionBarConfigurer;
import org.eclipse.ui.application.IWorkbenchWindowConfigurer;
import org.eclipse.ui.application.WorkbenchWindowAdvisor;

public class ApplicationWorkbenchWindowAdvisor extends WorkbenchWindowAdvisor {

	public ApplicationWorkbenchWindowAdvisor(IWorkbenchWindowConfigurer configurer) {
		super(configurer);
	}

	public ActionBarAdvisor createActionBarAdvisor(
			IActionBarConfigurer configurer) {
		return new ApplicationActionBarAdvisor(configurer);
	}

	public void preWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
		
//		configurer.setShowCoolBar(false);
//		configurer.setShowStatusLine(true);
//		configurer.setShowCoolBar(true);
		configurer.setTitle("ComponentComposite");
		ProjectManager.getInstance().setConfigurer(configurer);
		
//		configurer.setShowPerspectiveBar(true);
	}
	
	//20080729 KDI s 새 프로젝트 관련 액션 추가
	public void postWindowOpen() {
		IWorkbenchWindowConfigurer configurer = getWindowConfigurer();
//		NewProjectDialog da = new NewProjectDialog(configurer.getWindow().getShell());
//		int btn = da.open();
//
//		if(btn == IDialogConstants.OK_ID){
//			UMLTreeParentModel treeObject = null;
//
//			TreeItem trees[] = ProjectManager.getInstance().getModelBrowser().getViewer().getTree().getItems();
//			if(trees != null && trees.length>0){
//				treeObject = (UMLTreeParentModel)trees[0].getData();
//			}
//
//			String name = da.getName();
//			java.util.ArrayList list = da.getModelType();
//
//			if (da.isOK && treeObject != null){        	
//				ProjectManager.getInstance().addUMLModel(name, treeObject, null, 0,-1);
//				UMLTreeParentModel root = (UMLTreeParentModel)treeObject.getChildren()[0];
//				if(root != null){
//					for(int i=0;i<list.size();i++){
//						java.util.HashMap map = (java.util.HashMap)list.get(i);
//						ProjectManager.getInstance().addUMLDiagram((String)map.get("name"), root, null, 1, true, (Integer)map.get("type"));
//					}
//				}
//				ProjectManager.getInstance().getModelBrowser().refresh(root);
//			}	        
//		}			
	}
	//20080729 KDI e
}
