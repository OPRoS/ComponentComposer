package kr.co.n3soft.n3com.rcp.actions;

import kr.co.n3soft.n3com.UMLEditor;
import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.geometry.Point;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class ZOrderAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel; 
	 public ZOrderAction(IEditorPart editor) {
	        super(editor);
	        this.setId("ZOrderAction");
	        this.setText("ZOrder");
//	        setChecked(isChecked());
	    }
	 protected boolean calculateEnabled() {
		
	        StructuredSelection list = (StructuredSelection)this.getSelection();
	        if (list != null && list.size() == 1) {
	            Object obj = list.getFirstElement();
	            if(obj instanceof UMLEditPart){
	            UMLEditPart u = (UMLEditPart)obj;
	            selectModel= (UMLModel)u.getModel();
	            int i = ProjectManager.getInstance().getModelType(selectModel, -1);
	            if(i>=0){
	            	return true;
	            }
	            }
	        }
		 return false;
	 }
	public void run() {
		try{
			UMLEditor ue	 = ProjectManager.getInstance().getUMLEditor();	
			N3EditorDiagramModel nd = ue.getDiagram();
//			nd.addChild(selectModel);
			UMLModel addModel = selectModel;
			Point pt = addModel.getLocation();
			nd.removeModel(selectModel);
			addModel.setLocation(pt);
			
			nd.addChild(addModel);
//			nd.addChild(selectModel);
//			nd.getChildren().set(nd.getChildren().size()-1, selectModel);
			
			 
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

