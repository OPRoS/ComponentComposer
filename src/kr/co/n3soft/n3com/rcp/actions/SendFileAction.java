package kr.co.n3soft.n3com.rcp.actions;

import java.io.File;

import kr.co.n3soft.n3com.edit.UMLEditPart;
import kr.co.n3soft.n3com.model.comm.UMLModel;
import kr.co.n3soft.n3com.model.diagram.N3EditorDiagramModel;
import kr.co.n3soft.n3com.net.NetManager;
import kr.co.n3soft.n3com.project.browser.UMLTreeParentModel;
import kr.co.n3soft.n3com.projectmanager.CompAssemManager;
import kr.co.n3soft.n3com.projectmanager.ProjectManager;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchWindow;

public class SendFileAction extends SelectionAction {
	
	IWorkbenchWindow workbenchWindow;
	public static   int ID=0;
	IEditorInput input = null;
	UMLModel selectModel; 
	 public SendFileAction(IEditorPart editor) {
	        super(editor);
	        this.setId("SendFileAction");
	        this.setText("Deploy");
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
			
			UMLModel addModel = selectModel;
			N3EditorDiagramModel nd = ProjectManager.getInstance().getUMLEditor().getDiagram();
			UMLTreeParentModel up = nd.getUMLTreeModel().getParent();
			String str = CompAssemManager.getInstance().getNetFoler();
			File target = new File(str);
			if(addModel.getUMLTreeModel() instanceof UMLTreeParentModel){
				CompAssemManager.getInstance().makeNetCopyMain(up, target,(UMLTreeParentModel)addModel.getUMLTreeModel());
				
			}
			NetManager.getInstance().setDirectory(new File(str));
			NetManager.getInstance().setIp(selectModel.getIP());
			NetManager.getInstance().setPort(selectModel.getPort1());
			ProjectManager.getInstance().getConsole().addMessage(addModel.getName()+" Component 전송시작");
			NetManager.getInstance().executeCommand(0);
			addModel.setBackGroundColor(ColorConstants.lightGreen);
			 
		}
		catch(Exception e){
			e.printStackTrace();
		}
	    
//	    ProjectManager.getInstance().getModelBrowser().writeModel();
		
	}
	

}