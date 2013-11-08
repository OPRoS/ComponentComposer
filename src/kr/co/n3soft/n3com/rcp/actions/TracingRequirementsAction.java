package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.edit.RequirementEditPart;
import kr.co.n3soft.n3com.lang.N3Messages;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.project.browser.UMLTreeModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class TracingRequirementsAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel; 
	 public TracingRequirementsAction(IEditorPart editor) {
	        super(editor);
	        this.setId("TracingRequirementsAction");
	        this.setText(N3Messages.POPUP_TRACING_IN_REQUIREMENT);
//	        setChecked(isChecked());
	    }
	 protected boolean calculateEnabled() {
		
	        StructuredSelection list = (StructuredSelection)this.getSelection();
	        if (list != null && list.size() == 1) {
	            Object obj = list.getFirstElement();
	            if(obj instanceof RequirementEditPart){
	            	RequirementEditPart rep = (RequirementEditPart)obj;
	            	selectModel = (UMLModel)rep.getModel();
	                  return true;
	            }
	        }
		 return false;
	 }
	public void run() {
		try{
			
			UMLModel addModel = selectModel;
			UMLTreeModel treeObject = (UMLTreeModel)addModel.getUMLTreeModel();
			ProjectManager.getInstance().setSearchModel(false);
			ProjectManager.getInstance().setisSearchDiagaramModel(false);//20080325 PKY S °Ë»ö

			String type = ProjectManager.getInstance().getModelTypeName(ProjectManager.getInstance().getModelType((UMLModel)treeObject.getRefModel(), -1));
			UMLModel um= (UMLModel)treeObject.getRefModel();

			ProjectManager.getInstance().getModelBrowser().searchReqIDModel(um.getReqID(), null);
			
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
	
//	private String openFileDialog() {
//		FileDialog dialog = new FileDialog(workbenchWindow.getShell(), SWT.OPEN);
//		dialog.setText("shapes");
//		//dialog.setFilterExtensions(new String[] { ".shapes" });
//		return dialog.open();
//	}
}
